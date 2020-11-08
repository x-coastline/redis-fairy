/* eslint-disable indent */
/** node 相关请求 */
import { request } from 'ice';

const ITEM_MAP = {
  used_memory: 'usedMemory',
  used_memory_rss: 'usedMemoryRss',
  used_memory_overhead: 'usedMemoryOverhead',
  used_memory_dataset: 'usedMemoryDataset',
  mem_fragmentation_ratio: 'memFragmentationRatio',
  connections_received: 'connectionsReceived',
  rejected_connections: 'rejecteConnections',
  connected_clients: 'connectedClients',
  blocked_clients: 'blockedClients',
  commands_processed: 'commandsProcessed',
  instantaneous_ops_per_sec: 'instantaneousOpsPerSec',
  sync_full: 'syncFull',
  sync_partial_ok: 'syncPartialOk',
  sync_partial_err: 'syncPartialErr',
  keyspace_hits_ratio: 'keyspaceHitsRatio',
  keys: 'keys',
  expires: 'expires',
  cpu_sys: 'cpuSys',
  cpu_user: 'cpuUser'
}
export default {
  /**
   * 获取 node info 
   * @param nodeInfoParam 
   */
  async listInfo(nodeInfoParam) {
    const result = await request({
      url: '/monitor/info/item',
      method: 'POST',
      data: nodeInfoParam
    });
    return result;
  },

  /**
   * 获取 slow log
   * @param slowLogParam 
   */
  async listSlowLog(slowLogParam) {
    const result = await request({
      url: '/monitor/slowlog/list',
      method: 'POST',
      data: slowLogParam
    });
    return result;
  },

  async getRealTimeInfoList(nodeInfoParam) {
    const result = await request({
      url: '/monitor/info/real',
      method: 'POST',
      data: nodeInfoParam
    });
    return result;
  },

  mapUnderscoreToCamelCase(item) {
    return ITEM_MAP[item];
  },

  async listClients(clientListParam) {
    const result = await request({
      url: '/monitor/client/list',
      method: 'POST',
      data: clientListParam
    });
    return result;
  },
}