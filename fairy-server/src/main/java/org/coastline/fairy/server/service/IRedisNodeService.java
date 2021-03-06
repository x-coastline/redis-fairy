package org.coastline.fairy.server.service;


import org.coastline.fairy.server.entity.RedisNodeDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Nico.R.Xiang
 * @since 2020/9/16
 */
public interface IRedisNodeService {

    /**
     * 获取RedisNodes (实际节点 + db中存的节点)
     * @param clusterId
     * @return
     * @throws Exception
     */
    List<RedisNodeDO> getRedisNodeList(Integer clusterId);

    /**
     *
     * @param clusterId
     * @return
     */
    Map<String, Collection<RedisNodeDO>> getRedisNodeMap(Integer clusterId) throws Exception;

    /**
     * 获取集群中的实际节点
     * @param clusterId
     * @return
     * @throws Exception
     */
    List<RedisNodeDO> getRealNodeList(Integer clusterId) throws Exception;

    boolean addRedisNode(RedisNodeDO redisNode);

    boolean addRedisNodeList(List<RedisNodeDO> redisNodeList);

    boolean updateRedisNode(RedisNodeDO redisNode);
}
