package org.coastline.fairy.client.util;

import com.google.common.base.Strings;
import org.coastline.fairy.common.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

    public static Map<String, String> parseInfoToMap(String info)  {
        Map<String, String> infoMap = new LinkedHashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(info.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        try {
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
        } catch (Exception ignored) {
        }
        return infoMap;
    }

}
