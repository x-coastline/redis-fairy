package org.coastline.fairy.server.data.impl;

import org.coastline.fairy.server.config.SystemConfig;
import org.coastline.fairy.server.data.ICollectorManager;
import org.coastline.fairy.server.entity.ClusterDO;
import org.coastline.fairy.server.service.IClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * info: 根据不同模式选择不同的数据收集方式
 * node，cluster:
 * 定时线程池, 获取所有集群，每个集群分配一个线程进行更新
 *
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class DefaultCollectorManager implements ICollectorManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCollectorManager.class);

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private IClusterService clusterService;

    @Autowired
    private DefaultNodeCollector defaultNodeCollector;

    @Autowired
    private DefaultInfoCollector infoCollector;

    @Autowired
    private DefaultClusterCollector defaultClusterCollector;

    private ScheduledThreadPoolExecutor nodeScheduled;

    private ScheduledThreadPoolExecutor clusterScheduled;

    private ScheduledThreadPoolExecutor infoScheduled;

    private int schedule = 1;

    @Override
    public void initCollector() {

        nodeScheduled = new ScheduledThreadPoolExecutor(1);
        clusterScheduled = new ScheduledThreadPoolExecutor(1);
        infoScheduled = new ScheduledThreadPoolExecutor(1);

        if (SystemConfig.DEFAULT_MODEL.equals(systemConfig.getModel())) {

            infoScheduled.scheduleAtFixedRate(() -> {
                try {
                    List<ClusterDO> clusterList = clusterService.getAllCluster();
                    for (ClusterDO cluster : clusterList) {
                        infoCollector.collect(cluster);
                    }
                } catch (Exception e) {
                    logger.error("start collect info error.", e);
                }
            }, schedule, schedule, TimeUnit.MINUTES);
        }

        nodeScheduled.scheduleAtFixedRate(() -> {
            try {
                List<ClusterDO> clusterList = clusterService.getAllCluster();
                for (ClusterDO cluster : clusterList) {
                    defaultNodeCollector.collect(cluster);
                }
            } catch (Exception e) {
                logger.error("start collect node error.", e);
            }

        }, schedule, schedule, TimeUnit.MINUTES);

        clusterScheduled.scheduleAtFixedRate(() -> {
            try {
                List<ClusterDO> clusterList = clusterService.getAllCluster();
                for (ClusterDO cluster : clusterList) {
                    defaultClusterCollector.collect(cluster);
                }
            } catch (Exception e) {
                logger.error("start collect cluster error.", e);
            }
        }, schedule, schedule, TimeUnit.MINUTES);
    }


}
