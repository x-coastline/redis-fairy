package org.coastline.amber.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@MapperScan({"org.coastline.amber.server.dao"})
@SpringBootApplication
public class AmberApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmberApplication.class, args);
    }

}
