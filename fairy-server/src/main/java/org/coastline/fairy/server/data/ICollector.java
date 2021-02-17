package org.coastline.fairy.server.data;

/**
 * 数据收集
 *
 * @author Jay.H.Zou
 * @date 2020/12/23
 */
public interface ICollector<T> {

    /**
     * 收集任务接口
     *
     * @return
     */
    boolean collect(T t);

    void clean();
}
