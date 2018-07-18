package com.videumcorp.gitlab.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.adapters.RecyclerViewGitLabRepositoryCommitAdapter;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorycommit.GitLabRepositoryCommit;
import com.videumcorp.gitlab.classes.network.RetrofitUtils;
import com.videumcorp.gitlab.databinding.FragmentRepositoryCommitsListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryCommitsFragment extends Fragment {

    private static final String TAG = RepositoryCommitsFragment.class.getName();

    private FragmentRepositoryCommitsListBinding fragmentRepositoryCommitsListBinding;

    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_PRIVATE_TOKEN = "private_token";
    private static final String KEY_BRANCH_TAG_COMMIT = "BRANCH_TAG_COMMIT";

    private Context context;

    private int project_id;
    private String private_token;
    private String branch_tag_commit;

    private List<GitLabRepositoryCommit> gitLabRepositoryCommitList = new ArrayList<>();

    private RecyclerViewGitLabRepositoryCommitAdapter recyclerViewGitLabRepositoryCommitAdapter;

    public RepositoryCommitsFragment() {
    }

    public static RepositoryCommitsFragment newInstance(int project_id,
                                                        String private_token,
                                                        String branch_tag_commit) {

        RepositoryCommitsFragment fragment = new RepositoryCommitsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PROJECT_ID, project_id);
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

        fragmentRepositoryCommitsListBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_repository_commits_list, container, false);

        fragmentRepositoryCommitsListBinding.swipeRefreshLayoutFragmentRepositoryCommits
                .setEnabled(false);
        fragmentRepositoryCommitsListBinding.swipeRefreshLayoutFragmentRepositoryCommits
                .setRefreshing(true);

        Bundle bundle = getArguments();

        if (bundle != null) {
            project_id = bundle.getInt(KEY_PROJECT_ID);
            private_token = bundle.getString(KEY_PRIVATE_TOKEN);
            branch_tag_commit = bundle.getString(KEY_BRANCH_TAG_COMMIT);

            RetrofitUtils.getGitLabRepositoryCommits(project_id, private_token, branch_tag_commit,
                    new Callback<List<GitLabRepositoryCommit>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<GitLabRepositoryCommit>> call,
                                               @NonNull Response<List<GitLabRepositoryCommit>>
                                                       response) {
                            fragmentRepositoryCommitsListBinding
                                    .swipeRefreshLayoutFragmentRepositoryCommits.setRefreshing(false);

                            if (response.code() == 200) {

                                gitLabRepositoryCommitList = response.body();

                                recyclerViewGitLabRepositoryCommitAdapter =
                                        new RecyclerViewGitLabRepositoryCommitAdapter(
                                                gitLabRepositoryCommitList,
                                                private_token);

                                DividerItemDecoration dividerItemDecoration =
                                        new DividerItemDecoration(context,
                                                DividerItemDecoration.VERTICAL);

                                Drawable drawable = InterfaceUtils.drawableTint(context,
                                        R.drawable.recyclerview_divider_decoration,
                                        InterfaceUtils.colorGetAttrsColor(context,
                                                R.attr.recyclerItemDividerColor));

                                dividerItemDecoration.setDrawable(drawable);

                                fragmentRepositoryCommitsListBinding
                                        .recyclerViewFragmentRepositoryCommits
                                        .addItemDecoration(dividerItemDecoration);

                                Context context = fragmentRepositoryCommitsListBinding
                                        .recyclerViewFragmentRepositoryCommits.getContext();
                                fragmentRepositoryCommitsListBinding.recyclerViewFragmentRepositoryCommits
                                        .setLayoutManager(new LinearLayoutManager(context));
                                fragmentRepositoryCommitsListBinding.recyclerViewFragmentRepositoryCommits
                                        .setAdapter(recyclerViewGitLabRepositoryCommitAdapter);

                            } else {
                                InterfaceUtils.toastShowThemed(context,
                                        context.getString(R.string.gitlab_api_error_label)
                                                + " " + response.message() + " " + response.code(),
                                        Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<GitLabRepositoryCommit>> call,
                                              @NonNull Throwable t) {
                            InterfaceUtils.toastShowThemed(context,
                                    context.getResources().getString(R.string.error_try_again_label)
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG);
                        }
                    });
        }

        return fragmentRepositoryCommitsListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
