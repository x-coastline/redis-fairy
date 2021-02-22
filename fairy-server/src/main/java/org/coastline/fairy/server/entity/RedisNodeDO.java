package org.coastline.fairy.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coastline.fairy.client.model.RedisNode;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisNodeDO extends RedisNode {

    /**
     * database primary key
     */
    private Integer redisNodeId;

    private String host;

    private Integer port;

}
