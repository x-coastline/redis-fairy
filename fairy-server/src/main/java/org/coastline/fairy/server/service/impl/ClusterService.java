package org.coastline.fairy.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.coastline.fairy.client.RedisClient;
import org.coastline.fairy.client.RedisClientFactory;
import org.coastline.fairy.client.model.ClusterInfo;
import org.coastline.fairy.client.model.RedisInfo;
import org.coastline.fairy.client.model.RedisMode;
import org.coastline.fairy.common.TimeUtil;
import org.coastline.fairy.server.dao.IClusterDao;
import org.coastline.fairy.server.dao.INodeInfoDao;
import org.coastline.fairy.server.dao.IRedisNodeDao;
import org.coastline.fairy.server.entity.ClusterEntity;
import org.coastline.fairy.server.service.IClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static org.coastline.fairy.client.model.RedisInfo.SERVER;


/**
 * Created by jn50 on 2020/9/11.
 */
@Service
public class ClusterService implements IClusterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterService.class);

    private static final String OK = "ok";

    private static final String FAIL = "fail";

    private static final String CLUSTER_STATE = "cluster_state";

    private static final String CLUSTER_SLOTS_ASSIGNED = "cluster_slots_assigned";

    private static final String CLUSTER_SLOT_OK = "cluster_slots_ok";

    private static final String CLUSTER_SLOTS_PFAIL = "cluster_slots_pfail";

    private static final String CLUSTER_SLOTS_FAIL = "cluster_slots_fail";

    private static final String CLUSTER_KNOWN_NODES = "cluster_known_nodes";

    private static final String CLUSTER_SIZE = "cluster_size";

    @Autowired
    private IClusterDao clusterDao;

    @Autowired
    private INodeInfoDao nodeInfoDao;

    @Autowired
    private IRedisNodeDao redisNodeDao;

    @Override
    public Set<Integer> getAllClusterId() {
        return null;
    }

    @Override
    public List<ClusterEntity> getAllCluster() {
        return clusterDao.selectAllCluster();
    }

    @Override
    public ClusterEntity getClusterById(Integer clusterId) {
        return clusterDao.selectClusterById(clusterId);
    }

    @Override
    public List<ClusterEntity> getClusterByGroup(Integer groupId) {
        return clusterDao.selectClusterByGroup(groupId);
    }

    @Transactional
    @Override
    public boolean addCluster(ClusterEntity cluster) {
        ClusterEntity completedCluster = completeClusterInfo(cluster);
        Timestamp currentTimestamp = TimeUtil.getCurrentTimestamp();
        completedCluster.setCreateTime(currentTimestamp);
        completedCluster.setUpdateTime(currentTimestamp);
        clusterDao.insertCluster(completedCluster);
        Integer clusterId = cluster.getClusterId();
        // create node info table for this cluster
        nodeInfoDao.createNodeInfoTable(clusterId);
        return true;
    }

    @Override
    public boolean updateCluster(ClusterEntity cluster) {
        clusterDao.updateCluster(cluster);
        return true;
    }

    @Override
    public boolean updateClusterState(ClusterEntity cluster) {
        ClusterEntity completedCluster = completeClusterInfo(cluster);
        clusterDao.updateCluster(completedCluster);
        return true;
    }

    @Override
    public boolean deleteClusterState(ClusterEntity cluster) {
        clusterDao.deleteCluster(cluster);
        Integer clusterId = cluster.getClusterId();
        nodeInfoDao.deleteNodeInfoIndex(clusterId);
        nodeInfoDao.dropTable(clusterId);
        redisNodeDao.deleteNodeByClusterId(clusterId);
        return false;
    }

    @Override
    public ClusterEntity completeClusterInfo(ClusterEntity cluster) {
        Integer clusterId = cluster.getClusterId();
        String clusterName = cluster.getClusterName();
        // reset cluster state
        cluster.setState(ClusterEntity.State.HEALTH);
        boolean fillBaseInfoResult = fillBaseInfo(cluster);
        if (!fillBaseInfoResult) {
            LOGGER.error("Fill base info failed, cluster id = {}, cluster name = {}", clusterId, clusterName);
            cluster.setState(ClusterEntity.State.BAD);
        }
        RedisMode redisMode = cluster.getRedisMode();
        boolean fillInfoResult = false;
        switch (redisMode) {
            case CLUSTER:
                fillInfoResult = fillInfoForCluster(cluster);
                break;
            case STANDALONE:
                fillInfoResult = fillInfoForStandalone(cluster);
                break;
            case SENTINEL:
                fillInfoResult = fillInfoForSentinel(cluster);
            default:
                break;
        }
        if (!fillInfoResult) {
            LOGGER.error("Complete cluster info failed, cluster id = {}, cluster name = {}", clusterId, clusterName);
            cluster.setState(ClusterEntity.State.BAD);
        }
        return cluster;
    }

    private boolean fillInfoForCluster(ClusterEntity cluster) {
        RedisClient redisClient = null;
        try {
            redisClient = RedisClientFactory.buildRedisClient(cluster.getSeed(), cluster.getRedisPassword());
            ClusterInfo clusterInfo = redisClient.clusterInfoModel();
            cluster.setState(calculateClusterState(clusterInfo.getClusterState()));
            cluster.setMasterNumber(clusterInfo.getClusterSize());
            cluster.setNodeNumber(clusterInfo.getClusterKnownNodes());
            return true;
        } catch (Exception e) {
            LOGGER.error("Fill info for cluster failed, " + JSONObject.toJSONString(cluster), e);
            return false;
        } finally {
            if (redisClient != null) {
                redisClient.close();
            }
        }
    }

    private boolean fillInfoForStandalone(ClusterEntity cluster) {
        /*IStandaloneRedisManageOperator standaloneRedisManageOperator = null;
        try {
            RedisClientConfig seedRedisClientConfig = new RedisClientConfig(cluster.getSeed(), cluster.getRedisPassword());
            standaloneRedisManageOperator = new StandaloneRedisManageOperator(seedRedisClientConfig);
            List<RedisNode> nodeList = standaloneRedisManageOperator.nodes();
            cluster.setState(ClusterEntity.ClusterState.HEALTH);
            cluster.setMasterNumber(1);
            cluster.setNodeNumber(nodeList.size());
            return true;
        } catch (Exception e) {
            LOGGER.error("Fill info for standalone failed, " + JSONObject.toJSONString(cluster), e);
            return false;
        } finally {
            if (standaloneRedisManageOperator != null) {
                standaloneRedisManageOperator.close();
            }
        }*/
        return true;
    }

    private boolean fillInfoForSentinel(ClusterEntity cluster) {
        /*String[] nodes = StringUtil.splitByCommas(cluster.getSeed());
        int length = nodes.length;
        cluster.setState(ClusterEntity.ClusterState.HEALTH);
        cluster.setMasterNumber(length);
        cluster.setNodeNumber(length);*/
        return true;
    }

    /**
     * 注意：默认所有节点的版本一致
     * 寻找一个种子节点获取集群的基本信息
     *
     * @param cluster
     */
    private boolean fillBaseInfo(ClusterEntity cluster) {
        RedisClient redisClient = null;
        try {
            redisClient = RedisClientFactory.buildRedisClient(cluster.getSeed(), cluster.getRedisPassword());
            RedisInfo redisInfo = redisClient.infoModel(SERVER);
            cluster.setOs(redisInfo.getOs());
            cluster.setRedisMode(redisInfo.getRedisMode());
            cluster.setRedisVersion(redisInfo.getRedisVersion());
            return true;
        } catch (Exception e) {
            LOGGER.error("Fill redis base info failed, " + JSONObject.toJSONString(cluster), e);
            return false;
        } finally {
            if (redisClient != null) {
                redisClient.close();
            }
        }
    }

    private ClusterEntity.State calculateClusterState(ClusterInfo.ClusterState state) {
        return ClusterInfo.ClusterState.OK.equals(state) ? ClusterEntity.State.HEALTH : ClusterEntity.State.BAD;
    }

}
