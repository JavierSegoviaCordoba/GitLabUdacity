package com.videumcorp.gitlab.classes.gson.gitlabrepositorytree;

import com.google.gson.annotations.SerializedName;

public class GitLabRepositoryTree {

    @SerializedName("mode")
    private String mode;

    @SerializedName("path")
    private String path;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GitLabRepositoryTree{" +
                "mode='" + mode + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}