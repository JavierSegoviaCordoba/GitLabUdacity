package com.videumcorp.gitlab.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.MySharedPreferences;
import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.classes.network.FirebaseUtils;
import com.videumcorp.gitlab.classes.user.User;

import java.util.ArrayList;

class ListProvider implements RemoteViewsFactory {

    private static final String TAG = ListProvider.class.getName();

    private ArrayList<GitLabProject> listItemList = new ArrayList<>();
    private Context context;
    private int appWidgetId;

    ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {

        final int[] selectedAccount = {MySharedPreferences.getGitLabAccount(context)};

        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    if (user.getGitLabAccountList() != null) {

                        if ((selectedAccount[0] < 0) &&
                                (selectedAccount[0] >= user.getGitLabAccountList().size())) {
                            selectedAccount[0] = 0;
                        }

                        if (user.getGitLabAccountList().get(selectedAccount[0]).getGitLabProjectList() != null) {
                            listItemList.addAll(user.getGitLabAccountList().get(selectedAccount[0]).getGitLabProjectList());
                            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView);
                        }

                    }
                }
            }

            @Override
            public void onError(DatabaseError error) {
                InterfaceUtils.toastShowThemed(context,
                        context.getResources().getString(R.string.error_try_again_label)
                                + error.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.project_item_widget);
        GitLabProject listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.textViewProjectItemTitle, listItem.getName());
        if (listItem.getDescription() != null) {
            if (!listItem.getDescription().equals("")) {
                remoteView.setTextViewText(R.id.textViewProjectItemDescription, listItem.getDescription());
            } else {
                remoteView.setTextViewText(R.id.textViewProjectItemDescription,
                        context.getResources().getString(R.string.no_description_label));

            }
        } else {
            remoteView.setTextViewText(R.id.textViewProjectItemDescription,
                    context.getResources().getString(R.string.no_description_label));
        }

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }
}