package com.videumcorp.gitlab.classes.gson.gitlabuser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitLabUser {

    @SerializedName("can_create_project")
    private boolean canCreateProject;

    @SerializedName("theme_id")
    private int themeId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("bio")
    private Object bio;

    @SerializedName("projects_limit")
    private int projectsLimit;

    @SerializedName("linkedin")
    private String linkedin;

    @SerializedName("last_activity_on")
    private String lastActivityOn;

    @SerializedName("can_create_group")
    private boolean canCreateGroup;

    @SerializedName("skype")
    private String skype;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("identities")
    private List<IdentitiesItem> identities;

    @SerializedName("last_sign_in_at")
    private String lastSignInAt;

    @SerializedName("color_scheme_id")
    private int colorSchemeId;

    @SerializedName("id")
    private int id;

    @SerializedName("state")
    private String state;

    @SerializedName("confirmed_at")
    private String confirmedAt;

    @SerializedName("email")
    private String email;

    @SerializedName("current_sign_in_at")
    private String currentSignInAt;

    @SerializedName("two_factor_enabled")
    private boolean twoFactorEnabled;

    @SerializedName("shared_runners_minutes_limit")
    private Object sharedRunnersMinutesLimit;

    @SerializedName("external")
    private boolean external;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("website_url")
    private String websiteUrl;

    @SerializedName("organization")
    private Object organization;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Object location;

    @SerializedName("username")
    private String username;

    public void setCanCreateProject(boolean canCreateProject) {
        this.canCreateProject = canCreateProject;
    }

    public boolean isCanCreateProject() {
        return canCreateProject;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getBio() {
        return bio;
    }

    public void setProjectsLimit(int projectsLimit) {
        this.projectsLimit = projectsLimit;
    }

    public int getProjectsLimit() {
        return projectsLimit;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLastActivityOn(String lastActivityOn) {
        this.lastActivityOn = lastActivityOn;
    }

    public String getLastActivityOn() {
        return lastActivityOn;
    }

    public void setCanCreateGroup(boolean canCreateGroup) {
        this.canCreateGroup = canCreateGroup;
    }

    public boolean isCanCreateGroup() {
        return canCreateGroup;
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

    public void setIdentities(List<IdentitiesItem> identities) {
        this.identities = identities;
    }

    public List<IdentitiesItem> getIdentities() {
        return identities;
    }

    public void setLastSignInAt(String lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }

    public String getLastSignInAt() {
        return lastSignInAt;
    }

    public void setColorSchemeId(int colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    public int getColorSchemeId() {
        return colorSchemeId;
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

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCurrentSignInAt(String currentSignInAt) {
        this.currentSignInAt = currentSignInAt;
    }

    public String getCurrentSignInAt() {
        return currentSignInAt;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setSharedRunnersMinutesLimit(Object sharedRunnersMinutesLimit) {
        this.sharedRunnersMinutesLimit = sharedRunnersMinutesLimit;
    }

    public Object getSharedRunnersMinutesLimit() {
        return sharedRunnersMinutesLimit;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public boolean isExternal() {
        return external;
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

    public void setOrganization(Object organization) {
        this.organization = organization;
    }

    public Object getOrganization() {
        return organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getLocation() {
        return location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GitLabUser{" + "can_create_project = '" + canCreateProject + '\'' +
                ",theme_id = '" + themeId + '\'' + ",created_at = '" + createdAt + '\'' +
                ",bio = '" + bio + '\'' + ",projects_limit = '" + projectsLimit + '\'' +
                ",linkedin = '" + linkedin + '\'' + ",last_activity_on = '" + lastActivityOn + '\'' +
                ",can_create_group = '" + canCreateGroup + '\'' + ",skype = '" + skype + '\'' +
                ",twitter = '" + twitter + '\'' + ",identities = '" + identities + '\'' +
                ",last_sign_in_at = '" + lastSignInAt + '\'' + ",color_scheme_id = '" +
                colorSchemeId + '\'' + ",id = '" + id + '\'' + ",state = '" + state + '\'' +
                ",confirmed_at = '" + confirmedAt + '\'' + ",email = '" + email + '\'' +
                ",current_sign_in_at = '" + currentSignInAt + '\'' + ",two_factor_enabled = '" +
                twoFactorEnabled + '\'' + ",shared_runners_minutes_limit = '" +
                sharedRunnersMinutesLimit + '\'' + ",external = '" + external + '\'' +
                ",avatar_url = '" + avatarUrl + '\'' + ",web_url = '" + webUrl + '\'' +
                ",website_url = '" + websiteUrl + '\'' + ",organization = '" + organization + '\'' +
                ",name = '" + name + '\'' + ",location = '" + location + '\'' + ",username = '" +
                username + '\'' + "}";
    }
}