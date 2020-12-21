package org.coastline.fairy.client.model;


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

    private Set<NodeFlag> flags;

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


    /**
     * Redis Cluster node flags.
     */
    public enum NodeFlag {

        /**
         * no state
         */
        NOFLAGS("noflags"),
        /**
         * current node
         */
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

    public RedisClusterNode(){}

    public RedisClusterNode(String nodeId, HostAndPort node) {
        super(nodeId, node);
    }

    public Set<NodeFlag> getFlags() {
        return flags;
    }

    public void setFlags(Set<NodeFlag> flags) {
        this.flags = flags;
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

}
