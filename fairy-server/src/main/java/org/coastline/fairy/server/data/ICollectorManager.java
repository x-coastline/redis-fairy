package org.coastline.fairy.server.data;

/**
 * 数据收集管理器
 * 根据不同的策略选择不同的数据收集方式
 * @author Jay.H.Zou
 * @date 2021/2/17
 */
public interface ICollectorManager {

    /**
     * 初始化收集器
     */
    void initCollector();
}
