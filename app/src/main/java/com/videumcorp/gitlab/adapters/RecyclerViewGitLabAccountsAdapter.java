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
import com.videumcorp.gitlab.databinding.AccountItemBinding;

import java.util.List;

public class RecyclerViewGitLabAccountsAdapter
        extends RecyclerView.Adapter<RecyclerViewGitLabAccountsAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewGitLabAccountsAdapter.class.getSimpleName();

    private Context context;
    private List<GitLabAccount> list;
    private OnItemClickListener onItemClickListener;
    private OnItemOptionButtonClickListener onItemOptionButtonClickListener;
    private int lastPosition = -1;

    public RecyclerViewGitLabAccountsAdapter(Context context, List<GitLabAccount> list,
                                             OnItemClickListener onItemClickListener,
                                             OnItemOptionButtonClickListener
                                                     onItemOptionButtonClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.onItemOptionButtonClickListener = onItemOptionButtonClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final AccountItemBinding accountItemBinding;


        ViewHolder(final AccountItemBinding accountItemBinding) {
            super(accountItemBinding.getRoot());
            this.accountItemBinding = accountItemBinding;
        }

        void bind(final GitLabAccount gitLabAccount, final OnItemClickListener onItemClickListener,
                  final OnItemOptionButtonClickListener onItemOptionButtonClickListener) {

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getLayoutPosition(),
                    gitLabAccount.getToken()));

            accountItemBinding.appCompatImageViewMenu
                    .setOnClickListener(v -> onItemOptionButtonClickListener
                            .onItemClick(getLayoutPosition(), gitLabAccount));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        AccountItemBinding accountItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.account_item, parent, false);

        return new ViewHolder(accountItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GitLabAccount item = list.get(position);

        GlideApp.with(context).load(item.getGitLabUser().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.accountItemBinding.appCompatImageViewAccountAvatar);
        holder.accountItemBinding.appCompatTextViewAccountItemUsername
                .setText(item.getGitLabUser().getUsername());
        holder.bind(item, onItemClickListener, onItemOptionButtonClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String token);
    }

    public interface OnItemOptionButtonClickListener {
        void onItemClick(int position, GitLabAccount gitLabAccount);
    }
}