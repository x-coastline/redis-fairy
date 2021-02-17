package org.coastline.fairy.client.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
 *
 * @author Jay.H.Zou
 * @date 2020/10/8
 */
@Data
@Builder
@NoArgsConstructor
public class RedisInfo {

    public static final String SERVER = "server";

    /*======================================= server =======================================*/
    private String redisVersion;
    private String redisGitSha1;
    private int redisGitDirty;
    private String redisBuildId;
    private RedisMode redisMode;
    private String os;
    private int archBits;
    private String multiplexingApi;
    private String gccVersion;
    private int processId;
    private String runId;
    private int tcpPort;
    private long uptimeInSeconds;
    private long uptimeInDays;
    private int hz;
    private long lruClock;
    private String configFile;

    /*======================================= clients =======================================*/
    private long connectedClients;
    private long clientLongestOutputList;
    private long clientBiggestInputBuf;
    private long blockedClients;

    /*======================================= memory =======================================*/
    private double usedMemory;
    private double usedMemoryHuman;
    private double usedMemoryRss;
    private double usedMemoryRssHuman;
    private double usedMemoryPeak;
    private double usedMemoryPeakHuman;
    private double usedMemoryPeakPerc;
    private double usedMemoryOverhead;
    private double usedMemoryStartup;
    private double usedMemoryDataset;
    private double usedMemoryDatasetPerc;
    private double totalSystemMemory;
    private double totalSystemMemoryHuman;
    private double usedMemoryLua;
    private double usedMemoryLuaHuman;
    private double maxmemory;
    private double maxmemoryHuman;
    private String maxmemoryPolicy;
    private double memFragmentationRatio;
    private String memAllocator;
    private boolean activeDefragRunning;
    private double lazyfreePendingObjects;

    /*======================================= persistence =======================================*/
    private boolean loading;
    private long rdbChangesSinceLastSave;
    private boolean rdbBgsaveInProgress;
    private long rdbLastSaveTime;
    private String rdbLastBgsaveStatus;
    private long rdbLastBgsaveTimeSec;
    private long rdbCurrentBgsaveTimeSec;
    private long rdbLastCowSize;
    private boolean aofEnabled;
    private boolean aofRewriteInProgress;
    private boolean aofRewriteScheduled;
    private long aofLastRewriteTimeSec;
    private long aofCurrentRewriteTimeSec;
    private String aofLastBgrewriteStatus;
    private String aofLastWriteStatus;
    private long aofLastCowSize;

    /*======================================= stats =======================================*/
    private long totalConnectionsReceived;
    private long totalCommandsProcessed;
    private long instantaneousOpsPerSec;
    private long totalNetInputBytes;
    private long totalNetOutputBytes;
    private double instantaneousInputKbps;
    private double instantaneousOutputKbps;
    private long rejectedConnections;
    private long syncFull;
    private long syncPartialOk;
    private long syncPartialErr;
    private long expiredKeys;
    private double expiredStalePerc;
    private long expiredTimeCapReachedCount;
    private long evictedKeys;
    private long keyspaceHits;
    private long keyspaceMisses;
    private long pubsubChannels;
    private long pubsubPatterns;
    private long latestForkUsec;
    private long migrateCachedSockets;
    private long slaveExpiresTrackedKeys;
    private long activeDefragHits;
    private long activeDefragMisses;
    private long activeDefragKeyHits;
    private long activeDefragKeyMisses;


    /*======================================= replication =======================================*/
    private RedisRole role;
    private int connectedSlaves;
    List<String> replicas;
    private String masterReplid;
    private String masterReplid2;
    private long masterReplOffset;
    private boolean replBacklogActive;
    private long replBacklogSize;
    private long replBacklogFirstByteOffset;
    private long replBacklogHistlen;

    /*======================================= cpu =======================================*/
    private double usedCpuSys;
    private double usedCpuUser;
    private double usedCpuSysChildren;
    private double usedCpuUserChildren;

    /*======================================= commandstats =======================================*/
    private List<RedisCommandStat> commandStats;

    /*======================================= cluster =======================================*/
    private boolean clusterEnabled;
    /*======================================= keyspace =======================================*/
    private List<RedisKeyspace> keyspaceList;

    /**
     * keyspace
     * db0:keys=1,expires=0,avg_ttl=0
     */
    @Data
    @Builder
    @NoArgsConstructor
    public static class RedisKeyspace {
        private int database;

        private long keys;

        private long expires;

        private long avgTtl;

        public RedisKeyspace(int database, long keys, long expires, long avgTtl) {
            this.database = database;
            this.keys = keys;
            this.expires = expires;
            this.avgTtl = avgTtl;
        }
    }

    /**
     * command
     * cmdstat_client:calls=23645,usec=19649,usec_per_call=0.83
     * cmdstat_info:calls=9465,usec=151347,usec_per_call=15.99
     * cmdstat_ping:calls=23642,usec=5908,usec_per_call=0.25
     * cmdstat_psync:calls=1,usec=8265,usec_per_call=8265.00
     * cmdstat_replconf:calls=140764,usec=140604,usec_per_call=1.00
     * cmdstat_command:calls=1,usec=32711,usec_per_call=32711.00
     * cmdstat_scan:calls=2,usec=1323,usec_per_call=661.50
     * cmdstat_select:calls=2,usec=0,usec_per_call=0.00
     * cmdstat_cluster:calls=14184,usec=1683540,usec_per_call=118.69
     */
    @Data
    @Builder
    @NoArgsConstructor
    public static class RedisCommandStat {
        private String commandType;

        /**
         * 命令调用总次数
         */
        private long calls;

        /**
         * 总耗时，单位微秒
         */
        private long usec;

        /**
         * 平均耗时
         */
        private long usecPerCall;

        public RedisCommandStat(String commandType, long calls, long usec, long usecPerCall) {
            this.commandType = commandType;
            this.calls = calls;
            this.usec = usec;
            this.usecPerCall = usecPerCall;
        }

    }

}
