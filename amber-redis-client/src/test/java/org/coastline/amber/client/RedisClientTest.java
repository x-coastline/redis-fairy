package org.coastline.amber.client;

import org.coastline.amber.client.model.RedisSlowLog;
import org.coastline.amber.client.model.RedisURI;
import org.junit.Test;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2020/11/7
 */
public class RedisClientTest {

    @Test
    public void testSlowLog() {
        RedisURI redisURI = new RedisURI("192.168.0.112", 9000);
        RedisClient redisClient = new RedisClient(redisURI);
        List<RedisSlowLog> slowLogList = redisClient.slowLog();
        slowLogList.forEach(System.out::println);
        redisClient.close();
    }


}
