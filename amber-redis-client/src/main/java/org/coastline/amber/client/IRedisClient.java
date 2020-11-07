package org.coastline.amber.client;

import org.coastline.amber.client.model.ClusterInfo;
import org.coastline.amber.client.model.RedisClusterNode;
import org.coastline.amber.client.model.RedisInfo;
import org.coastline.amber.client.model.RedisSlowLog;
import redis.clients.jedis.ClusterReset;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.MigrateParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2020/11/3
 */
public interface IRedisClient {

    Jedis getClient();

    /**
     * Ping redis
     *
     * @return
     */
    boolean ping();

    Map<String, String> info() throws IOException;

    RedisInfo infoModel();

    /**
     * server: Redis服务器的一般信息
     * clients: 客户端的连接部分
     * memory: 内存消耗相关信息
     * persistence: RDB和AOF相关信息
     * stats: 一般统计
     * replication: 主/从复制信息
     * cpu: 统计CPU的消耗
     * commandstats: Redis命令统计
     * cluster: Redis集群信息
     * keyspace: 数据库的相关统计
     * @param section
     * @return
     */
    Map<String, String> info(String section);

    RedisInfo infoModel(String section);

    Map<String, String> clusterInfo();

    ClusterInfo clusterInfoModel();

    List<RedisClusterNode> clusterNodes() throws IOException;

    /**
     * Get slow log
     *
     * @return
     */
    List<RedisSlowLog> slowLog();

    List<RedisSlowLog> slowLog(int size);


    /** config */
    /**
     * Get redis configuration
     *
     * @return
     */
    Map<String, String> getConfig();

    /**
     * Get redis configuration with sub key
     *
     * @param pattern
     * @return
     */
    Map<String, String> getConfig(String pattern);

    /**
     * Rewrite redis configuration
     *
     * @return
     */
    boolean rewriteConfig();

    String clusterMeet(String host, int port);

    /**
     * Be slave
     * 重新配置一个节点成为指定master的salve节点
     *
     * @param nodeId
     * @return
     */
    boolean clusterReplicate(String nodeId);

    /**
     * Be master
     * 该命令只能在群集slave节点执行，让slave节点进行一次人工故障切换
     *
     * @return
     */
    boolean clusterFailOver();

    String clusterAddSlots(int... slots);

    String clusterSetSlotNode(int slot, String nodeId);

    String clusterSetSlotImporting(int slot, String nodeId);

    String clusterSetSlotMigrating(int slot, String nodeId);

    List<String> clusterGetKeysInSlot(int slot, int count);

    String clusterSetSlotStable(int slot);

    boolean clusterForget(String nodeId);

    String clusterReset(ClusterReset reset);

    String migrate(String host, int port, String key, int destinationDb, int timeout);

    String migrate(String host, int port, int destinationDB,
                   int timeout, MigrateParams params, String... keys);

    /**
     * old name: slaveOf
     *
     * @param host
     * @param port
     * @return OK
     */
    boolean replicaOf(String host, int port);

    /**
     * standalone forget this node
     *
     * @return
     */
    String replicaNoOne();

    String memoryPurge();

    List<Map<String, String>> getSentinelMasters();

    List<String> getMasterAddrByName(String masterName);

    List<Map<String, String>> sentinelSlaves(String masterName);

    boolean monitorMaster(String masterName, String ip, int port, int quorum);

    boolean failoverMaster(String masterName);

    boolean sentinelRemove(String masterName);

    Long resetConfig(String pattern);

    boolean sentinelSet(String masterName, Map<String, String> parameterMap);

    /**
     * Close client
     *
     * @return
     */
    void close();
}
