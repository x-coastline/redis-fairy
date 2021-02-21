package org.coastline.fairy.server.data.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.coastline.fairy.server.data.ICollector;
import org.coastline.fairy.server.entity.ClusterDO;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 节点信息收集
 *
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class NodeCollector implements ICollector<ClusterDO>, InitializingBean {

    private ExecutorService threadPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        int cupNumber = Runtime.getRuntime().availableProcessors();
        int core = cupNumber * 2;
        int maxSize = cupNumber * 4;
        threadPool = new ThreadPoolExecutor(core, maxSize, 1, TimeUnit.HOURS,
                new ArrayBlockingQueue<>(1),
                new ThreadFactoryBuilder().setNameFormat("collect-node-pool-thread-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public boolean collect(ClusterDO cluster) {
        return false;
    }

    @Override
    public void clean() {

    }


}
