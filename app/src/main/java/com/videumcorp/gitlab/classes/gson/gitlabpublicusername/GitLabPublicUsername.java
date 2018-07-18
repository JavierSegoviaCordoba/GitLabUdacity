package com.videumcorp.gitlab.classes.gson.gitlabpublicusername;

import com.google.gson.annotations.SerializedName;

public class GitLabPublicUsername {

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("state")
    private String state;

    @SerializedName("username")
    private String username;

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GitLabPublicUsername{" + "avatar_url = '" + avatarUrl + '\'' + ",web_url = '" +
                webUrl + '\'' + ",name = '" + name + '\'' + ",id = '" + id + '\'' + ",state = '" +
                state + '\'' + ",username = '" + username + '\'' + "}";
    }
}