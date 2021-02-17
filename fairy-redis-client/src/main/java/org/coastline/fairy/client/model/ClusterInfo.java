package org.coastline.fairy.client.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cluster info
 *
 * @author Jay.H.Zou
 * @date 2020/10/8
 */
@Data
@Builder
@NoArgsConstructor
public class ClusterInfo {

    private ClusterState clusterState;
    private int clusterSlotsAssigned;
    private int clusterSlotsOk;
    private int clusterSlotsPfail;
    private int clusterSlotsFail;
    private int clusterKnownNodes;
    private int clusterSize;
    private int clusterCurrentEpoch;
    private int clusterMyEpoch;
    private long clusterStatsMessagesPingSent;
    private long clusterStatsMessagesPongSent;
    private long clusterStatsMessagesSent;
    private long clusterStatsMessagesPingReceived;
    private long clusterStatsMessagesPongReceived;
    private long clusterStatsMessagesReceived;

    public enum ClusterState {
        OK,

        FAIL,

        PFAIL
    }

}
