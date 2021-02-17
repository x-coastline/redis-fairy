package org.coastline.fairy.server.data.impl;

import org.coastline.fairy.server.data.ICollector;

/**
 * 节点信息收集
 *
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public class NodeCollector implements ICollector<Integer> {
    @Override
    public boolean collect(Integer integer) {
        return false;
    }

    @Override
    public void clean() {

    }
}
