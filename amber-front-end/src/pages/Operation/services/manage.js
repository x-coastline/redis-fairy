/* eslint-disable indent */
/** cluster 相关请求 */
import { request } from 'ice';
import React from 'react';
import { Icon } from '@alifd/next';

export default {

  async nodeInfo(clusterId, node, section){
    const result = await request({
      url: '/cluster/nodeInfo',
      method: 'POST',
      params: {
        clusterId,
        node,
        section
      }
    });
    return result;
  },

  async configs(clusterId, node){
    const result = await request({
      url: '/cluster/configs',
      method: 'POST',
      params: {
        clusterId,
        node,
      }
    });
    return result;
  },

  // async startNode(node){
  //   const result = await request({
  //     url: '/node/start',
  //     method: 'GET',
  //     data: node
  //   });
  //   return result;
  // },

  async getRedisNodes(clusterId) {
    const result = await request({
      url: `/manage/nodes/${clusterId}`,
      method: 'GET',
    });
    return result;
    // return [
    //   {
    //     id: 'x1',
    //     linkState: 'connected',
    //     flags: 'Master',
    //     inCluster: true,
    //     runState: true,
    //     master: true,
    //     host: '10.16.50.219',
    //     port: 8666,
    //     slotRange: '0-1111',
    //     slotNumber: 1112,
    //     children: [
    //       {
    //         id: "1",
    //         linkState: 'connected',
    //         flags: 'Slave',
    //         inCluster: false,
    //         runState: false,
    //         host: '10.16.50.220',
    //         port: 8777,
    //       },
    //       {
    //         id: "2",
    //         linkState: 'connected',
    //         inCluster: false,
    //         runState: true,
    //         flags: 'Slave',
    //         host: '10.16.50.221',
    //         port: 8777,
    //       },
    //     ],
    //   },
    //   {
    //     id: "x2",
    //     linkState: 'connected',
    //     flags: 'Master',
    //     inCluster: true,
    //     runState: false,
    //     master: true,
    //     host: '10.16.50.223',
    //     port: 8666,
    //     slotRange: '1112-3232',
    //     slotNumber: 2120,
    //   },
    // ];
  },

  async addCluster(cluster, callback) {
    console.log('========')
    const result = await request({
      url: '/cluster/nodes/1',
      method: 'POST',
      data: cluster
    });
    return callback(result)
  },

  // 格式化返回值
  // async getDetail(params) {
  //     const data = await request({
  //         url: `/api/detail`,
  //         params,
  //     });

  //     return data.map(item => {
  //         return {
  //             ...item,
  //             price: item.oldPrice,
  //             text: item.status === '1' ? '确定' : '取消'
  //         };
  //     });
  // }
}
