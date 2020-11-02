package org.coastline.amber.client.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.coastline.amber.common.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2020/11/2
 */
public class RedisUtil {

    private RedisUtil() {}

    public static Map<String, String> parseInfoToMap(String info) throws IOException {
        Map<String, String> infoMap = new LinkedHashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(info.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] keyValue = StringUtil.splitByColon(line);
            if (keyValue.length < 2) {
                continue;
            }
            String key = keyValue[0];
            String value = keyValue[1];
            if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
                continue;
            }
            infoMap.put(key, value);
        }
        return infoMap;
    }

    public static void main(String[] args) throws IOException {
        String info = "cluster_state:ok\n" +
                "cluster_slots_assigned:16384\n" +
                "cluster_slots_ok:16384\n" +
                "cluster_slots_pfail:0\n" +
                "cluster_slots_fail:0\n" +
                "cluster_known_nodes:6\n" +
                "cluster_size:3\n" +
                "cluster_current_epoch:5\n" +
                "cluster_my_epoch:4\n" +
                "cluster_stats_messages_ping_sent:176233\n" +
                "cluster_stats_messages_pong_sent:175307\n" +
                "cluster_stats_messages_sent:351540\n" +
                "cluster_stats_messages_ping_received:175307\n" +
                "cluster_stats_messages_pong_received:176232\n" +
                "cluster_stats_messages_received:351539\n";
        Map<String, String> infoToMap = parseInfoToMap(info);
        infoToMap.forEach((key, value) -> {
            System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key));
        });
    }
    
}
