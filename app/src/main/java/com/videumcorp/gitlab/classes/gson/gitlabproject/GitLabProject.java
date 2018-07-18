package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;

import java.util.List;

public class GitLabProject {

    @SerializedName("_repository_tree")
    private List<GitLabRepositoryTree> gitLabRepositoryTree;

    @SerializedName("ssh_url_to_repo")
    private String sshUrlToRepo;

    @SerializedName("issues_enabled")
    private boolean issuesEnabled;

    @SerializedName("only_allow_merge_if_all_discussions_are_resolved")
    private boolean onlyAllowMergeIfAllDiscussionsAreResolved;

    @SerializedName("repository_storage")
    private String repositoryStorage;

    @SerializedName("request_access_enabled")
    private boolean requestAccessEnabled;

    @SerializedName("_links")
    private Links links;

    @SerializedName("open_issues_count")
    private int openIssuesCount;

    @SerializedName("snippets_enabled")
    private boolean snippetsEnabled;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("import_status")
    private String importStatus;

    @SerializedName("path")
    private String path;

    @SerializedName("archived")
    private boolean archived;

    @SerializedName("tag_list")
    private List<String> tagList;

    @SerializedName("permissions")
    private Permissions permissions;

    @SerializedName("last_activity_at")
    private String lastActivityAt;

    @SerializedName("shared_runners_enabled")
    private boolean sharedRunnersEnabled;

    @SerializedName("id")
    private int id;

    @SerializedName("container_registry_enabled")
    private boolean containerRegistryEnabled;

    @SerializedName("owner")
    private Owner owner;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("path_with_namespace")
    private String pathWithNamespace;

    @SerializedName("resolve_outdated_diff_discussions")
    private boolean resolveOutdatedDiffDiscussions;

    @SerializedName("merge_requests_enabled")
    private boolean mergeRequestsEnabled;

    @SerializedName("jobs_enabled")
    private boolean jobsEnabled;

    @SerializedName("import_error")
    private Object importError;

    @SerializedName("shared_with_groups")
    private List<SharedWithGroupsItem> sharedWithGroups;

    @SerializedName("http_url_to_repo")
    private String httpUrlToRepo;

    @SerializedName("only_allow_merge_if_pipeline_succeeds")
    private boolean onlyAllowMergeIfPipelineSucceeds;

    @SerializedName("readme_url")
    private String readmeUrl;

    @SerializedName("merge_method")
    private String mergeMethod;

    @SerializedName("printing_merge_requests_link_enabled")
    private boolean printingMergeRequestsLinkEnabled;

    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("wiki_enabled")
    private boolean wikiEnabled;

    @SerializedName("public_jobs")
    private boolean publicJobs;

    @SerializedName("name")
    private String name;

    @SerializedName("creator_id")
    private int creatorId;

    @SerializedName("namespace")
    private Namespace namespace;

    @SerializedName("default_branch")
    private String defaultBranch;

    @SerializedName("approvals_before_merge")
    private int approvalsBeforeMerge;

    @SerializedName("name_with_namespace")
    private String nameWithNamespace;

    @SerializedName("star_count")
    private int starCount;

    @SerializedName("forks_count")
    private int forksCount;

    @SerializedName("runners_token")
    private String runnersToken;

    @SerializedName("statistics")
    private Statistics statistics;

    public List<GitLabRepositoryTree> getGitLabRepositoryTree() {
        return gitLabRepositoryTree;
    }

    public void setGitLabRepositoryTree(List<GitLabRepositoryTree> gitLabRepositoryTree) {
        this.gitLabRepositoryTree = gitLabRepositoryTree;
    }

    public void setSshUrlToRepo(String sshUrlToRepo) {
        this.sshUrlToRepo = sshUrlToRepo;
    }

    public String getSshUrlToRepo() {
        return sshUrlToRepo;
    }

    public void setIssuesEnabled(boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public boolean isIssuesEnabled() {
        return issuesEnabled;
    }

    public void setOnlyAllowMergeIfAllDiscussionsAreResolved(
            boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
        this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public boolean isOnlyAllowMergeIfAllDiscussionsAreResolved() {
        return onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public void setRepositoryStorage(String repositoryStorage) {
        this.repositoryStorage = repositoryStorage;
    }

    public String getRepositoryStorage() {
        return repositoryStorage;
    }

    public void setRequestAccessEnabled(boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
    }

    public boolean isRequestAccessEnabled() {
        return requestAccessEnabled;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return links;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setSnippetsEnabled(boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public boolean isSnippetsEnabled() {
        return snippetsEnabled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setLastActivityAt(String lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public String getLastActivityAt() {
        return lastActivityAt;
    }

    public void setSharedRunnersEnabled(boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
    }

    public boolean isSharedRunnersEnabled() {
        return sharedRunnersEnabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setContainerRegistryEnabled(boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
    }

    public boolean isContainerRegistryEnabled() {
        return containerRegistryEnabled;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setPathWithNamespace(String pathWithNamespace) {
        this.pathWithNamespace = pathWithNamespace;
    }

    public String getPathWithNamespace() {
        return pathWithNamespace;
    }

    public void setResolveOutdatedDiffDiscussions(boolean resolveOutdatedDiffDiscussions) {
        this.resolveOutdatedDiffDiscussions = resolveOutdatedDiffDiscussions;
    }

    public boolean isResolveOutdatedDiffDiscussions() {
        return resolveOutdatedDiffDiscussions;
    }

    public void setMergeRequestsEnabled(boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public boolean isMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    public void setJobsEnabled(boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
    }

    public boolean isJobsEnabled() {
        return jobsEnabled;
    }

    public void setImportError(Object importError) {
        this.importError = importError;
    }

    public Object getImportError() {
        return importError;
    }

    public void setSharedWithGroups(List<SharedWithGroupsItem> sharedWithGroups) {
        this.sharedWithGroups = sharedWithGroups;
    }

    public List<SharedWithGroupsItem> getSharedWithGroups() {
        return sharedWithGroups;
    }

    public void setHttpUrlToRepo(String httpUrlToRepo) {
        this.httpUrlToRepo = httpUrlToRepo;
    }

    public String getHttpUrlToRepo() {
        return httpUrlToRepo;
    }

    public void setOnlyAllowMergeIfPipelineSucceeds(boolean onlyAllowMergeIfPipelineSucceeds) {
        this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
    }

    public boolean isOnlyAllowMergeIfPipelineSucceeds() {
        return onlyAllowMergeIfPipelineSucceeds;
    }

    public void setReadmeUrl(String readmeUrl) {
        this.readmeUrl = readmeUrl;
    }

    public String getReadmeUrl() {
        return readmeUrl;
    }

    public void setMergeMethod(String mergeMethod) {
        this.mergeMethod = mergeMethod;
    }

    public String getMergeMethod() {
        return mergeMethod;
    }

    public void setPrintingMergeRequestsLinkEnabled(boolean printingMergeRequestsLinkEnabled) {
        this.printingMergeRequestsLinkEnabled = printingMergeRequestsLinkEnabled;
    }

    public boolean isPrintingMergeRequestsLinkEnabled() {
        return printingMergeRequestsLinkEnabled;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setWikiEnabled(boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public boolean isWikiEnabled() {
        return wikiEnabled;
    }

    public void setPublicJobs(boolean publicJobs) {
        this.publicJobs = publicJobs;
    }

    public boolean isPublicJobs() {
        return publicJobs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setApprovalsBeforeMerge(int approvalsBeforeMerge) {
        this.approvalsBeforeMerge = approvalsBeforeMerge;
    }

    public int getApprovalsBeforeMerge() {
        return approvalsBeforeMerge;
    }

    public void setNameWithNamespace(String nameWithNamespace) {
        this.nameWithNamespace = nameWithNamespace;
    }

    public String getNameWithNamespace() {
        return nameWithNamespace;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setRunnersToken(String runnersToken) {
        this.runnersToken = runnersToken;
    }

    public String getRunnersToken() {
        return runnersToken;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return "GitLabProject{" +
                "gitLabRepositoryTree=" + gitLabRepositoryTree +
                ", sshUrlToRepo='" + sshUrlToRepo + '\'' +
                ", issuesEnabled=" + issuesEnabled +
                ", onlyAllowMergeIfAllDiscussionsAreResolved=" + onlyAllowMergeIfAllDiscussionsAreResolved +
                ", repositoryStorage='" + repositoryStorage + '\'' +
                ", requestAccessEnabled=" + requestAccessEnabled +
                ", links=" + links +
                ", openIssuesCount=" + openIssuesCount +
                ", snippetsEnabled=" + snippetsEnabled +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", importStatus='" + importStatus + '\'' +
                ", path='" + path + '\'' +
                ", archived=" + archived +
                ", tagList=" + tagList +
                ", permissions=" + permissions +
                ", lastActivityAt='" + lastActivityAt + '\'' +
                ", sharedRunnersEnabled=" + sharedRunnersEnabled +
                ", id=" + id +
                ", containerRegistryEnabled=" + containerRegistryEnabled +
                ", owner=" + owner +
                ", visibility='" + visibility + '\'' +
                ", pathWithNamespace='" + pathWithNamespace + '\'' +
                ", resolveOutdatedDiffDiscussions=" + resolveOutdatedDiffDiscussions +
                ", mergeRequestsEnabled=" + mergeRequestsEnabled +
                ", jobsEnabled=" + jobsEnabled +
                ", importError=" + importError +
                ", sharedWithGroups=" + sharedWithGroups +
                ", httpUrlToRepo='" + httpUrlToRepo + '\'' +
                ", onlyAllowMergeIfPipelineSucceeds=" + onlyAllowMergeIfPipelineSucceeds +
                ", readmeUrl='" + readmeUrl + '\'' +
                ", mergeMethod='" + mergeMethod + '\'' +
                ", printingMergeRequestsLinkEnabled=" + printingMergeRequestsLinkEnabled +
                ", webUrl='" + webUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", wikiEnabled=" + wikiEnabled +
                ", publicJobs=" + publicJobs +
                ", name='" + name + '\'' +
                ", creatorId=" + creatorId +
                ", namespace=" + namespace +
                ", defaultBranch='" + defaultBranch + '\'' +
                ", approvalsBeforeMerge=" + approvalsBeforeMerge +
                ", nameWithNamespace='" + nameWithNamespace + '\'' +
                ", starCount=" + starCount +
                ", forksCount=" + forksCount +
                ", runnersToken='" + runnersToken + '\'' +
                ", statistics=" + statistics +
                '}';
    }
}