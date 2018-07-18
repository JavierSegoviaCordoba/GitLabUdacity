package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;

class SharedWithGroupsItem {

    @SerializedName("group_id")
    private int groupId;

    @SerializedName("group_name")
    private String groupName;

    @SerializedName("group_access_level")
    private int groupAccessLevel;

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupAccessLevel(int groupAccessLevel) {
        this.groupAccessLevel = groupAccessLevel;
    }

    public int getGroupAccessLevel() {
        return groupAccessLevel;
    }

    @Override
    public String toString() {
        return "SharedWithGroupsItem{" + "group_id = '" + groupId + '\'' + ",group_name = '" +
                groupName + '\'' + ",group_access_level = '" + groupAccessLevel + '\'' + "}";
    }
}