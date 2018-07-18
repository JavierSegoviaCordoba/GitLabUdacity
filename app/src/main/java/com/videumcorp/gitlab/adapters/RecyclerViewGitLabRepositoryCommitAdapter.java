package com.videumcorp.gitlab.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorycommit.GitLabRepositoryCommit;
import com.videumcorp.gitlab.databinding.FragmentRepositoryCommitItemBinding;

import java.util.List;

public class RecyclerViewGitLabRepositoryCommitAdapter extends RecyclerView.Adapter<RecyclerViewGitLabRepositoryCommitAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewGitLabRepositoryCommitAdapter.class.getName();

    private final List<GitLabRepositoryCommit> gitLabRepositoryCommitList;

    private static final String KEY_TREE = "tree";

    private Context context;
    private String private_token;


    public RecyclerViewGitLabRepositoryCommitAdapter(List<GitLabRepositoryCommit> gitLabRepositoryCommitList,
                                                     String private_token) {
        this.gitLabRepositoryCommitList = gitLabRepositoryCommitList;
        this.private_token = private_token;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final FragmentRepositoryCommitItemBinding fragmentRepositoryCommitItemBinding;
        GitLabRepositoryCommit gitLabRepositoryCommit;

        ViewHolder(final FragmentRepositoryCommitItemBinding fragmentRepositoryCommitItemBinding) {
            super(fragmentRepositoryCommitItemBinding.getRoot());
            this.fragmentRepositoryCommitItemBinding = fragmentRepositoryCommitItemBinding;
        }

        void bind(final GitLabRepositoryCommit gitLabRepositoryCommit) {
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        FragmentRepositoryCommitItemBinding fragmentRepositoryCommitItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_repository_commit_item,
                        parent, false);
        return new ViewHolder(fragmentRepositoryCommitItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        GitLabRepositoryCommit gitLabRepositoryCommit = gitLabRepositoryCommitList.get(position);

        holder.gitLabRepositoryCommit = gitLabRepositoryCommit;
        holder.fragmentRepositoryCommitItemBinding
                .appCompatTextViewCommitItemTitle.setText(gitLabRepositoryCommit.getTitle());
        holder.fragmentRepositoryCommitItemBinding
                .appCompatTextViewCommitItemDescription.setText(gitLabRepositoryCommit.getMessage());
        holder.bind(gitLabRepositoryCommit);
    }

    @Override
    public int getItemCount() {
        if (gitLabRepositoryCommitList != null) {
            return gitLabRepositoryCommitList.size();
        } else {
            return 0;
        }
    }
}
