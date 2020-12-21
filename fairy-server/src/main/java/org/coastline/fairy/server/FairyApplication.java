package org.coastline.fairy.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@MapperScan({"org.coastline.fairy.server.dao"})
@SpringBootApplication
public class FairyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FairyApplication.class, args);
    }

}
