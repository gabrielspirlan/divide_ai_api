package com.api.divideai.event.domain.dtos.group;

import java.util.List;

public class GroupUsersResponseDTO {

    private String groupId;
    private String groupName;
    private List<GroupUserDTO> users;

    public GroupUsersResponseDTO() {
    }

    public GroupUsersResponseDTO(String groupId, String groupName, List<GroupUserDTO> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.users = users;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GroupUserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<GroupUserDTO> users) {
        this.users = users;
    }
}

