package com.videumcorp.gitlab.adapters;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.videumcorp.gitlab.GlideApp;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.user.GitLabAccount;
import com.videumcorp.gitlab.databinding.CircleAccountItemBinding;

import java.util.List;

public class RecyclerViewCircleGitLabAccountsAdapter
        extends RecyclerView.Adapter<RecyclerViewCircleGitLabAccountsAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewCircleGitLabAccountsAdapter.class.getSimpleName();

    private Context context;
    private List<GitLabAccount> list;
    private OnItemClickListener onItemClickListener;
    private int lastPosition = -1;

    public RecyclerViewCircleGitLabAccountsAdapter(Context context, List<GitLabAccount> list,
                                                   OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleAccountItemBinding circleAccountItemBinding;


        ViewHolder(final CircleAccountItemBinding circleAccountItemBinding) {
            super(circleAccountItemBinding.getRoot());
            this.circleAccountItemBinding = circleAccountItemBinding;
        }

        void bind(final GitLabAccount model, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener
                    .onItemClick(getLayoutPosition(), model.getToken()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        CircleAccountItemBinding circleAccountItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.circle_account_item, parent, false);

        return new ViewHolder(circleAccountItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitLabAccount item = list.get(position);

        GlideApp.with(context).load(item.getGitLabUser().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.circleAccountItemBinding.appCompatImageViewCircleGitLabAccount);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    interface OnItemClickListener {
        void onItemClick(int position, String token);
    }

}