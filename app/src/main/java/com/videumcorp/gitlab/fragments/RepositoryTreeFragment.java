package com.videumcorp.gitlab.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pddstudio.highlightjs.models.Theme;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.adapters.RecyclerViewGitLabRepositoryTreeAdapter;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.MySharedPreferences;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;
import com.videumcorp.gitlab.classes.network.RetrofitUtils;
import com.videumcorp.gitlab.databinding.FragmentRepositoryTreeListBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryTreeFragment extends Fragment {

    private static final String TAG = RepositoryTreeFragment.class.getName();

    private FragmentRepositoryTreeListBinding fragmentRepositoryTreeListBinding;

    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_PATH = "path";
    private static final String KEY_PRIVATE_TOKEN = "private_token";
    private static final String KEY_BRANCH_TAG_COMMIT = "BRANCH_TAG_COMMIT";

    private Context context;

    private int project_id;
    private String path;
    private String private_token;
    private String branch_tag_commit;

    private List<GitLabRepositoryTree> gitLabRepositoryTreeList = new ArrayList<>();

    private RecyclerViewGitLabRepositoryTreeAdapter recyclerViewGitLabRepositoryTreeAdapter;

    private int folder_level = 0;

    private List<List<GitLabRepositoryTree>> gitlabRepositoryTreeFolderLevelList = new ArrayList<>();

    public RepositoryTreeFragment() {
    }

    public static RepositoryTreeFragment newInstance(int project_id, String path,
                                                     String private_token,
                                                     String branch_tag_commit) {

        RepositoryTreeFragment fragment = new RepositoryTreeFragment();
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

        fragmentRepositoryTreeListBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_repository_tree_list, container, false);

        fragmentRepositoryTreeListBinding.swipeRefreshLayoutFragmentRepositoryTree
                .setEnabled(false);
        fragmentRepositoryTreeListBinding.swipeRefreshLayoutFragmentRepositoryTree
                .setRefreshing(true);

        Bundle bundle = getArguments();

        if (bundle != null) {
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
                            fragmentRepositoryTreeListBinding
                                    .swipeRefreshLayoutFragmentRepositoryTree
                                    .setRefreshing(false);

                            if (response.code() == 200) {

                                gitLabRepositoryTreeList = response.body();

                                gitlabRepositoryTreeFolderLevelList
                                        .add(folder_level, gitLabRepositoryTreeList);

                                recyclerViewGitLabRepositoryTreeAdapter =
                                        new RecyclerViewGitLabRepositoryTreeAdapter(
                                                gitLabRepositoryTreeList,
                                                private_token, new OnItemClickListener());

                                Context context = fragmentRepositoryTreeListBinding
                                        .recyclerViewFragmentRepositoryTree.getContext();


                                if (!InterfaceUtils.isTablet(context)) {

                                    DividerItemDecoration dividerItemDecoration =
                                            new DividerItemDecoration(context,
                                                    DividerItemDecoration.VERTICAL);

                                    Drawable drawable = InterfaceUtils.drawableTint(context,
                                            R.drawable.recyclerview_divider_decoration,
                                            InterfaceUtils.colorGetAttrsColor(context,
                                                    R.attr.recyclerItemDividerColor));

                                    dividerItemDecoration.setDrawable(drawable);

                                    fragmentRepositoryTreeListBinding
                                            .recyclerViewFragmentRepositoryTree
                                            .addItemDecoration(dividerItemDecoration);

                                    fragmentRepositoryTreeListBinding
                                            .recyclerViewFragmentRepositoryTree
                                            .setLayoutManager(new LinearLayoutManager(context));
                                } else {
                                    fragmentRepositoryTreeListBinding
                                            .recyclerViewFragmentRepositoryTree
                                            .setLayoutManager(new GridLayoutManager(context, 3));
                                }

                                fragmentRepositoryTreeListBinding.recyclerViewFragmentRepositoryTree
                                        .setAdapter(recyclerViewGitLabRepositoryTreeAdapter);

                            } else {
                                InterfaceUtils.toastShowThemed(context,
                                        context.getString(R.string.gitlab_api_error_label)
                                                + " " + response.message() + " " + response.code(),
                                        Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<GitLabRepositoryTree>> call,
                                              @NonNull Throwable t) {
                            InterfaceUtils.toastShowThemed(context,
                                    context.getResources()
                                            .getString(R.string.error_try_again_label)
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG);
                        }
                    });
        }

        setupFab();

        return fragmentRepositoryTreeListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupFab() {

        fragmentRepositoryTreeListBinding
                .floatingActionButtonFragmentRepositoryTree.setOnClickListener(v -> {
            if (fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                    .getVisibility() == View.VISIBLE
                    || fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                    .getVisibility() == View.VISIBLE) {
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setVisibility(View.GONE);
                fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                        .setVisibility(View.GONE);
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setSource(getString(R.string.loading_dots_label));
            } else {
                folder_level = folder_level - 1;
                if (folder_level >= 0) {
                    recyclerViewGitLabRepositoryTreeAdapter =
                            new RecyclerViewGitLabRepositoryTreeAdapter(
                                    gitlabRepositoryTreeFolderLevelList.get(folder_level),
                                    private_token, new OnItemClickListener());
                    fragmentRepositoryTreeListBinding.recyclerViewFragmentRepositoryTree
                            .setAdapter(recyclerViewGitLabRepositoryTreeAdapter);
                }
            }
            showFab();
        });
        showFab();
    }

    private void showFab() {
        changeFabIcon();
        if (folder_level > 0
                || fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                .getVisibility() == View.VISIBLE
                || fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                .getVisibility() == View.VISIBLE) {
            fragmentRepositoryTreeListBinding
                    .floatingActionButtonFragmentRepositoryTree.show();
        } else {
            fragmentRepositoryTreeListBinding
                    .floatingActionButtonFragmentRepositoryTree.hide();
        }
    }

    private void changeFabIcon() {
        if (fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                .getVisibility() == View.VISIBLE
                || fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                .getVisibility() == View.VISIBLE) {
            fragmentRepositoryTreeListBinding.floatingActionButtonFragmentRepositoryTree
                    .setImageResource(R.drawable.ic_clear_white_24dp);
        } else {
            fragmentRepositoryTreeListBinding.floatingActionButtonFragmentRepositoryTree
                    .setImageResource(R.drawable.ic_subdirectory_arrow_left_black_24dp);
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

    class OnItemClickListener implements RecyclerViewGitLabRepositoryTreeAdapter.OnItemClickListener {
        @Override
        public void onItemClick(int position, String type, String path, String private_token) {
            fragmentRepositoryTreeListBinding.swipeRefreshLayoutFragmentRepositoryTree
                    .setRefreshing(true);
            if (type.equals("tree")) {
                RetrofitUtils.getGitLabRepositoryTree(project_id, path, private_token,
                        branch_tag_commit, new Callback<List<GitLabRepositoryTree>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<GitLabRepositoryTree>> call,
                                                   @NonNull Response<List<GitLabRepositoryTree>>
                                                           response) {
                                fragmentRepositoryTreeListBinding.swipeRefreshLayoutFragmentRepositoryTree
                                        .setRefreshing(false);

                                if (response.code() == 200) {

                                    gitLabRepositoryTreeList = response.body();

                                    folder_level = folder_level + 1;

                                    gitlabRepositoryTreeFolderLevelList
                                            .add(folder_level, gitLabRepositoryTreeList);

                                    recyclerViewGitLabRepositoryTreeAdapter =
                                            new RecyclerViewGitLabRepositoryTreeAdapter(gitLabRepositoryTreeList,
                                                    private_token, new OnItemClickListener());
                                    fragmentRepositoryTreeListBinding.recyclerViewFragmentRepositoryTree
                                            .setAdapter(recyclerViewGitLabRepositoryTreeAdapter);

                                    showFab();
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
                                        context.getResources()
                                                .getString(R.string.error_try_again_label)
                                                + t.getMessage(),
                                        Toast.LENGTH_LONG);
                            }
                        });
            } else {
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree.getSettings();
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setBackgroundColor(InterfaceUtils
                                .colorGetAttrsColor(context, R.attr.colorPrimaryDark));
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setInitialScale(130);

                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setTheme(Theme.values()[MySharedPreferences.getThemeCodeView(context)]);
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setZoomSupportEnabled(true);
                fragmentRepositoryTreeListBinding.webViewFragmentRepositoryTree
                        .setShowLineNumbers(true);

                RetrofitUtils.getGitLabFile(project_id, path, private_token,
                        "master", new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call,
                                                   @NonNull Response<ResponseBody> response) {

                                fragmentRepositoryTreeListBinding.swipeRefreshLayoutFragmentRepositoryTree
                                        .setRefreshing(false);

                                if (response.code() == 200) {
                                    if (path.toLowerCase().endsWith(".md")) {
                                        fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                                                .loadMarkdownFile(call.request().url().toString(),
                                                        InterfaceUtils.getMarkdownTheme(context));
                                        fragmentRepositoryTreeListBinding.markdownFragmentRepositoryTree
                                                .setVisibility(View.VISIBLE);
                                        showFab();
                                    } else {
                                        try {
                                            URL url = new URL(call.request().url().toString());
                                            fragmentRepositoryTreeListBinding
                                                    .webViewFragmentRepositoryTree
                                                    .setSource(url);
                                            fragmentRepositoryTreeListBinding
                                                    .webViewFragmentRepositoryTree
                                                    .setVisibility(View.VISIBLE);
                                            showFab();
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    InterfaceUtils.toastShowThemed(context,
                                            context
                                                    .getString(R.string.gitlab_api_error_label)
                                                    + " " + response.message() + " "
                                                    + response.code(),
                                            Toast.LENGTH_LONG);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call,
                                                  @NonNull Throwable t) {
                                InterfaceUtils.toastShowThemed(context,
                                        context.getResources()
                                                .getString(R.string.error_try_again_label)
                                                + t.getMessage(),
                                        Toast.LENGTH_LONG);
                            }
                        });
            }
        }
    }
}
