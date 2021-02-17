package org.coastline.fairy.agent.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coastline.fairy.client.model.RedisNode;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@Builder
@NoArgsConstructor
public class RedisNodeDO extends RedisNode {

    /**
     * database primary key
     */
    private Integer redisNodeId;

    private String host;

    private Integer port;

}
