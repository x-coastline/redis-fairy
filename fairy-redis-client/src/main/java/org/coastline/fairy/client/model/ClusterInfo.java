package org.coastline.fairy.client.model;

/**
 * @author Jay.H.Zou
 * @date 2020/10/8
 */
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

    public ClusterState getClusterState() {
        return clusterState;
    }

    public void setClusterState(ClusterState clusterState) {
        this.clusterState = clusterState;
    }

    public int getClusterSlotsAssigned() {
        return clusterSlotsAssigned;
    }

    public void setClusterSlotsAssigned(int clusterSlotsAssigned) {
        this.clusterSlotsAssigned = clusterSlotsAssigned;
    }

    public int getClusterSlotsOk() {
        return clusterSlotsOk;
    }

    public void setClusterSlotsOk(int clusterSlotsOk) {
        this.clusterSlotsOk = clusterSlotsOk;
    }

    public int getClusterSlotsPfail() {
        return clusterSlotsPfail;
    }

    public void setClusterSlotsPfail(int clusterSlotsPfail) {
        this.clusterSlotsPfail = clusterSlotsPfail;
    }

    public int getClusterSlotsFail() {
        return clusterSlotsFail;
    }

    public void setClusterSlotsFail(int clusterSlotsFail) {
        this.clusterSlotsFail = clusterSlotsFail;
    }

    public int getClusterKnownNodes() {
        return clusterKnownNodes;
    }

    public void setClusterKnownNodes(int clusterKnownNodes) {
        this.clusterKnownNodes = clusterKnownNodes;
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public int getClusterCurrentEpoch() {
        return clusterCurrentEpoch;
    }

    public void setClusterCurrentEpoch(int clusterCurrentEpoch) {
        this.clusterCurrentEpoch = clusterCurrentEpoch;
    }

    public int getClusterMyEpoch() {
        return clusterMyEpoch;
    }

    public void setClusterMyEpoch(int clusterMyEpoch) {
        this.clusterMyEpoch = clusterMyEpoch;
    }

    public long getClusterStatsMessagesPingSent() {
        return clusterStatsMessagesPingSent;
    }

    public void setClusterStatsMessagesPingSent(long clusterStatsMessagesPingSent) {
        this.clusterStatsMessagesPingSent = clusterStatsMessagesPingSent;
    }

    public long getClusterStatsMessagesPongSent() {
        return clusterStatsMessagesPongSent;
    }

    public void setClusterStatsMessagesPongSent(long clusterStatsMessagesPongSent) {
        this.clusterStatsMessagesPongSent = clusterStatsMessagesPongSent;
    }

    public long getClusterStatsMessagesSent() {
        return clusterStatsMessagesSent;
    }

    public void setClusterStatsMessagesSent(long clusterStatsMessagesSent) {
        this.clusterStatsMessagesSent = clusterStatsMessagesSent;
    }

    public long getClusterStatsMessagesPingReceived() {
        return clusterStatsMessagesPingReceived;
    }

    public void setClusterStatsMessagesPingReceived(long clusterStatsMessagesPingReceived) {
        this.clusterStatsMessagesPingReceived = clusterStatsMessagesPingReceived;
    }

    public long getClusterStatsMessagesPongReceived() {
        return clusterStatsMessagesPongReceived;
    }

    public void setClusterStatsMessagesPongReceived(long clusterStatsMessagesPongReceived) {
        this.clusterStatsMessagesPongReceived = clusterStatsMessagesPongReceived;
    }

    public long getClusterStatsMessagesReceived() {
        return clusterStatsMessagesReceived;
    }

    public void setClusterStatsMessagesReceived(long clusterStatsMessagesReceived) {
        this.clusterStatsMessagesReceived = clusterStatsMessagesReceived;
    }
}
