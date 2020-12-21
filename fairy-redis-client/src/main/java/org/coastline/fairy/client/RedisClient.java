package org.coastline.fairy.client;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.coastline.fairy.client.model.*;
import org.coastline.fairy.client.util.RedisUtil;
import org.coastline.fairy.common.StringUtil;
import redis.clients.jedis.ClusterReset;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.MigrateParams;
import redis.clients.jedis.util.Slowlog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2020/11/4
 */
public class RedisClient implements IRedisClient {

    private static final String OK = "OK";

    private static final String PONG = "PONG";

    private static final String CONNECTED = "connected";

    private Jedis jedis;

    private RedisURI redisURI;

    public RedisClient(RedisURI redisURI) {
        this.redisURI = redisURI;
        String redisPassword = redisURI.getPassword();
        String clientName = redisURI.getClientName();
        Set<HostAndPort> hostAndPortSet = redisURI.getHostAndPortSet();
        for (HostAndPort hostAndPort : hostAndPortSet) {
            try {
                jedis = new Jedis(hostAndPort.getHost(), hostAndPort.getPort(), redisURI.getTimeout());
                if (!Strings.isNullOrEmpty(redisPassword)) {
                    jedis.auth(redisPassword);
                }
                if (!Strings.isNullOrEmpty(clientName)) {
                    jedis.clientSetname(clientName);
                }
                if (ping()) {
                    break;
                }
            } catch (JedisConnectionException e) {
                // try next nodes
                close();
            }
        }
    }

    @Override
    public Jedis getClient() {
        return jedis;
    }

    @Override
    public boolean ping() {
        String ping = jedis.ping();
        return !Strings.isNullOrEmpty(ping) && Objects.equals(ping.toUpperCase(), PONG);
    }

    @Override
    public Map<String, String> info() {
        return RedisUtil.parseInfoToMap(jedis.info());
    }

    @Override
    public RedisInfo infoModel() {
        try {
            Object redisInfo = infoToObject(info());
            return (RedisInfo) redisInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> info(String section) {
        return RedisUtil.parseInfoToMap(jedis.info(section));
    }

    @Override
    public RedisInfo infoModel(String section) {
        try {
            Object redisInfo = infoToObject(info(section));
            return (RedisInfo) redisInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> clusterInfo() {
        return RedisUtil.parseInfoToMap(jedis.clusterInfo());
    }

    @Override
    public ClusterInfo clusterInfoModel() {
        try {
            Object redisInfo = clusterInfoToObject(clusterInfo());
            return (ClusterInfo) redisInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RedisClusterNode> clusterNodes() {
        List<RedisClusterNode> clusterNodeList = new ArrayList<>();
        String nodes = jedis.clusterNodes();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(nodes.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] item = StringUtil.splitBySpace(line);
                String nodeId = item[0].trim();
                String ipPort = item[1];
                String flagsStr = item[2];
                String masterId = item[3];
                String pingSent = item[4];
                String pongRecv = item[5];
                String configEpoch = item[6];
                String linkState = item[7];
                String hostAndPortStr = StringUtil.splitByAite(ipPort)[0];
                HostAndPort hostAndPort = strToHostAndPort(hostAndPortStr);
                RedisClusterNode redisNode = new RedisClusterNode(nodeId, hostAndPort);
                String[] flagArr = StringUtil.splitByCommas(flagsStr);
                Set<RedisClusterNode.NodeFlag> flags = new HashSet<>(flagArr.length);
                for (String flag : flagArr) {
                    flags.add(RedisClusterNode.NodeFlag.valueOf(flag));
                }
                redisNode.setFlags(flags);
                boolean isMaster = StringUtil.MINUS.equals(masterId);
                redisNode.setReplicateOf(isMaster ? null : masterId);
                redisNode.setRole(isMaster ? RedisRole.MASTER : RedisRole.REPLICA);
                redisNode.setConnected(CONNECTED.equals(linkState));
                redisNode.setPingSentTimestamp(Long.parseLong(pingSent));
                redisNode.setPongReceivedTimestamp(Long.parseLong(pongRecv));
                redisNode.setConfigEpoch(Long.parseLong(configEpoch));
                int length = item.length;
                if (length > 8) {
                    int slotNumber = 0;
                    StringBuilder slotRang = new StringBuilder();
                    for (int i = 8; i < length; i++) {
                        String slotRangeItem = item[i];
                        String[] startAndEnd = StringUtil.splitByMinus(slotRangeItem);
                        if (startAndEnd.length == 1) {
                            slotNumber = 1;
                        } else {
                            slotNumber += Integer.parseInt(startAndEnd[1]) - Integer.parseInt(startAndEnd[0]) + 1;
                        }
                        slotRang.append(slotRangeItem);
                        if (i < length - 1) {
                            slotRang.append(StringUtil.COMMAS);
                        }
                    }
                    redisNode.setSlotRange(slotRang.toString());
                    redisNode.setSlotNumber(slotNumber);
                }
                clusterNodeList.add(redisNode);
            }
        } catch (Exception ignored) {
        }
        return clusterNodeList;
    }

    @Override
    public List<RedisSlowLog> slowLog() {
        return slowLog(-1);
    }

    @Override
    public List<RedisSlowLog> slowLog(int size) {
        List<Slowlog> slowlogs;
        if (size > 0) {
            slowlogs = jedis.slowlogGet(size);
        } else {
            slowlogs = jedis.slowlogGet();
        }
        List<RedisSlowLog> slowLogList = new ArrayList<>(slowlogs.size());
        for (Slowlog slowlog : slowlogs) {
            RedisSlowLog redisSlowLog = new RedisSlowLog();
            redisSlowLog.setId(slowlog.getId());
            redisSlowLog.setDuration(slowlog.getExecutionTime());
            redisSlowLog.setTimestamp(new Timestamp(slowlog.getTimeStamp() * 1000));
            List<String> args = slowlog.getArgs();
            redisSlowLog.setType(args.get(0));
            redisSlowLog.setCommand(Joiner.on(" ").skipNulls().join(args));
            slowLogList.add(redisSlowLog);
        }
        return slowLogList;
    }

    @Override
    public Map<String, String> getConfig() {
        return getConfig("*");
    }

    @Override
    public Map<String, String> getConfig(String pattern) {
        List<String> configList = jedis.configGet(pattern);
        Map<String, String> configMap = new LinkedHashMap<>();
        for (int i = 0, length = configList.size(); i < length; i += 2) {
            String key = configList.get(i);
            if (Strings.isNullOrEmpty(key)) {
                continue;
            }
            configMap.put(key, configList.get(i + 1));
        }
        return configMap;
    }

    @Override
    public boolean rewriteConfig() {
        return Objects.equals(jedis.configRewrite(), OK);
    }

    @Override
    public String clusterMeet(String host, int port) {
        return jedis.clusterMeet(host, port);
    }

    @Override
    public boolean clusterReplicate(String nodeId) {
        return Objects.equals(jedis.clusterReplicate(nodeId), OK);
    }

    @Override
    public boolean clusterFailOver() {
        return Objects.equals(jedis.clusterFailover(), OK);
    }

    @Override
    public String clusterAddSlots(int... slots) {
        return jedis.clusterAddSlots(slots);
    }

    @Override
    public String clusterSetSlotNode(int slot, String nodeId) {
        return jedis.clusterSetSlotNode(slot, nodeId);
    }

    @Override
    public String clusterSetSlotImporting(int slot, String nodeId) {
        return jedis.clusterSetSlotImporting(slot, nodeId);
    }

    @Override
    public String clusterSetSlotMigrating(int slot, String nodeId) {
        return jedis.clusterSetSlotMigrating(slot, nodeId);
    }

    @Override
    public List<String> clusterGetKeysInSlot(int slot, int count) {
        return jedis.clusterGetKeysInSlot(slot, count);
    }

    @Override
    public String clusterSetSlotStable(int slot) {
        return jedis.clusterSetSlotStable(slot);
    }

    @Override
    public boolean clusterForget(String nodeId) {
        return Objects.equals(jedis.clusterForget(nodeId), OK);
    }

    @Override
    public String clusterReset(ClusterReset reset) {
        return jedis.clusterReset(reset);
    }

    @Override
    public String migrate(String host, int port, String key, int destinationDb, int timeout) {
        return jedis.migrate(host, port, key, 0, timeout);
    }

    @Override
    public String migrate(String host, int port, int destinationDB,
                          int timeout, MigrateParams params, String... keys) {
        return jedis.migrate(host, port, destinationDB, timeout, params, keys);
    }

    @Override
    public boolean replicaOf(String host, int port) {
        return Objects.equals(jedis.slaveof(host, port), OK);
    }

    @Override
    public String replicaNoOne() {
        return jedis.slaveofNoOne();
    }

    @Override
    public String memoryPurge() {
        return null;
    }

    @Override
    public List<Map<String, String>> getSentinelMasters() {
        return null;
    }

    @Override
    public List<String> getMasterAddrByName(String masterName) {
        return null;
    }

    @Override
    public List<Map<String, String>> sentinelSlaves(String masterName) {
        return null;
    }

    @Override
    public boolean monitorMaster(String masterName, String ip, int port, int quorum) {
        return false;
    }

    @Override
    public boolean failoverMaster(String masterName) {
        return false;
    }

    @Override
    public boolean sentinelRemove(String masterName) {
        return false;
    }

    @Override
    public Long resetConfig(String pattern) {
        return null;
    }

    @Override
    public boolean sentinelSet(String masterName, Map<String, String> parameterMap) {
        return false;
    }

    @Override
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    private static HostAndPort strToHostAndPort(String hostAndPortStr) {
        String[] hostPort = StringUtil.splitByColon(hostAndPortStr);
        return new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1]));
    }

    private RedisInfo infoToObject(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        map.forEach((key, value) -> {
            // TODO: 类型转换处理
            // TODO: keyspace, command operation,
            jsonObject.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key), value);
        });
        return JSONObject.parseObject(jsonObject.toJSONString(), RedisInfo.class);
    }

    private Object clusterInfoToObject(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        map.forEach((key, value) -> {
            jsonObject.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key), value);
        });
        return JSONObject.parseObject(jsonObject.toJSONString(), ClusterInfo.class);
    }
}
