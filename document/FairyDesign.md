企业级 redis 开发、运维平台
# 核心功能
监控
管理
创建
告警
工具集
## 监控
### 问题&改进
数据收集不及时，靠定时器收集太慢切不不够精准  
数据展示不流畅（数据量，查询需要优化）  
监控与告警联动不足  
节点关系不够清晰，不够人性化（显示层级关系）  
slow log 需要添加告警  
slow log 需要定时收集并绘制趋势图

### 功能点
#### 监控指标 
* 集群状态
* INFO 指标
* NODE 状态
* 集群结构变化
* 集群结构不合理给出提示（例如：Master and Replica are on same IP）
* 集群重要变化（bgsave, rewrite aof, failover, fullsync...）
* CLIENT LIST 监控，实时->持久化
* SLOW LOG 监控，实时->持久化
* BIG KEYS 分析
* 性能监测（--latency）

#### 监控方式
* 历史数据图表展示（优化存储读取，优化网络消耗，数据聚合）
* 实时图表展示：用于暂时性监控当前集群秒级别 INFO 指标变化趋势


## Manage
* Auto Rebalance Cluster Slot
* Manual Resharding(Assign Slot)
* Rebuild Cluster
* Destroy Cluster
* Add Node
* Forget Node
* Replicate Of
* FailOver
* MemoryPurge
* Start, Stop, Restart, Delete
* Config Manage(ReWrite, Refresh) 批量+单个
* 内存使用百分比展示
* 自动伸缩（暂定）


## Installation
* 自动安装(环境监测 & 资源监测)
* 可断点恢复
* 安装日志持久化
* 安装失败可自动销毁/修复
* 机器管理（资源管理，支持多种方式访问）
* shell 操作方案

## Alert
* Info, Node, Cluster 级别告警
* 阈值，百分比，趋势告警
* 告警模板
* 邮件告警、微信告警、钉钉告警


## Data Operation
* 可视化操作数据
* scan 操作
* 大集合 scan 操作
* 命令行操作（参考 redisinsight）
* 集群间导数据
* DB 导数据

## System
### Statistics
* 资源分析
* 报表生成
* 提供申请，运维，伸缩，修改等完善的处理流程

### 权限系统
* 提供健全的、较为细致的权限系统


## 其他问题
* Token 使用方式