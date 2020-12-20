package org.coastline.fairy.client.model;

import com.google.common.collect.Sets;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 2020/10/31
 */
public class RedisURI {

    /**
     * Default timeout: 60 sec
     */
    public static final int DEFAULT_TIMEOUT = 100;

    /**
     * default client name
     */
    public static String DEFAULT_CLIENT_NAME = "amber";

    private Set<HostAndPort> hostAndPortSet;

    private int database;

    private String clientName = DEFAULT_CLIENT_NAME;

    private String sentinelMasterId;

    private String password;

    private int timeout = DEFAULT_TIMEOUT;

    private RedisURI() {
    }

    public RedisURI(String host, int port) {
        this(new HostAndPort(host, port), null);
    }

    public RedisURI(String host, int port, String password) {
        this(new HostAndPort(host, port), password);
    }

    public RedisURI(HostAndPort hostAndPort, String password) {
        this(Sets.newHashSet(hostAndPort), password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, String password) {
        this(hostAndPortSet, 0, null, password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, int database, String sentinelMasterId, String password) {
        this.hostAndPortSet = hostAndPortSet;
        this.database = database;
        this.clientName = DEFAULT_CLIENT_NAME;
        this.sentinelMasterId = sentinelMasterId;
        this.password = password;
    }

    public Set<HostAndPort> getHostAndPortSet() {
        return hostAndPortSet;
    }

    public void setHostAndPortSet(Set<HostAndPort> hostAndPortSet) {
        this.hostAndPortSet = hostAndPortSet;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSentinelMasterId() {
        return sentinelMasterId;
    }

    public void setSentinelMasterId(String sentinelMasterId) {
        this.sentinelMasterId = sentinelMasterId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "RedisURI{" +
                "hostAndPortSet=" + hostAndPortSet +
                ", database=" + database +
                ", clientName='" + clientName + '\'' +
                ", sentinelMasterId='" + sentinelMasterId + '\'' +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
