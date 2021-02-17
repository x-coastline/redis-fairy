package org.coastline.fairy.client.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

/**
 * <id> <ip:port> <flags> <master> <ping-sent> <pong-recv> <config-epoch> <link-state> <slot> <slot> ... <slot>
 *
 * reference: http://www.redis.cn/commands/cluster-nodes.html
 * @author Jay.H.Zou
 * @date 2020/10/14
 */
@Data
@Builder
@NoArgsConstructor
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

    public RedisClusterNode(String nodeId, HostAndPort node) {
        super(nodeId, node);
    }

}
