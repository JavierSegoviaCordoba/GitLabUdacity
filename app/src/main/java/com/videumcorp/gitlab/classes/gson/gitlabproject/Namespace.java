package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;

public class Namespace {

    @SerializedName("path")
    private String path;

    @SerializedName("kind")
    private String kind;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("full_path")
    private String fullPath;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
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

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String toString() {
        return "Namespace{" + "path = '" + path + '\'' + ",kind = '" + kind + '\'' + ",name = '" +
                name + '\'' + ",id = '" + id + '\'' + ",full_path = '" + fullPath + '\'' + "}";
    }
}