/* eslint-disable indent */
/** cluster 相关请求 */
import { request } from 'ice';
import React from 'react';
import { Icon } from '@alifd/next';

export default {

    // 获取统计列表
    async getStatisticsList(groupId) {
        return [
            {
                title: 'Health Cluster',
                number: 123,
                increasement: 3,
                icon: <Icon type="smile" size='xxxl' style={{ color: '#36CFC9' }} />
            },
            {
                title: 'Bad Cluster',
                number: 2,
                increasement: 1,
                icon: <Icon type="cry" size='xxxl' style={{ color: '#f4516c' }} />
            }, {
                title: 'All Keys',
                number: '259,416,539',
                increasement: 564523,
                icon: <Icon type="chart-bar" size='xxxl' style={{ color: '#36a3f7' }} />
            },
            {
                title: 'All Memory',
                number: 61.6,
                increasement: 1.5,
                icon: <Icon type="chart-pie" size='xxxl' style={{ color: '#fbd437' }} />
            }
        ]
        //return await request(`/cluster/list/${groupId}`);
    },

    // 获取集群列表
    async getClusterList(groupId) {
        // return [
        //     {
        //         clusterId: 122,
        //         clusterName: 'asgard-snapshot-e4',
        //         redisMode: 'cluster',
        //         state: 'health',
        //         nodeNumber: 20,
        //         masterNumber: 10,
        //         version: '4.0.14',
        //         enviroment: 'docker',
        //         from: 'import',
        //         requirePass: true
        //     }
        // ]
        return await request(`/cluster/list/${groupId}`);
    },

    // 参数场景
    async getCluster(clusterId) {
        return await request(`/cluster/get/${clusterId}`);
    },

    async addCluster(cluster, callback) {
        const result = await request({
            url: '/cluster/add',
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
