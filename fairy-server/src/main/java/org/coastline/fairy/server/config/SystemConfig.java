package org.coastline.fairy.server.config;

import org.coastline.fairy.server.exception.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * amber server 启动相关配置文件
 * TODO: 检查必要配置的正确性
 *
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Configuration
public class SystemConfig {

    @Autowired
    private Environment environment;

    private int port;

    @Value("${amber.monitor.data-keep-days:0}")
    private int monitorDataKeepDays;

    private boolean needCleanNodeInfo;

    @Value("${amber.alert.data-keep-days:0}")
    private int alertDataKeepDays;
    

    @PostConstruct
    public void init() {
        port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
        if (getMonitorDataKeepDays() < 0) {
            throw new ConfigurationException("amber.monitor.data-keep-days must not be less than 0.");
        }
        needCleanNodeInfo = getMonitorDataKeepDays() > 0;
    }

    public int getPort() {
        return port;
    }

    public int getMonitorDataKeepDays() {
        return monitorDataKeepDays;
    }

    public boolean needCleanNodeInfo() {
        return needCleanNodeInfo;
    }

    public int getAlertDataKeepDays() {
        return alertDataKeepDays;
    }

}
