package org.coastline.fairy.server.config;

import org.coastline.fairy.server.dao.IClusterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * 自动创建表
 *
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Component
public class InitializedTables {

    @Autowired
    private IClusterDao clusterDao;

    @PostConstruct
    private void init() {
        createTables();
    }

    private void createTables() {
        clusterDao.createClusterTable();
    }
}
