package org.coastline.fairy.server.dao;

import org.apache.ibatis.annotations.*;
import org.coastline.fairy.server.entity.ClusterEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Mapper
public interface IClusterDao {

    @Select("SELECT cluster_id FROM cluster")
    Set<Integer> selectAllClusterId();

    @Select("SELECT * FROM cluster")
    List<ClusterEntity> selectAllCluster();

    @Select("SELECT " +
            "cluster_id, group_id, cluster_name, seed, redis_mode, " +
            "os, redis_version, image, state, " +
            "master_number, node_number, tag, environment, import_type, info, " +
            "create_time, update_time " +
            "FROM cluster WHERE cluster_id = #{clusterId}")
    ClusterEntity selectClusterById(@Param("clusterId") Integer clusterId);

    @Select("SELECT " +
            "cluster_id, group_id, cluster_name, seed, redis_mode, " +
            "os, redis_version, image, state, " +
            "master_number, node_number, tag, environment, import_type, info, " +
            "create_time, update_time " +
            "FROM cluster WHERE group_id = #{groupId}")
    List<ClusterEntity> selectClusterByGroup(@Param("groupId") Integer groupId);

    @Insert("UPDATE cluster SET " +
            "cluster_name = #{clusterName}, seed = #{seed}, redis_password = #{redisPassword}, " +
            "tag = #{tag}, info = #{info}, environment = #{environment}, " +
            "update_time = #{updateTime} " +
            "WHERE cluster_id = #{clusterId}")
    int updateCluster(ClusterEntity cluster);

    @Update("UPDATE cluster SET " +
            "state = #{state}, master_number = #{masterNumber}, node_number = #{nodeNumber}, " +
            "update_time = #{updateTime} " +
            "WHERE cluster_id = #{clusterId}")
    int updateClusterState(ClusterEntity cluster);

    @Insert("INSERT INTO cluster " +
            "(group_id, cluster_name, seed, redis_mode, " +
            "os, redis_version, image, redis_password, state, " +
            "master_number, node_number, tag, environment, import_type, info, " +
            "create_time, update_time) " +
            "VALUES " +
            "(#{groupId}, #{clusterName}, #{seed}, #{redisMode}, " +
            "#{os}, #{redisVersion}, #{image}, #{redisPassword}, #{state}, " +
            "#{masterNumber}, #{nodeNumber}, #{tag}, #{environment}, #{importType}, #{info}, " +
            "#{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "clusterId", keyColumn = "cluster_id")
    Integer insertCluster(ClusterEntity cluster);

    @Update("DELETE FROM cluster WHERE cluster_id = #{clusterId}")
    int deleteCluster(ClusterEntity cluster);

    @Select("CREATE TABLE IF NOT EXISTS `cluster` (" +
            "cluster_id integer(4) NOT NULL AUTO_INCREMENT COMMENT '自增ID'," +
            "group_id integer(4) NOT NULL," +
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
            "info varchar(255) DEFAULT NULL," +
            "create_time datetime(0) NOT NULL," +
            "update_time datetime(0) NOT NULL," +
            "PRIMARY KEY (cluster_id)," +
            "UNIQUE KEY (cluster_name)" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;")
    void createClusterTable();

}
