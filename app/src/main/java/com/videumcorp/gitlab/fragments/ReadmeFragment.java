package com.videumcorp.gitlab.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;
import com.videumcorp.gitlab.classes.network.RetrofitUtils;
import com.videumcorp.gitlab.databinding.FragmentReadmeBinding;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadmeFragment extends Fragment {

    private static final String TAG = ReadmeFragment.class.getName();

    private FragmentReadmeBinding fragmentReadmeBinding;

    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_PATH = "path";
    private static final String KEY_PRIVATE_TOKEN = "private_token";
    private static final String KEY_BRANCH_TAG_COMMIT = "BRANCH_TAG_COMMIT";

    private int project_id;
    private String path;
    private String private_token;
    private String branch_tag_commit;

    private Context context;

    private boolean hasReadme = false;

    private List<GitLabRepositoryTree> gitLabRepositoryTreeList = new ArrayList<>();

    public ReadmeFragment() {
    }

    public static ReadmeFragment newInstance(int project_id, String path,
                                             String private_token,
                                             String branch_tag_commit) {
        ReadmeFragment fragment = new ReadmeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PROJECT_ID, project_id);
        args.putString(KEY_PATH, path);
        args.putString(KEY_PRIVATE_TOKEN, private_token);
        args.putString(KEY_BRANCH_TAG_COMMIT, branch_tag_commit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentReadmeBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_readme, container, false);

        Bundle bundle = getArguments();


        fragmentReadmeBinding.swipeRefreshLayoutFragmentReadme.setEnabled(false);

        if (bundle != null) {
            fragmentReadmeBinding.swipeRefreshLayoutFragmentReadme.setRefreshing(true);

            project_id = bundle.getInt(KEY_PROJECT_ID);
            path = bundle.getString(KEY_PATH);
            private_token = bundle.getString(KEY_PRIVATE_TOKEN);
            branch_tag_commit = bundle.getString(KEY_BRANCH_TAG_COMMIT);

            RetrofitUtils.getGitLabRepositoryTree(project_id, path, private_token, branch_tag_commit,
                    new Callback<List<GitLabRepositoryTree>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<GitLabRepositoryTree>> call,
                                               @NonNull Response<List<GitLabRepositoryTree>>
                                                       response) {

                            if (response.code() == 200) {
                                gitLabRepositoryTreeList = response.body();

                                if (gitLabRepositoryTreeList != null) {
                                    checkIfHasReadme();
                                }
                            } else {
                                InterfaceUtils.toastShowThemed(context,
                                        context
                                                .getString(R.string.gitlab_api_error_label)
                                                + " " + response.message() + " " + response.code(),
                                        Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<GitLabRepositoryTree>> call,
                                              @NonNull Throwable t) {
                            InterfaceUtils.toastShowThemed(context,
                                    context.getResources().getString(R.string.error_try_again_label)
                                    + t.getMessage(),
                                    Toast.LENGTH_LONG);
                        }
                    });
        }

        return fragmentReadmeBinding.getRoot();
    }

    private void checkIfHasReadme() {
        for (int i = 0; i < gitLabRepositoryTreeList.size(); i++) {

            if (gitLabRepositoryTreeList.get(i)
                    .getName().equalsIgnoreCase("README.md")) {

                hasReadme = true;

                String readMePath = gitLabRepositoryTreeList.get(i)
                        .getPath();

                RetrofitUtils.getGitLabFile(project_id,
                        readMePath, private_token, branch_tag_commit,
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(
                                    @NonNull Call<ResponseBody> call,
                                    @NonNull Response<ResponseBody> response) {

                                if (response.code() == 200) {
                                    fragmentReadmeBinding.markdownViewFragmentReadme
                                            .loadMarkdownFile(call.request().url().toString(),
                                                    InterfaceUtils.getMarkdownTheme(context));
                                    fragmentReadmeBinding.markdownViewFragmentReadme
                                            .setWebViewClient(new WebViewClient() {
                                                @Override
                                                public void onPageFinished(WebView view, String url) {
                                                    super.onPageFinished(view, url);
                                                    fragmentReadmeBinding.swipeRefreshLayoutFragmentReadme
                                                            .setRefreshing(false);
                                                }
                                            });
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
                                    @NonNull Call<ResponseBody> call,
                                    @NonNull Throwable t) {
                                InterfaceUtils.toastShowThemed(context,
                                        context.getResources()
                                                .getString(R.string.error_try_again_label)
                                                + t.getMessage(),
                                        Toast.LENGTH_LONG);
                            }
                        });
                break;
            }
        }
        if (!hasReadme) {
            fragmentReadmeBinding.markdownViewFragmentReadme
                    .loadMarkdown("##NO README FILE",
                            InterfaceUtils.getMarkdownTheme(context));
            fragmentReadmeBinding.markdownViewFragmentReadme.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    fragmentReadmeBinding.swipeRefreshLayoutFragmentReadme.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
