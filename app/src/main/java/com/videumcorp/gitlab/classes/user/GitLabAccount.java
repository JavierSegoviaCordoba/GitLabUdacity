package com.videumcorp.gitlab.classes.user;

import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.classes.gson.gitlabuser.GitLabUser;

import java.util.List;

public class GitLabAccount {

    private String token;
    private GitLabUser gitLabUser;
    private List<GitLabProject> gitLabProjectList;
    private List<GitLabProject> gitLabStarredProjectList;

    public static String gitLabAccount = "account";
    public static String gitLabAccountUser = "user";
    public static String gitLabAccountProjects = "projects";

    public GitLabAccount() {
    }

    public GitLabAccount(String token, GitLabUser gitLabUser, List<GitLabProject> gitLabProjectList,
                         List<GitLabProject> gitLabStarredProjectList) {
        this.token = token;
        this.gitLabUser = gitLabUser;
        this.gitLabProjectList = gitLabProjectList;
        this.gitLabStarredProjectList = gitLabStarredProjectList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GitLabUser getGitLabUser() {
        return gitLabUser;
    }

    public void setGitLabUser(GitLabUser gitLabUser) {
        this.gitLabUser = gitLabUser;
    }

    public List<GitLabProject> getGitLabProjectList() {
        return gitLabProjectList;
    }

    public void setGitLabProjectList(List<GitLabProject> gitLabProjectList) {
        this.gitLabProjectList = gitLabProjectList;
    }

    public List<GitLabProject> getGitLabStarredProjectList() {
        return gitLabStarredProjectList;
    }

    public void setGitLabStarredProjectList(List<GitLabProject> gitLabStarredProjectList) {
        this.gitLabStarredProjectList = gitLabStarredProjectList;
    }

    @Override
    public String toString() {
        return "GitLabAccount{" + "token='" + token + '\'' + ", gitLabUser=" + gitLabUser +
                ", gitLabProjectList=" + gitLabProjectList + ", gitLabStarredProjectList=" +
                gitLabStarredProjectList + '}';
    }
}