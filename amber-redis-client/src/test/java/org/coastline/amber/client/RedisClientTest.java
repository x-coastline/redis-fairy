package org.coastline.amber.client;

import com.alibaba.fastjson.JSONObject;
import org.coastline.amber.client.model.RedisInfo;
import org.coastline.amber.client.model.RedisSlowLog;
import org.coastline.amber.client.model.RedisURI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2020/11/7
 */
public class RedisClientTest {

    RedisClient redisClient;

    @Before
    public void init() {
        RedisURI redisURI = new RedisURI("192.168.0.112", 9000);
        redisClient = new RedisClient(redisURI);
    }


    @Test
    public void testSlowLog() {
        List<RedisSlowLog> slowLogList = redisClient.slowLog();
        slowLogList.forEach(System.out::println);
    }

    @Test
    public void testInfo() {
        RedisInfo redisInfo = redisClient.infoModel();
        System.out.println(JSONObject.toJSONString(redisInfo));
    }

    @After
    public void close() {
        redisClient.close();
    }


}
