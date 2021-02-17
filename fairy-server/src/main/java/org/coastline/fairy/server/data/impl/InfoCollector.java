package org.coastline.fairy.server.data.impl;


import org.coastline.fairy.client.model.RedisInfo;
import org.coastline.fairy.server.data.ICollector;

import java.util.List;

/**
 * 本机直接收集 info
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class InfoCollector implements ICollector<List<RedisInfo>> {

    @Override
    public boolean collect(List<RedisInfo> redisInfoList) {
        return false;
    }

    @Override
    public void clean() {

    }
}
