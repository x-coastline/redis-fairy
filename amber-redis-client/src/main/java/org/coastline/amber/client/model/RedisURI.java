package org.coastline.amber.client.model;

import com.google.common.collect.Sets;
import redis.clients.jedis.HostAndPort;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2020/10/31
 */
public class RedisURI {

    /**
     * Default timeout: 60 sec
     */
    public static final long DEFAULT_TIMEOUT = 60;

    public static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.SECONDS;

    public static final Duration DEFAULT_TIMEOUT_DURATION = Duration.ofSeconds(DEFAULT_TIMEOUT);

    /**
     * default client name
     */
    public static String DEFAULT_CLIENT_NAME = "amber";

    private Set<HostAndPort> hostAndPortSet;

    private int database;

    private String clientName;

    private String sentinelMasterId;

    private String password;

    private RedisURI() {
    }

    public RedisURI(String host, int port, String password) {
        this(new HostAndPort(host, port), password);
    }

    public RedisURI(HostAndPort hostAndPort, String password) {
        this(Sets.newHashSet(hostAndPort), password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, String password) {
        this(hostAndPortSet, 0, DEFAULT_CLIENT_NAME, null, password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, int database, String clientName, String sentinelMasterId, String password) {
        this.hostAndPortSet = hostAndPortSet;
        this.database = database;
        this.clientName = clientName;
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

    @Override
    public String toString() {
        return "RedisURI{" +
                "hostAndPortSet=" + hostAndPortSet +
                ", database=" + database +
                ", clientName='" + clientName + '\'' +
                ", sentinelMasterId='" + sentinelMasterId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
