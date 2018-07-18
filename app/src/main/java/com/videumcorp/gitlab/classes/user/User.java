package com.videumcorp.gitlab.classes.user;

import java.util.List;

public class User {

    private GoogleAccount googleAccount;
    private List<GitLabAccount> gitLabAccountList;

    public static String users_label = "users";
    public static String googleAccount_label = "google_account";
    public static String gitLabAccounts_label = "gitlab_accounts";

    public User() {
    }

    public User(GoogleAccount googleAccount, List<GitLabAccount> gitLabAccountList) {
        this.googleAccount = googleAccount;
        this.gitLabAccountList = gitLabAccountList;
    }

    public GoogleAccount getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(GoogleAccount googleAccount) {
        this.googleAccount = googleAccount;
    }

    public List<GitLabAccount> getGitLabAccountList() {
        return gitLabAccountList;
    }

    public void setGitLabAccountList(List<GitLabAccount> gitLabAccountList) {
        this.gitLabAccountList = gitLabAccountList;
    }

    @Override
    public String toString() {
        return "User{" + "googleAccount=" + googleAccount + ", gitLabAccountList=" +
                gitLabAccountList + '}';
    }
}
