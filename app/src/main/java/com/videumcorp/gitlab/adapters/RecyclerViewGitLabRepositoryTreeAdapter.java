package com.videumcorp.gitlab.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.databinding.FragmentRepositoryTreeItemBinding;
import com.videumcorp.gitlab.classes.gson.gitlabrepositorytree.GitLabRepositoryTree;

import java.util.List;

public class RecyclerViewGitLabRepositoryTreeAdapter extends RecyclerView.Adapter<RecyclerViewGitLabRepositoryTreeAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewGitLabRepositoryTreeAdapter.class.getName();

    private final List<GitLabRepositoryTree> gitLabRepositoryTreeList;
    private OnItemClickListener onItemClickListener;

    private static final String KEY_TREE = "tree";

    private Context context;
    private String private_token;


    public RecyclerViewGitLabRepositoryTreeAdapter(List<GitLabRepositoryTree> gitLabRepositoryTreeList,
                                                   String private_token,
                                                   OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
        this.gitLabRepositoryTreeList = gitLabRepositoryTreeList;
        this.private_token = private_token;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final FragmentRepositoryTreeItemBinding fragmentRepositoryTreeItemBinding;
        GitLabRepositoryTree gitLabRepositoryTree;

        ViewHolder(final FragmentRepositoryTreeItemBinding fragmentRepositoryTreeItemBinding) {
            super(fragmentRepositoryTreeItemBinding.getRoot());
            this.fragmentRepositoryTreeItemBinding = fragmentRepositoryTreeItemBinding;
        }

        void bind(final GitLabRepositoryTree gitLabRepositoryTree, final OnItemClickListener listener) {
            itemView.setOnClickListener(v ->
                    listener.onItemClick(getLayoutPosition(),
                            gitLabRepositoryTree.getType(),
                            gitLabRepositoryTree.getPath(),
                            private_token));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        FragmentRepositoryTreeItemBinding fragmentRepositoryTreeItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_repository_tree_item,
                        parent, false);
        return new ViewHolder(fragmentRepositoryTreeItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        GitLabRepositoryTree gitLabRepositoryTree = gitLabRepositoryTreeList.get(position);

        holder.gitLabRepositoryTree = gitLabRepositoryTree;
        holder.fragmentRepositoryTreeItemBinding
                .appCompatTextViewFileItemTitle.setText(gitLabRepositoryTree.getName());
        if (gitLabRepositoryTree.getType().equals(KEY_TREE)) {
            holder.fragmentRepositoryTreeItemBinding
                    .appCompatImageViewFile.setImageDrawable(InterfaceUtils.drawableTint(
                    context, R.drawable.ic_folder_white_24dp,
                    InterfaceUtils.colorGetAttrsColor(context,
                            R.attr.recyclerItemIconsColor)));
        } else {
            holder.fragmentRepositoryTreeItemBinding
                    .appCompatImageViewFile.setImageDrawable(InterfaceUtils.drawableTint(
                    context, R.drawable.ic_book_white_24dp,
                    InterfaceUtils.colorGetAttrsColor(context,
                            R.attr.recyclerItemIconsColor)));
        }

        holder.bind(gitLabRepositoryTree, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (gitLabRepositoryTreeList != null) {
            return gitLabRepositoryTreeList.size();
        } else {
            return 0;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position, String type, String path, String private_token);
    }
}
