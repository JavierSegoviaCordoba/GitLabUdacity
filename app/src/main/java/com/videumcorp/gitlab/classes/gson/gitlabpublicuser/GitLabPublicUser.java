package com.videumcorp.gitlab.classes.gson.gitlabpublicuser;

import com.google.gson.annotations.SerializedName;

public class GitLabPublicUser {

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("bio")
    private String bio;

    @SerializedName("linkedin")
    private String linkedin;

    @SerializedName("skype")
    private String skype;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("website_url")
    private String websiteUrl;

    @SerializedName("organization")
    private String organization;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private String location;

    @SerializedName("id")
    private int id;

    @SerializedName("state")
    private String state;

    @SerializedName("username")
    private String username;

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSkype() {
        return skype;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTwitter() {
        return twitter;
    }

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

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
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
        return "GitLabPublicUser{" + "created_at = '" + createdAt + '\'' + ",bio = '" + bio + '\'' +
                ",linkedin = '" + linkedin + '\'' + ",skype = '" + skype + '\'' + ",twitter = '" +
                twitter + '\'' + ",avatar_url = '" + avatarUrl + '\'' + ",web_url = '" + webUrl +
                '\'' + ",website_url = '" + websiteUrl + '\'' + ",organization = '" + organization +
                '\'' + ",name = '" + name + '\'' + ",location = '" + location + '\'' + ",id = '" +
                id + '\'' + ",state = '" + state + '\'' + ",username = '" + username + '\'' + "}";
    }
}