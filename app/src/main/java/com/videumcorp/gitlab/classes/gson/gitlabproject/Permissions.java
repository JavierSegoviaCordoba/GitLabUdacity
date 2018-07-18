package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;

public class Permissions {

    @SerializedName("project_access")
    private ProjectAccess projectAccess;

    @SerializedName("group_access")
    private GroupAccess groupAccess;

    public void setProjectAccess(ProjectAccess projectAccess) {
        this.projectAccess = projectAccess;
    }

    public ProjectAccess getProjectAccess() {
        return projectAccess;
    }

    public void setGroupAccess(GroupAccess groupAccess) {
        this.groupAccess = groupAccess;
    }

    public GroupAccess getGroupAccess() {
        return groupAccess;
    }

    @Override
    public String toString() {
        return "Permissions{" + "project_access = '" + projectAccess + '\'' + ",group_access = '" +
                groupAccess + '\'' + "}";
    }
}