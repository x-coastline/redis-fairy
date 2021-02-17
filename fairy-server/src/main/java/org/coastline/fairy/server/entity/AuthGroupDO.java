package org.coastline.fairy.server.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
@Data
@Builder
@NoArgsConstructor
public class AuthGroupDO {

    private Integer groupId;

    private String groupName;

    private String owner;

    private String email;

    private Timestamp creationTime;

}
