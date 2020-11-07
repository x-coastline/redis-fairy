package org.coastline.amber.client.model;


import redis.clients.jedis.HostAndPort;

import java.util.Set;

/**
 * <id> <ip:port> <flags> <master> <ping-sent> <pong-recv> <config-epoch> <link-state> <slot> <slot> ... <slot>
 *
 * reference: http://www.redis.cn/commands/cluster-nodes.html
 * @author Jay.H.Zou
 * @date 2020/10/14
 */
public class RedisClusterNode extends RedisNode {

    private String nodeId;

    private HostAndPort node;

    private Set<NodeFlag> flags;

    private RedisRole role;

    /**
     * 当前节点是副本是谁
     */
    private String replicateOf;

    private HostAndPort masterNode;

    private long pingSentTimestamp;

    private long pongReceivedTimestamp;

    private long configEpoch;

    private boolean connected;

    private int slotNumber;

    private String slotRange;

    public RedisClusterNode(){}

    public RedisClusterNode(String nodeId, HostAndPort node) {
        this.nodeId = nodeId;
        this.node = node;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public HostAndPort getNode() {
        return node;
    }

    public void setNode(HostAndPort node) {
        this.node = node;
    }

    public Set<NodeFlag> getFlags() {
        return flags;
    }

    public void setFlags(Set<NodeFlag> flags) {
        this.flags = flags;
    }

    public RedisRole getRole() {
        return role;
    }

    public void setRole(RedisRole role) {
        this.role = role;
    }

    public String getReplicateOf() {
        return replicateOf;
    }

    public void setReplicateOf(String replicateOf) {
        this.replicateOf = replicateOf;
    }

    public HostAndPort getMasterNode() {
        return masterNode;
    }

    public void setMasterNode(HostAndPort masterNode) {
        this.masterNode = masterNode;
    }

    public long getPingSentTimestamp() {
        return pingSentTimestamp;
    }

    public void setPingSentTimestamp(long pingSentTimestamp) {
        this.pingSentTimestamp = pingSentTimestamp;
    }

    public long getPongReceivedTimestamp() {
        return pongReceivedTimestamp;
    }

    public void setPongReceivedTimestamp(long pongReceivedTimestamp) {
        this.pongReceivedTimestamp = pongReceivedTimestamp;
    }

    public long getConfigEpoch() {
        return configEpoch;
    }

    public void setConfigEpoch(long configEpoch) {
        this.configEpoch = configEpoch;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getSlotRange() {
        return slotRange;
    }

    public void setSlotRange(String slotRange) {
        this.slotRange = slotRange;
    }

    /**
     * Redis Cluster node flags.
     */
    public enum NodeFlag {

        NOFLAGS("noflags"),
        MYSELF("myself"),
        SLAVE("slave"),
        REPLICA("replica"),
        MASTER("master"),
        EVENTUAL_FAIL("eventual_fail"),
        FAIL("fail"),
        HANDSHAKE("handshake"),
        NOADDR("noaddr"),
        UNKOWN("unknow");

        private String value;

        NodeFlag(String value) {
            this.value = value;
        }

    }

}
