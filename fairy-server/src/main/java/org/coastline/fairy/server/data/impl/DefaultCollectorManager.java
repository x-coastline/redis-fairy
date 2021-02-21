package org.coastline.fairy.server.data.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.coastline.fairy.server.config.SystemConfig;
import org.coastline.fairy.server.data.ICollector;
import org.coastline.fairy.server.data.ICollectorManager;
import org.coastline.fairy.server.entity.ClusterDO;
import org.coastline.fairy.server.service.IClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.*;

/**
 * info: 根据不同模式选择不同的数据收集方式
 * node，cluster:
 * 定时线程池, 获取所有集群，每个集群分配一个线程进行更新
 *
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class DefaultCollectorManager implements ICollectorManager {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private IClusterService clusterService;

    @Autowired
    private NodeCollector nodeCollector;

    private ScheduledThreadPoolExecutor nodeScheduled;

    private ScheduledThreadPoolExecutor clusterScheduled;

    private ScheduledThreadPoolExecutor infoScheduled;


    private ExecutorService nodeThreadPool;

    private ExecutorService clusterThreadPool;

    private ExecutorService infoThreadPool;

    @Override
    public void initCollector() {
        int schedule = 1;
        nodeScheduled = new ScheduledThreadPoolExecutor(1);
        clusterScheduled = new ScheduledThreadPoolExecutor(1);
        if (SystemConfig.DEFAULT_MODEL.equals(systemConfig.getModel())) {
            infoScheduled = new ScheduledThreadPoolExecutor(1);
        }

        nodeScheduled.scheduleAtFixedRate(() -> {
            List<ClusterDO> clusterList = clusterService.getAllCluster();
            for (ClusterDO cluster : clusterList) {

            }
        }, 0, schedule, TimeUnit.MINUTES);

        nodeScheduled.scheduleAtFixedRate(() -> {
            List<ClusterDO> clusterList = clusterService.getAllCluster();
            for (ClusterDO cluster : clusterList) {

            }
        }, 0, schedule, TimeUnit.MINUTES);
    }



}
