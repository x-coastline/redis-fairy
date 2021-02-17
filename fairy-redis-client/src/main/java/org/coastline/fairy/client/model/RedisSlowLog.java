package org.coastline.fairy.client.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/4
 */
@Data
@Builder
@NoArgsConstructor
public class RedisSlowLog {

    /**
     * redis slow log 命令获取
     */
    private long id;

    /**
     * from 4.0
     */
    private String client;

    /**
     * 命令耗时，单位：微妙
     */
    private long duration;
    private String type;
    private String command;
    private String clientName;
    private Timestamp creationTime;
    
}
