package org.coastline.fairy.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


/**
 * 查询 node info 请求参数包装类
 *
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeInfoParam {

    public static final int TIME_TYPE_MINUTE = 0;

    public static final int TIME_TYPE_REAL_TIME = 1;

    protected static final int DEFAULT_TIME_TYPE = TIME_TYPE_MINUTE;

    private Integer clusterId;

    /**
     * info 指标中的某一项
     */
    private String infoItem;

    private List<String> infoItemList;

    private Integer timeType = DEFAULT_TIME_TYPE;

    private Timestamp startTime;

    private Timestamp endTime;

    private String node;

    private List<String> nodeList;

    public NodeInfoParam(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public NodeInfoParam(Integer clusterId, List<String> nodeList) {
        this.clusterId = clusterId;
        this.nodeList = nodeList;
    }

    public NodeInfoParam(Integer clusterId, String node) {
        this.clusterId = clusterId;
        this.node = node;
    }

    @Override
    public String toString() {
        return "NodeInfoParam{" +
                "clusterId=" + clusterId +
                ", infoItem='" + infoItem + '\'' +
                ", timeType=" + timeType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", node='" + node + '\'' +
                ", nodeList=" + nodeList +
                '}';
    }
}
