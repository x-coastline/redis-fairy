package org.coastline.fairy.server.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coastline.fairy.server.exception.ConfigurationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * fairy server 启动相关配置文件
 * TODO: 检查必要配置的正确性
 *
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@Builder
@NoArgsConstructor
@Configuration
public class SystemConfig implements InitializingBean {

    public static final String DEFAULT_MODEL = "none";

    @Autowired
    private Environment environment;

    @Value("fairy.model:none")
    private String model;

    private int port;

    @Value("${fairy.monitor.data-keep-days:0}")
    private int monitorDataKeepDays;

    @Value("${fairy.alert.data-keep-days:0}")
    private int alertDataKeepDays;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
        if (getMonitorDataKeepDays() < 0) {
            throw new ConfigurationException("fairy.monitor.data-keep-days must not be less than 0.");
        }


    }
}
