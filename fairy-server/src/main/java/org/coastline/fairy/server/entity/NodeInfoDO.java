package org.coastline.fairy.server.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coastline.fairy.client.model.RedisRole;

import java.sql.Timestamp;

/**
 * Important: 此类中的字段并非与 info 命令返回的字段一一对应，有些做过计算或截断
 * <p>
 * Redis Info
 * Server, Clients, Memory, Persistence, Stats, Replication, CPU, Cluster, Keyspace
 * <p>
 * <p>
 * Monitor metrics:
 * <p>
 * response_time: √
 * <p>
 * connected_clients: √
 * blocked_clients: √
 * <p>
 * mem_fragmentation_ratio: √
 * used_memory: √
 * used_memory_rss: √
 * used_memory_dataset: √
 * <p>
 * total_connections_received: √
 * total_commands_processed: √
 * instantaneous_ops_per_sec: √
 * rejected_connections: √
 * sync_full: √
 * sync_partial_ok: √
 * sync_partial_err: √
 * <p>
 * keys: √
 * expires: √
 * <p>
 * keyspace_hits_ratio:  √
 * <p>
 * used_cpu_sys: √
 * Scalable
 *
 * @author Jay.H.Zou
 * @date 2019/7/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeInfoDO {

    private Integer infoId;

    private String node;

    private RedisRole role;

    private boolean lastTime;

    /*======================================= clients =======================================*/
    private long connectedClients;
    private long clientLongestOutputList;
    private long clientBiggestInputBuf;
    private long blockedClients;

    /*======================================= memory =======================================*/
    /**
     * 由 Redis 分配器分配的内存总量，以字节（byte）为单位
     * usedMemory = used_memory / 1024 / 1024
     */
    private long usedMemory;

    /**
     * 从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）。这个值和 top 、 ps等命令的输出一致
     * usedMemoryRss = used_memory_rss / 1024 / 1024
     */
    private long usedMemoryRss;

    /**
     * Redis为了维护数据集的内部机制所需的内存开销，包括所有客户端输出缓冲区、查询缓冲区、AOF重写缓冲区和主从复制的backlog
     * usedMemoryOverhead = used_memory_overhead / 1024 / 1024
     */
    private long usedMemoryOverhead;

    /**
     * usedMemoryDataset = (used_memory - used_memory_overhead) / 1024 / 1024
     */
    private long usedMemoryDataset;

    /**
     * 100 * (used_memory_dataset / (used_memory - used_memory_startup))
     */
    private double usedMemoryDatasetPerc;

    private double memFragmentationRatio;

    /***********************  Stats ******************************/
    /**
     * 新创建连接个数,如果新创建连接过多，过度地创建和销毁连接对性能有影响，说明短连接严重或连接池使用有问题，需调研代码的连接设置
     */
    private long totalConnectionsReceived;

    /**
     * redis 每分钟或每小时新建连接数
     * 需计算
     */
    private long connectionsReceived;

    private long rejectedConnections;

    /**
     * redis处理的命令数 (total_commands_processed)
     */
    private long totalCommandsProcessed;

    /**
     * redis 每分钟或每小时处理的命令数
     * 需计算
     */
    private long commandsProcessed;

    /**
     * redis当前的qps (instantaneous_ops_per_sec): redis内部较实时的每秒执行的命令数
     */
    private long instantaneousOpsPerSec;
    /**
     * redis 网络入口流量字节数
     */
    private long totalNetInputBytes;

    /**
     * redis 每分钟或每小时网络入口流量字节数
     * 需计算
     */
    private long netInputBytes;

    /**
     * redis 网络出口流量字节数
     */
    private long totalNetOutputBytes;

    /**
     * redis 每分钟或每小时网络出口流量字节数
     * 需计算
     */
    private long netOutputBytes;

    /**
     * keyspace_hits
     */
    private long keyspaceHits;

    /**
     * keyspace_misses
     */
    private long keyspaceMisses;

    // 每分钟 key 命中率
    private double keyspaceHitsRatio;

    /***********************  Replication ******************************/
    // 计算主从复制情况

    private long syncFull;

    private long syncPartialOk;

    private long syncPartialErr;

    // 计算出每分钟的差值
    private long syncFullMin;

    private long syncPartialOkMin;

    private long syncPartialErrMin;

    /***********************  CPU ******************************/
    private double usedCpuSys;

    private double cpuSys;

    private double usedCpuUser;

    private double cpuUser;

    /**=====================================  Keyspace =====================================*/
    /**
     * 当前节点所有db的 key 总数
     */
    private long keys;

    private long expires;

    private long totalSlowLog;

    private long slowLog;

    private Timestamp creationTime;
}
