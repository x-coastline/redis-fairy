package org.coastline.fairy.server.service.impl;

import org.coastline.fairy.server.entity.RedisNodeDO;
import org.coastline.fairy.server.service.IClusterService;
import org.coastline.fairy.server.service.IRedisNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author Nico.R.Xiang
 * @since 2020/9/16
 */
@Service
public class RedisNodeService implements IRedisNodeService {

    private static final Logger logger = LoggerFactory.getLogger(RedisNodeService.class);

    @Autowired
    private IClusterService clusterService;

    @Override
    public List<RedisNodeDO> getRedisNodeList(Integer clusterId) {
        return null;
    }

    @Override
    public Map<String, Collection<RedisNodeDO>> getRedisNodeMap(Integer clusterId) throws Exception {
        return null;
    }

    @Override
    public List<RedisNodeDO> getRealNodeList(Integer clusterId) throws Exception {
        return null;
    }

    @Override
    public boolean addRedisNode(RedisNodeDO redisNode) {
        return false;
    }

    @Override
    public boolean addRedisNodeList(List<RedisNodeDO> redisNodeList) {
        return false;
    }

    @Override
    public boolean updateRedisNode(RedisNodeDO redisNode) {
        return false;
    }
}
