package org.coastline.fairy.server.service;


import org.coastline.fairy.server.entity.ClusterDO;

import java.util.List;
import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 9/3/2020
 */
public interface IClusterService {

    Set<Integer> getAllClusterId();

    List<ClusterDO> getAllCluster();

    ClusterDO getClusterById(Integer clusterId);

    List<ClusterDO> getClusterByGroup(Integer groupId);

    boolean addCluster(ClusterDO cluster);

    boolean updateCluster(ClusterDO cluster);

    boolean updateClusterState(ClusterDO cluster);

    boolean deleteClusterState(ClusterDO cluster);

    ClusterDO completeClusterInfo(ClusterDO cluster);

}
