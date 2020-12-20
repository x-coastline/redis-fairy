package org.coastline.fairy.server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.coastline.fairy.client.model.RedisSlowLog;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public interface ISlowLogDao {


    @Insert("")
    int insertSlowLog(List<RedisSlowLog> slowLogList);

    @Select("")
    List<RedisSlowLog> selectSlowLog();

    @Select("CREATE TABLE IF NOT EXISTS `slow_log` (" +
            "slow_log_id integer(4) NOT NULL AUTO_INCREMENT COMMENT '自增ID'," +
            "cluster_id integer(4) NOT NULL," +
            "cluster_name varchar(255) NOT NULL," +
            "seed TEXT NOT NULL," +
            "redis_mode varchar(25) NOT NULL," +
            "os varchar(255) NOT NULL," +
            "redis_version varchar(25) NOT NULL," +
            "image varchar(255) DEFAULT NULL," +
            "redis_password varchar(50) DEFAULT NULL," +
            "state varchar(50) NOT NULL," +
            "master_number integer(4) NOT NULL," +
            "node_number integer(4) NOT NULL," +
            "tag varchar(50) DEFAULT NULL," +
            "environment varchar(50) NOT NULL," +
            "import_type varchar(50) NOT NULL," +
            "info varchar(255) NOT NULL," +
            "create_time datetime(0) NOT NULL," +
            "update_time datetime(0) NOT NULL," +
            "PRIMARY KEY (cluster_id)," +
            "UNIQUE KEY (cluster_name)" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;")
    void createSlowLogTable();

}
