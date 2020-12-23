package org.coastline.fairy.client.model;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/4
 */
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

    private Timestamp timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
