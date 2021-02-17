package org.coastline.fairy.server.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coastline.fairy.client.model.RedisMode;
import org.coastline.fairy.server.model.Environment;
import org.coastline.fairy.server.model.ImportType;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@Builder
@NoArgsConstructor
public class ClusterDO {

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


}
