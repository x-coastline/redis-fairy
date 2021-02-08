package org.coastline.fairy.client.model;

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

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public long getKeys() {
            return keys;
        }

        public void setKeys(long keys) {
            this.keys = keys;
        }

        public long getExpires() {
            return expires;
        }

        public void setExpires(long expires) {
            this.expires = expires;
        }

        public long getAvgTtl() {
            return avgTtl;
        }

        public void setAvgTtl(long avgTtl) {
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

        public String getCommandType() {
            return commandType;
        }

        public void setCommandType(String commandType) {
            this.commandType = commandType;
        }

        public long getCalls() {
            return calls;
        }

        public void setCalls(long calls) {
            this.calls = calls;
        }

        public long getUsec() {
            return usec;
        }

        public void setUsec(long usec) {
            this.usec = usec;
        }

        public long getUsecPerCall() {
            return usecPerCall;
        }

        public void setUsecPerCall(long usecPerCall) {
            this.usecPerCall = usecPerCall;
        }
    }

    public String getRedisVersion() {
        return redisVersion;
    }

    public void setRedisVersion(String redisVersion) {
        this.redisVersion = redisVersion;
    }

    public String getRedisGitSha1() {
        return redisGitSha1;
    }

    public void setRedisGitSha1(String redisGitSha1) {
        this.redisGitSha1 = redisGitSha1;
    }

    public int getRedisGitDirty() {
        return redisGitDirty;
    }

    public void setRedisGitDirty(int redisGitDirty) {
        this.redisGitDirty = redisGitDirty;
    }

    public String getRedisBuildId() {
        return redisBuildId;
    }

    public void setRedisBuildId(String redisBuildId) {
        this.redisBuildId = redisBuildId;
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

    public int getArchBits() {
        return archBits;
    }

    public void setArchBits(int archBits) {
        this.archBits = archBits;
    }

    public String getMultiplexingApi() {
        return multiplexingApi;
    }

    public void setMultiplexingApi(String multiplexingApi) {
        this.multiplexingApi = multiplexingApi;
    }

    public String getGccVersion() {
        return gccVersion;
    }

    public void setGccVersion(String gccVersion) {
        this.gccVersion = gccVersion;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public long getUptimeInSeconds() {
        return uptimeInSeconds;
    }

    public void setUptimeInSeconds(long uptimeInSeconds) {
        this.uptimeInSeconds = uptimeInSeconds;
    }

    public long getUptimeInDays() {
        return uptimeInDays;
    }

    public void setUptimeInDays(long uptimeInDays) {
        this.uptimeInDays = uptimeInDays;
    }

    public int getHz() {
        return hz;
    }

    public void setHz(int hz) {
        this.hz = hz;
    }

    public long getLruClock() {
        return lruClock;
    }

    public void setLruClock(long lruClock) {
        this.lruClock = lruClock;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public long getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(long connectedClients) {
        this.connectedClients = connectedClients;
    }

    public long getClientLongestOutputList() {
        return clientLongestOutputList;
    }

    public void setClientLongestOutputList(long clientLongestOutputList) {
        this.clientLongestOutputList = clientLongestOutputList;
    }

    public long getClientBiggestInputBuf() {
        return clientBiggestInputBuf;
    }

    public void setClientBiggestInputBuf(long clientBiggestInputBuf) {
        this.clientBiggestInputBuf = clientBiggestInputBuf;
    }

    public long getBlockedClients() {
        return blockedClients;
    }

    public void setBlockedClients(long blockedClients) {
        this.blockedClients = blockedClients;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public double getUsedMemoryHuman() {
        return usedMemoryHuman;
    }

    public void setUsedMemoryHuman(double usedMemoryHuman) {
        this.usedMemoryHuman = usedMemoryHuman;
    }

    public double getUsedMemoryRss() {
        return usedMemoryRss;
    }

    public void setUsedMemoryRss(double usedMemoryRss) {
        this.usedMemoryRss = usedMemoryRss;
    }

    public double getUsedMemoryRssHuman() {
        return usedMemoryRssHuman;
    }

    public void setUsedMemoryRssHuman(double usedMemoryRssHuman) {
        this.usedMemoryRssHuman = usedMemoryRssHuman;
    }

    public double getUsedMemoryPeak() {
        return usedMemoryPeak;
    }

    public void setUsedMemoryPeak(double usedMemoryPeak) {
        this.usedMemoryPeak = usedMemoryPeak;
    }

    public double getUsedMemoryPeakHuman() {
        return usedMemoryPeakHuman;
    }

    public void setUsedMemoryPeakHuman(double usedMemoryPeakHuman) {
        this.usedMemoryPeakHuman = usedMemoryPeakHuman;
    }

    public double getUsedMemoryPeakPerc() {
        return usedMemoryPeakPerc;
    }

    public void setUsedMemoryPeakPerc(double usedMemoryPeakPerc) {
        this.usedMemoryPeakPerc = usedMemoryPeakPerc;
    }

    public double getUsedMemoryOverhead() {
        return usedMemoryOverhead;
    }

    public void setUsedMemoryOverhead(double usedMemoryOverhead) {
        this.usedMemoryOverhead = usedMemoryOverhead;
    }

    public double getUsedMemoryStartup() {
        return usedMemoryStartup;
    }

    public void setUsedMemoryStartup(double usedMemoryStartup) {
        this.usedMemoryStartup = usedMemoryStartup;
    }

    public double getUsedMemoryDataset() {
        return usedMemoryDataset;
    }

    public void setUsedMemoryDataset(double usedMemoryDataset) {
        this.usedMemoryDataset = usedMemoryDataset;
    }

    public double getUsedMemoryDatasetPerc() {
        return usedMemoryDatasetPerc;
    }

    public void setUsedMemoryDatasetPerc(double usedMemoryDatasetPerc) {
        this.usedMemoryDatasetPerc = usedMemoryDatasetPerc;
    }

    public double getTotalSystemMemory() {
        return totalSystemMemory;
    }

    public void setTotalSystemMemory(double totalSystemMemory) {
        this.totalSystemMemory = totalSystemMemory;
    }

    public double getTotalSystemMemoryHuman() {
        return totalSystemMemoryHuman;
    }

    public void setTotalSystemMemoryHuman(double totalSystemMemoryHuman) {
        this.totalSystemMemoryHuman = totalSystemMemoryHuman;
    }

    public double getUsedMemoryLua() {
        return usedMemoryLua;
    }

    public void setUsedMemoryLua(double usedMemoryLua) {
        this.usedMemoryLua = usedMemoryLua;
    }

    public double getUsedMemoryLuaHuman() {
        return usedMemoryLuaHuman;
    }

    public void setUsedMemoryLuaHuman(double usedMemoryLuaHuman) {
        this.usedMemoryLuaHuman = usedMemoryLuaHuman;
    }

    public double getMaxmemory() {
        return maxmemory;
    }

    public void setMaxmemory(double maxmemory) {
        this.maxmemory = maxmemory;
    }

    public double getMaxmemoryHuman() {
        return maxmemoryHuman;
    }

    public void setMaxmemoryHuman(double maxmemoryHuman) {
        this.maxmemoryHuman = maxmemoryHuman;
    }

    public String getMaxmemoryPolicy() {
        return maxmemoryPolicy;
    }

    public void setMaxmemoryPolicy(String maxmemoryPolicy) {
        this.maxmemoryPolicy = maxmemoryPolicy;
    }

    public double getMemFragmentationRatio() {
        return memFragmentationRatio;
    }

    public void setMemFragmentationRatio(double memFragmentationRatio) {
        this.memFragmentationRatio = memFragmentationRatio;
    }

    public String getMemAllocator() {
        return memAllocator;
    }

    public void setMemAllocator(String memAllocator) {
        this.memAllocator = memAllocator;
    }

    public boolean isActiveDefragRunning() {
        return activeDefragRunning;
    }

    public void setActiveDefragRunning(boolean activeDefragRunning) {
        this.activeDefragRunning = activeDefragRunning;
    }

    public double getLazyfreePendingObjects() {
        return lazyfreePendingObjects;
    }

    public void setLazyfreePendingObjects(double lazyfreePendingObjects) {
        this.lazyfreePendingObjects = lazyfreePendingObjects;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public long getRdbChangesSinceLastSave() {
        return rdbChangesSinceLastSave;
    }

    public void setRdbChangesSinceLastSave(long rdbChangesSinceLastSave) {
        this.rdbChangesSinceLastSave = rdbChangesSinceLastSave;
    }

    public boolean isRdbBgsaveInProgress() {
        return rdbBgsaveInProgress;
    }

    public void setRdbBgsaveInProgress(boolean rdbBgsaveInProgress) {
        this.rdbBgsaveInProgress = rdbBgsaveInProgress;
    }

    public long getRdbLastSaveTime() {
        return rdbLastSaveTime;
    }

    public void setRdbLastSaveTime(long rdbLastSaveTime) {
        this.rdbLastSaveTime = rdbLastSaveTime;
    }

    public String getRdbLastBgsaveStatus() {
        return rdbLastBgsaveStatus;
    }

    public void setRdbLastBgsaveStatus(String rdbLastBgsaveStatus) {
        this.rdbLastBgsaveStatus = rdbLastBgsaveStatus;
    }

    public long getRdbLastBgsaveTimeSec() {
        return rdbLastBgsaveTimeSec;
    }

    public void setRdbLastBgsaveTimeSec(long rdbLastBgsaveTimeSec) {
        this.rdbLastBgsaveTimeSec = rdbLastBgsaveTimeSec;
    }

    public long getRdbCurrentBgsaveTimeSec() {
        return rdbCurrentBgsaveTimeSec;
    }

    public void setRdbCurrentBgsaveTimeSec(long rdbCurrentBgsaveTimeSec) {
        this.rdbCurrentBgsaveTimeSec = rdbCurrentBgsaveTimeSec;
    }

    public long getRdbLastCowSize() {
        return rdbLastCowSize;
    }

    public void setRdbLastCowSize(long rdbLastCowSize) {
        this.rdbLastCowSize = rdbLastCowSize;
    }

    public boolean isAofEnabled() {
        return aofEnabled;
    }

    public void setAofEnabled(boolean aofEnabled) {
        this.aofEnabled = aofEnabled;
    }

    public boolean isAofRewriteInProgress() {
        return aofRewriteInProgress;
    }

    public void setAofRewriteInProgress(boolean aofRewriteInProgress) {
        this.aofRewriteInProgress = aofRewriteInProgress;
    }

    public boolean isAofRewriteScheduled() {
        return aofRewriteScheduled;
    }

    public void setAofRewriteScheduled(boolean aofRewriteScheduled) {
        this.aofRewriteScheduled = aofRewriteScheduled;
    }

    public long getAofLastRewriteTimeSec() {
        return aofLastRewriteTimeSec;
    }

    public void setAofLastRewriteTimeSec(long aofLastRewriteTimeSec) {
        this.aofLastRewriteTimeSec = aofLastRewriteTimeSec;
    }

    public long getAofCurrentRewriteTimeSec() {
        return aofCurrentRewriteTimeSec;
    }

    public void setAofCurrentRewriteTimeSec(long aofCurrentRewriteTimeSec) {
        this.aofCurrentRewriteTimeSec = aofCurrentRewriteTimeSec;
    }

    public String getAofLastBgrewriteStatus() {
        return aofLastBgrewriteStatus;
    }

    public void setAofLastBgrewriteStatus(String aofLastBgrewriteStatus) {
        this.aofLastBgrewriteStatus = aofLastBgrewriteStatus;
    }

    public String getAofLastWriteStatus() {
        return aofLastWriteStatus;
    }

    public void setAofLastWriteStatus(String aofLastWriteStatus) {
        this.aofLastWriteStatus = aofLastWriteStatus;
    }

    public long getAofLastCowSize() {
        return aofLastCowSize;
    }

    public void setAofLastCowSize(long aofLastCowSize) {
        this.aofLastCowSize = aofLastCowSize;
    }

    public long getTotalConnectionsReceived() {
        return totalConnectionsReceived;
    }

    public void setTotalConnectionsReceived(long totalConnectionsReceived) {
        this.totalConnectionsReceived = totalConnectionsReceived;
    }

    public long getTotalCommandsProcessed() {
        return totalCommandsProcessed;
    }

    public void setTotalCommandsProcessed(long totalCommandsProcessed) {
        this.totalCommandsProcessed = totalCommandsProcessed;
    }

    public long getInstantaneousOpsPerSec() {
        return instantaneousOpsPerSec;
    }

    public void setInstantaneousOpsPerSec(long instantaneousOpsPerSec) {
        this.instantaneousOpsPerSec = instantaneousOpsPerSec;
    }

    public long getTotalNetInputBytes() {
        return totalNetInputBytes;
    }

    public void setTotalNetInputBytes(long totalNetInputBytes) {
        this.totalNetInputBytes = totalNetInputBytes;
    }

    public long getTotalNetOutputBytes() {
        return totalNetOutputBytes;
    }

    public void setTotalNetOutputBytes(long totalNetOutputBytes) {
        this.totalNetOutputBytes = totalNetOutputBytes;
    }

    public double getInstantaneousInputKbps() {
        return instantaneousInputKbps;
    }

    public void setInstantaneousInputKbps(double instantaneousInputKbps) {
        this.instantaneousInputKbps = instantaneousInputKbps;
    }

    public double getInstantaneousOutputKbps() {
        return instantaneousOutputKbps;
    }

    public void setInstantaneousOutputKbps(double instantaneousOutputKbps) {
        this.instantaneousOutputKbps = instantaneousOutputKbps;
    }

    public long getRejectedConnections() {
        return rejectedConnections;
    }

    public void setRejectedConnections(long rejectedConnections) {
        this.rejectedConnections = rejectedConnections;
    }

    public long getSyncFull() {
        return syncFull;
    }

    public void setSyncFull(long syncFull) {
        this.syncFull = syncFull;
    }

    public long getSyncPartialOk() {
        return syncPartialOk;
    }

    public void setSyncPartialOk(long syncPartialOk) {
        this.syncPartialOk = syncPartialOk;
    }

    public long getSyncPartialErr() {
        return syncPartialErr;
    }

    public void setSyncPartialErr(long syncPartialErr) {
        this.syncPartialErr = syncPartialErr;
    }

    public long getExpiredKeys() {
        return expiredKeys;
    }

    public void setExpiredKeys(long expiredKeys) {
        this.expiredKeys = expiredKeys;
    }

    public double getExpiredStalePerc() {
        return expiredStalePerc;
    }

    public void setExpiredStalePerc(double expiredStalePerc) {
        this.expiredStalePerc = expiredStalePerc;
    }

    public long getExpiredTimeCapReachedCount() {
        return expiredTimeCapReachedCount;
    }

    public void setExpiredTimeCapReachedCount(long expiredTimeCapReachedCount) {
        this.expiredTimeCapReachedCount = expiredTimeCapReachedCount;
    }

    public long getEvictedKeys() {
        return evictedKeys;
    }

    public void setEvictedKeys(long evictedKeys) {
        this.evictedKeys = evictedKeys;
    }

    public long getKeyspaceHits() {
        return keyspaceHits;
    }

    public void setKeyspaceHits(long keyspaceHits) {
        this.keyspaceHits = keyspaceHits;
    }

    public long getKeyspaceMisses() {
        return keyspaceMisses;
    }

    public void setKeyspaceMisses(long keyspaceMisses) {
        this.keyspaceMisses = keyspaceMisses;
    }

    public long getPubsubChannels() {
        return pubsubChannels;
    }

    public void setPubsubChannels(long pubsubChannels) {
        this.pubsubChannels = pubsubChannels;
    }

    public long getPubsubPatterns() {
        return pubsubPatterns;
    }

    public void setPubsubPatterns(long pubsubPatterns) {
        this.pubsubPatterns = pubsubPatterns;
    }

    public long getLatestForkUsec() {
        return latestForkUsec;
    }

    public void setLatestForkUsec(long latestForkUsec) {
        this.latestForkUsec = latestForkUsec;
    }

    public long getMigrateCachedSockets() {
        return migrateCachedSockets;
    }

    public void setMigrateCachedSockets(long migrateCachedSockets) {
        this.migrateCachedSockets = migrateCachedSockets;
    }

    public long getSlaveExpiresTrackedKeys() {
        return slaveExpiresTrackedKeys;
    }

    public void setSlaveExpiresTrackedKeys(long slaveExpiresTrackedKeys) {
        this.slaveExpiresTrackedKeys = slaveExpiresTrackedKeys;
    }

    public long getActiveDefragHits() {
        return activeDefragHits;
    }

    public void setActiveDefragHits(long activeDefragHits) {
        this.activeDefragHits = activeDefragHits;
    }

    public long getActiveDefragMisses() {
        return activeDefragMisses;
    }

    public void setActiveDefragMisses(long activeDefragMisses) {
        this.activeDefragMisses = activeDefragMisses;
    }

    public long getActiveDefragKeyHits() {
        return activeDefragKeyHits;
    }

    public void setActiveDefragKeyHits(long activeDefragKeyHits) {
        this.activeDefragKeyHits = activeDefragKeyHits;
    }

    public long getActiveDefragKeyMisses() {
        return activeDefragKeyMisses;
    }

    public void setActiveDefragKeyMisses(long activeDefragKeyMisses) {
        this.activeDefragKeyMisses = activeDefragKeyMisses;
    }

    public RedisRole getRole() {
        return role;
    }

    public void setRole(RedisRole role) {
        this.role = role;
    }

    public int getConnectedSlaves() {
        return connectedSlaves;
    }

    public void setConnectedSlaves(int connectedSlaves) {
        this.connectedSlaves = connectedSlaves;
    }

    public List<String> getReplicas() {
        return replicas;
    }

    public void setReplicas(List<String> replicas) {
        this.replicas = replicas;
    }

    public String getMasterReplid() {
        return masterReplid;
    }

    public void setMasterReplid(String masterReplid) {
        this.masterReplid = masterReplid;
    }

    public String getMasterReplid2() {
        return masterReplid2;
    }

    public void setMasterReplid2(String masterReplid2) {
        this.masterReplid2 = masterReplid2;
    }

    public long getMasterReplOffset() {
        return masterReplOffset;
    }

    public void setMasterReplOffset(long masterReplOffset) {
        this.masterReplOffset = masterReplOffset;
    }

    public boolean isReplBacklogActive() {
        return replBacklogActive;
    }

    public void setReplBacklogActive(boolean replBacklogActive) {
        this.replBacklogActive = replBacklogActive;
    }

    public long getReplBacklogSize() {
        return replBacklogSize;
    }

    public void setReplBacklogSize(long replBacklogSize) {
        this.replBacklogSize = replBacklogSize;
    }

    public long getReplBacklogFirstByteOffset() {
        return replBacklogFirstByteOffset;
    }

    public void setReplBacklogFirstByteOffset(long replBacklogFirstByteOffset) {
        this.replBacklogFirstByteOffset = replBacklogFirstByteOffset;
    }

    public long getReplBacklogHistlen() {
        return replBacklogHistlen;
    }

    public void setReplBacklogHistlen(long replBacklogHistlen) {
        this.replBacklogHistlen = replBacklogHistlen;
    }

    public double getUsedCpuSys() {
        return usedCpuSys;
    }

    public void setUsedCpuSys(double usedCpuSys) {
        this.usedCpuSys = usedCpuSys;
    }

    public double getUsedCpuUser() {
        return usedCpuUser;
    }

    public void setUsedCpuUser(double usedCpuUser) {
        this.usedCpuUser = usedCpuUser;
    }

    public double getUsedCpuSysChildren() {
        return usedCpuSysChildren;
    }

    public void setUsedCpuSysChildren(double usedCpuSysChildren) {
        this.usedCpuSysChildren = usedCpuSysChildren;
    }

    public double getUsedCpuUserChildren() {
        return usedCpuUserChildren;
    }

    public void setUsedCpuUserChildren(double usedCpuUserChildren) {
        this.usedCpuUserChildren = usedCpuUserChildren;
    }

    public List<RedisCommandStat> getCommandStats() {
        return commandStats;
    }

    public void setCommandStats(List<RedisCommandStat> commandStats) {
        this.commandStats = commandStats;
    }

    public boolean isClusterEnabled() {
        return clusterEnabled;
    }

    public void setClusterEnabled(boolean clusterEnabled) {
        this.clusterEnabled = clusterEnabled;
    }

    public List<RedisKeyspace> getKeyspaceList() {
        return keyspaceList;
    }

    public void setKeyspaceList(List<RedisKeyspace> keyspaceList) {
        this.keyspaceList = keyspaceList;
    }
}
