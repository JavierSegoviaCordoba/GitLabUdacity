package com.videumcorp.gitlab.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.videumcorp.gitlab.GlideApp;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.databinding.ProjectItemBinding;

import java.util.List;

public class RecyclerViewProjectsAdapter
        extends RecyclerView.Adapter<RecyclerViewProjectsAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewProjectsAdapter.class.getSimpleName();

    private Context context;
    private String private_token;
    private List<GitLabProject> gitLabProjectList;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewProjectsAdapter(Context context, String private_token,
                                       List<GitLabProject> gitLabProjectList,
                                       OnItemClickListener onItemClickListener) {
        this.private_token = private_token;
        this.context = context;
        this.gitLabProjectList = gitLabProjectList;
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ProjectItemBinding projectItemBinding;


        ViewHolder(final ProjectItemBinding projectItemBinding) {
            super(projectItemBinding.getRoot());
            this.projectItemBinding = projectItemBinding;
        }

        void bind(final GitLabProject gitLabProject, final OnItemClickListener listener) {
            itemView.setOnClickListener(
                    v -> listener.onItemClick(getLayoutPosition(), gitLabProject.getName(),
                            gitLabProject.getId()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        ProjectItemBinding projectItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.project_item, parent, false);

        return new ViewHolder(projectItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitLabProject gitLabProject = gitLabProjectList.get(position);

        if (gitLabProject.getAvatarUrl() != null) {
            String url = gitLabProject.getAvatarUrl() +
                    context.getString(R.string.private_token_query) + private_token;
            GlideApp.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.projectItemBinding.appCompatImageViewProjectAvatar);
            holder.projectItemBinding.appCompatTextViewProjectLetter.setVisibility(View.GONE);

        } else {
            int color;
            if (!InterfaceUtils.isTablet(context)){
                color = InterfaceUtils.colorGetAttrsColor(context,
                        R.attr.recyclerItemCircleBackgroundColor);
            } else {
                color = InterfaceUtils.colorGetAttrsColor(context,
                        R.attr.generalSecondaryBackground);
            }

            Drawable drawable = InterfaceUtils
                    .drawableTint(context, R.drawable.circle_background, color);

            GlideApp.with(context).load("").placeholder(drawable)
                    .into(holder.projectItemBinding.appCompatImageViewProjectAvatar);

            holder.projectItemBinding.appCompatTextViewProjectLetter
                    .setText(gitLabProject.getName().substring(0, 1).toUpperCase());
            holder.projectItemBinding.appCompatTextViewProjectLetter.setVisibility(View.VISIBLE);

        }

        holder.projectItemBinding.appCompatTextViewProjectItemTitle.setText(gitLabProject.getName());
        if (gitLabProject.getDescription() != null) {
            if (!gitLabProject.getDescription().equals("")) {
                holder.projectItemBinding.appCompatTextViewProjectItemDescription
                        .setTypeface(null, Typeface.NORMAL);
                holder.projectItemBinding.appCompatTextViewProjectItemDescription
                        .setText(gitLabProject.getDescription());
            } else {
                holder.projectItemBinding.appCompatTextViewProjectItemDescription
                        .setTypeface(null, Typeface.ITALIC);
                holder.projectItemBinding.appCompatTextViewProjectItemDescription
                        .setText(R.string.no_description_label);
            }
        } else {
            holder.projectItemBinding.appCompatTextViewProjectItemDescription
                    .setTypeface(null, Typeface.ITALIC);
            holder.projectItemBinding.appCompatTextViewProjectItemDescription
                    .setText(R.string.no_description_label);
        }

        holder.bind(gitLabProject, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return gitLabProjectList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String projectName, int projectID);
    }

}