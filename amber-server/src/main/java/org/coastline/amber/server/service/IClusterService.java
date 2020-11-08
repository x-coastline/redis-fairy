package org.coastline.amber.server.service;


import org.coastline.amber.server.entity.ClusterEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 9/3/2020
 */
public interface IClusterService {

    Set<Integer> getAllClusterId();

    List<ClusterEntity> getAllCluster();

    ClusterEntity getClusterById(Integer clusterId);

    List<ClusterEntity> getClusterByGroup(Integer groupId);

    boolean addCluster(ClusterEntity cluster);

    boolean updateCluster(ClusterEntity cluster);

    boolean updateClusterState(ClusterEntity cluster);

    boolean deleteClusterState(ClusterEntity cluster);

    ClusterEntity completeClusterInfo(ClusterEntity cluster);

}
