package com.videumcorp.gitlab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;
import com.videumcorp.gitlab.classes.network.RetrofitGitLab;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RetrofitUnitTest {

    private static final String TAG = RetrofitUnitTest.class.getName();

    //Add project_id and private_token to test some retrofit methods.
    private final int project_id = 0;
    private final String private_token = "";
    private final String branch_tag_commit = "master";

    private Gson gson;
    private Retrofit retrofit;
    private RetrofitGitLab retrofitGitLab;

    @Before
    public void createRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gitlab.com/api/v4/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        retrofitGitLab = retrofit.create(RetrofitGitLab.class);
    }

    @Test
    public void retrofitGitLabRepositoryTree() {

        Call<List<GitLabRepositoryTree>> gitLabRepositoryTreeCall =
                retrofitGitLab.getRepositoryTree(project_id, "", private_token, branch_tag_commit);
        try {
            Response<List<GitLabRepositoryTree>> response = gitLabRepositoryTreeCall.execute();
            System.out.println(TAG + " " + Objects.requireNonNull(response.body()).get(0).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void retrofitGitLabFile() {

        Call<ResponseBody> gitLabFileCall =
                retrofitGitLab.getFile(project_id, "build.gradle", private_token, "master");
        try {
            Response<ResponseBody> response = gitLabFileCall.execute();
            System.out.println(TAG + " " + Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}