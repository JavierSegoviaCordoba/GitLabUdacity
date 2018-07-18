package com.videumcorp.gitlab.classes.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.classes.gson.gitlabpublicuser.GitLabPublicUser;
import com.videumcorp.gitlab.classes.gson.gitlabpublicusername.GitLabPublicUsername;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorycommit.GitLabRepositoryCommit;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;
import com.videumcorp.gitlab.classes.gson.gitlabuser.GitLabUser;
import com.videumcorp.gitlab.classes.user.GitLabAccount;

import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit utils.
 */
public class RetrofitUtils {

    private static final String TAG = RetrofitUtils.class.getName();

    private static Gson gson = new GsonBuilder().setLenient().create();

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gitlab.com/api/v4/")
            .addConverterFactory(
                    GsonConverterFactory
                            .create(gson)).build();

    private static RetrofitGitLab retrofitGitLab = retrofit.create(RetrofitGitLab.class);

    /**
     * Gets {@link GitLabAccount}.
     *
     * @param private_token          the private token
     * @param gitlabAccountInterface the gitlab account interface
     */
    public static void getGitLabAccount(Context context, final String private_token,
                                        final GitlabAccountInterface gitlabAccountInterface) {
        final GitLabAccount gitLabAccount = new GitLabAccount();
        gitLabAccount.setToken(private_token);
        getGitLabUser(private_token, new Callback<GitLabUser>() {
            @Override
            public void onResponse(@NonNull Call<GitLabUser> call,
                                   @NonNull Response<GitLabUser> response) {
                if (response.code() == 200) {
                    gitLabAccount.setGitLabUser(response.body());
                    final int user_id = Objects.requireNonNull(response.body()).getId();
                    getGitLabUserProjects(user_id, false, private_token,
                            new Callback<List<GitLabProject>>() {
                                @Override
                                public void onResponse(
                                        @NonNull Call<List<GitLabProject>> call,
                                        @NonNull Response<List<GitLabProject>> response) {

                                    if (response.code() == 200) {
                                        gitLabAccount.setGitLabProjectList(response.body());
                                        getGitLabUserProjects(user_id, true,
                                                private_token,
                                                new Callback<List<GitLabProject>>() {
                                                    @Override
                                                    public void onResponse(
                                                            @NonNull Call<List<GitLabProject>> call,
                                                            @NonNull Response<List<GitLabProject>>
                                                                    response) {

                                                        if (response.code() == 200) {
                                                            gitLabAccount
                                                                    .setGitLabStarredProjectList(
                                                                            response.body());
                                                            gitlabAccountInterface
                                                                    .onSuccess(gitLabAccount);
                                                        } else {
                                                            InterfaceUtils.toastShowThemed(context,
                                                                    context
                                                                            .getString(R.string.gitlab_api_error_label)
                                                                            + " " + response.message() + " " + response.code(),
                                                                    Toast.LENGTH_LONG);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(
                                                            @NonNull Call<List<GitLabProject>> call,
                                                            @NonNull Throwable t) {
                                                        gitlabAccountInterface.onError(t);
                                                        InterfaceUtils.toastShowThemed(context,
                                                                context.getResources()
                                                                        .getString(R.string.error_try_again_label)
                                                                + t.getMessage(),
                                                                Toast.LENGTH_LONG);
                                                    }
                                                });
                                    } else {
                                        InterfaceUtils.toastShowThemed(context,
                                                context.getString(R.string.gitlab_api_error_label)
                                                        + " " + response.message() + " " + response.code(),
                                                Toast.LENGTH_LONG);
                                    }
                                }

                                @Override
                                public void onFailure(
                                        @NonNull Call<List<GitLabProject>> call,
                                        @NonNull Throwable t) {
                                    InterfaceUtils.toastShowThemed(context,
                                            context.getResources()
                                                    .getString(R.string.error_try_again_label)
                                            + t.getMessage(),
                                            Toast.LENGTH_LONG);
                                }
                            });
                } else {
                    InterfaceUtils.toastShowThemed(context,
                            context.getString(R.string.gitlab_api_error_label)
                                    + " " + response.message() + " " + response.code(),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitLabUser> call, @NonNull Throwable t) {
                InterfaceUtils.toastShowThemed(context,
                        context.getResources().getString(R.string.error_try_again_label)
                        + t.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Gets {@link GitLabUser}.
     *
     * @param private_token the private token
     * @param callback      the callback
     */
    private static void getGitLabUser(String private_token, Callback<GitLabUser> callback) {
        Call<GitLabUser> gitLabUserCall = retrofitGitLab.getGitlabUser(private_token);
        gitLabUserCall.enqueue(callback);
    }

    /**
     * Gets {@link GitLabPublicUsername}.
     *
     * @param username the username
     * @param callback the callback
     */
    public static void getGitLabUsername(String username, Callback<List<GitLabPublicUsername>> callback) {
        Call<List<GitLabPublicUsername>> gitLabPublicUsernameCall =
                retrofitGitLab.getGitLabPublicUsername(username);
        gitLabPublicUsernameCall.enqueue(callback);
    }

    /**
     * Gets {@link GitLabPublicUser}.
     *
     * @param user_id  the user id
     * @param callback the callback
     */
    public static void getGitLabPublicUser(int user_id, Callback<GitLabPublicUser> callback) {
        Call<GitLabPublicUser> gitLabPublicUserCall = retrofitGitLab.getGitLabPublicUser(user_id);
        gitLabPublicUserCall.enqueue(callback);
    }

    /**
     * Gets {@link GitLabProject}.
     *
     * @param user_id       the user id
     * @param starred       the starred
     * @param private_token the private token
     * @param callback      the callback
     */
    private static void getGitLabUserProjects(int user_id, boolean starred, String private_token,
                                              Callback<List<GitLabProject>> callback) {
        Call<List<GitLabProject>> gitLabUserProjectsCall =
                retrofitGitLab.getGitLabUserProjects(user_id, starred, private_token);
        gitLabUserProjectsCall.enqueue(callback);
    }


    public static void getGitLabRepositoryTree(int project_id, String path, String private_token,
                                               String branch_tag_commit,
                                               Callback<List<GitLabRepositoryTree>> callback) {
        Call<List<GitLabRepositoryTree>> gitLabRepositoryTreeCall =
                retrofitGitLab.getRepositoryTree(project_id, path, private_token, branch_tag_commit);
        gitLabRepositoryTreeCall.enqueue(callback);
    }

    public static void getGitLabFile(int project_id, String file_path, String private_token,
                                     String branch_tag_commit,
                                     Callback<ResponseBody> callback) {
        Call<ResponseBody> gitLabFile =
                retrofitGitLab.getFile(project_id, file_path, private_token, branch_tag_commit);
        gitLabFile.enqueue(callback);
    }

    public static void getGitLabRepositoryCommits(int project_id, String private_token,
                                                  String branch_tag_commit,
                                                  Callback<List<GitLabRepositoryCommit>> callback) {
        Call<List<GitLabRepositoryCommit>> gitLabRepositoryCommits = retrofitGitLab
                .getRepositoryCommits(project_id, private_token, branch_tag_commit);
        gitLabRepositoryCommits.enqueue(callback);
    }

    /**
     * The interface Gitlab account interface.
     */
    public interface GitlabAccountInterface {
        /**
         * On success.
         *
         * @param gitlabAccount the gitlab account
         */
        void onSuccess(GitLabAccount gitlabAccount);

        /**
         * On error.
         *
         * @param t the t
         */
        void onError(Throwable t);
    }
}
