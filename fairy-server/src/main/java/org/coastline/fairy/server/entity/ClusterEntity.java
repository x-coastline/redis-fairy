package org.coastline.fairy.server.entity;

import org.coastline.fairy.client.model.RedisMode;
import org.coastline.fairy.server.model.Environment;
import org.coastline.fairy.server.model.ImportType;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class ClusterEntity {

    private Integer clusterId;

    private Integer groupId;

    /**
     * cluster name
     */
    private String clusterName;

    /**
     * seed nodes: ip:port,ip:port
     */
    private String seed;

    private RedisMode redisMode;

    private String os;

    private String redisVersion;

    private String image;

    private String redisPassword;

    private State state;

    private int masterNumber;

    /**
     * cluster-known-nodes
     * The total number of known nodes in the cluster, including nodes in HANDSHAKE state that may not currently be proper members of the cluster.
     */
    private int nodeNumber;

    private String tag;

    private Environment environment;

    private ImportType importType;

    private String description;

    private Timestamp updateTime;

    private Timestamp creationTime;

    /**
     * 集群状态
     */
    public enum State {
        /**
         * cluster info state: ok
         */
        HEALTH,

        /**
         * cluster info state not ok
         * can't get cluster info
         * redis node not good
         */
        BAD,

        /**
         * 有 info 告警时
         */
        WARN;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public RedisMode getRedisMode() {
        return redisMode;
    }

    public void setRedisMode(RedisMode redisMode) {
        this.redisMode = redisMode;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRedisVersion() {
        return redisVersion;
    }

    public void setRedisVersion(String redisVersion) {
        this.redisVersion = redisVersion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getMasterNumber() {
        return masterNumber;
    }

    public void setMasterNumber(int masterNumber) {
        this.masterNumber = masterNumber;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
