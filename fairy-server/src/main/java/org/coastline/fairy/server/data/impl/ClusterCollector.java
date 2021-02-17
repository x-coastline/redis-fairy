package org.coastline.fairy.server.data.impl;

import org.coastline.fairy.server.data.ICollector;

/**
 * 集群信息收集
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class ClusterCollector implements ICollector<Integer> {
    @Override
    public boolean collect(Integer integer) {
        return false;
    }

    @Override
    public void clean() {

    }
}
