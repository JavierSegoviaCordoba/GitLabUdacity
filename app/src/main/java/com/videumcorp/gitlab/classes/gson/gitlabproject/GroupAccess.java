package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;

public class GroupAccess {

    @SerializedName("access_level")
    private int accessLevel;

    @SerializedName("notification_level")
    private int notificationLevel;

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setNotificationLevel(int notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public int getNotificationLevel() {
        return notificationLevel;
    }

    @Override
    public String toString() {
        return "GroupAccess{" + "access_level = '" + accessLevel + '\'' +
                ",notification_level = '" + notificationLevel + '\'' + "}";
    }
}