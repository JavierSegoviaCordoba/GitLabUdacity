package com.videumcorp.gitlab.classes.gson.gitlabproject;

import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("commit_count")
    private int commitCount;

    @SerializedName("lfs_objects_size")
    private int lfsObjectsSize;

    @SerializedName("job_artifacts_size")
    private int jobArtifactsSize;

    @SerializedName("repository_size")
    private int repositorySize;

    @SerializedName("storage_size")
    private int storageSize;

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void setLfsObjectsSize(int lfsObjectsSize) {
        this.lfsObjectsSize = lfsObjectsSize;
    }

    public int getLfsObjectsSize() {
        return lfsObjectsSize;
    }

    public void setJobArtifactsSize(int jobArtifactsSize) {
        this.jobArtifactsSize = jobArtifactsSize;
    }

    public int getJobArtifactsSize() {
        return jobArtifactsSize;
    }

    public void setRepositorySize(int repositorySize) {
        this.repositorySize = repositorySize;
    }

    public int getRepositorySize() {
        return repositorySize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public int getStorageSize() {
        return storageSize;
    }

    @Override
    public String toString() {
        return "Statistics{" + "commit_count = '" + commitCount + '\'' + ",lfs_objects_size = '" +
                lfsObjectsSize + '\'' + ",job_artifacts_size = '" + jobArtifactsSize + '\'' +
                ",repository_size = '" + repositorySize + '\'' + ",storage_size = '" + storageSize +
                '\'' + "}";
    }
}