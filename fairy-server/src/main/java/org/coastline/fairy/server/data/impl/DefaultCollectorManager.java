package org.coastline.fairy.server.data.impl;

import org.coastline.fairy.server.config.SystemConfig;
import org.coastline.fairy.server.data.ICollectorManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

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

    @Override
    public void initCollector() {
        ScheduledThreadPoolExecutor nodeScheduled = new ScheduledThreadPoolExecutor(1);
        ScheduledThreadPoolExecutor clusterScheduled = new ScheduledThreadPoolExecutor(1);
        if (SystemConfig.DEFAULT_MODEL.equals(systemConfig.getModel())) {
            ScheduledThreadPoolExecutor infoScheduled = new ScheduledThreadPoolExecutor(1);
        }
    }


}
