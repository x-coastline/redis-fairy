package org.coastline.fairy.server.controller;

import org.coastline.fairy.server.entity.ClusterEntity;
import org.coastline.fairy.server.model.ImportType;
import org.coastline.fairy.server.model.Result;
import org.coastline.fairy.server.service.IClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@RestController
@RequestMapping(value = "/cluster/*")
public class ClusterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterController.class);

    @Autowired
    private IClusterService clusterService;

    @GetMapping("/list/{groupId}")
    public Result<List<ClusterEntity>> listCluster(@PathVariable Integer groupId) {
        try {
            List<ClusterEntity> clusterList = clusterService.getClusterByGroup(groupId);
            // TODO: 移除敏感字段（password, token...）
            return Result.ofSuccess(clusterList);
        } catch (Exception e) {
            String msg = "Get cluster by group failed.";
            LOGGER.error(msg, e);
            return Result.ofFail(msg);
        }
    }

    @GetMapping("/get/{clusterId}")
    public Result<ClusterEntity> getCluster(@PathVariable Integer clusterId) {
        try {
            ClusterEntity cluster = clusterService.getClusterById(clusterId);
            return Result.ofSuccess(cluster);
        } catch (Exception e) {
            return Result.ofFail(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result addCluster(@RequestBody ClusterEntity cluster) {
        try {
            cluster.setImportType(ImportType.IMPORT);
            clusterService.addCluster(cluster);
            return Result.ofSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ofFail(e.getMessage());
        }
    }

}
