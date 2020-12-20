package org.coastline.fairy.server.entity;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class AuthGroup {

    private Integer groupId;

    private String groupName;

    private String email;

    private Timestamp updateTime;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
