package com.videumcorp.gitlab.classes.network;

import com.videumcorp.gitlab.classes.gson.gitlabrepositorycommit.GitLabRepositoryCommit;
import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.classes.gson.gitlabpublicuser.GitLabPublicUser;
import com.videumcorp.gitlab.classes.gson.gitlabpublicusername.GitLabPublicUsername;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;
import com.videumcorp.gitlab.classes.gson.gitlabuser.GitLabUser;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * The interface Retrofit GitLab.
 */
public interface RetrofitGitLab {

    /**
     * Gets {@link GitLabUser}.
     *
     * @param private_token the private token
     * @return the gitlab user
     */
    @GET("user")
    Call<GitLabUser> getGitlabUser(@Query("private_token") String private_token);

    /**
     * Gets {@link GitLabPublicUsername}.
     *
     * @param username the username
     * @return the git lab public user name
     */
    @GET("users")
    Call<List<GitLabPublicUsername>> getGitLabPublicUsername(@Query("username") String username);

    /**
     * Gets {@link GitLabPublicUser}.
     *
     * @param id the id
     * @return the git lab public user
     */
    @GET("users/{user_id}")
    Call<GitLabPublicUser> getGitLabPublicUser(@Path("user_id") int id);

    /**
     * Gets {@link GitLabProject}.
     *
     * @param user_id       the user id
     * @param starred       the starred
     * @param private_token the private token
     * @return the git lab user projects
     */
    @GET("users/{user_id}/projects")
    Call<List<GitLabProject>> getGitLabUserProjects(@Path("user_id") int user_id,
                                                    @Query("starred") boolean starred,
                                                    @Query("private_token") String private_token);

    /**
     * Gets {@link GitLabRepositoryTree}.
     *
     * @param project_id        the project id
     * @param path              the path
     * @param private_token     the private token
     * @param branch_tag_commit the branch tag commit
     * @return the project files
     */
    @GET("projects/{project_id}/repository/tree")
    Call<List<GitLabRepositoryTree>> getRepositoryTree(@Path("project_id") int project_id,
                                                       @Query("path") String path,
                                                       @Query("private_token") String private_token,
                                                       @Query("ref") String branch_tag_commit);

    /**
     * Gets Gitlab file.
     *
     * @param project_id        the project id
     * @param file_path         the file path
     * @param private_token     the private token
     * @param branch_tag_commit the branch tag commit
     * @return the file
     */
    @GET("projects/{project_id}/repository/files/{file_path}/raw")
    Call<ResponseBody> getFile(@Path("project_id") int project_id,
                               @Path("file_path") String file_path,
                               @Query("private_token") String private_token,
                               @Query("ref") String branch_tag_commit);


    /**
     * Gets {@link GitLabRepositoryCommit}.
     *
     * @param project_id        the project id
     * @param private_token     the private token
     * @param branch_tag_commit the branch tag commit
     * @return the repository commits
     */
    @GET("projects/{project_id}/repository/commits")
    Call<List<GitLabRepositoryCommit>> getRepositoryCommits(@Path("project_id") int project_id,
                                                            @Query("private_token") String private_token,
                                                            @Query("ref_name") String branch_tag_commit);



}

