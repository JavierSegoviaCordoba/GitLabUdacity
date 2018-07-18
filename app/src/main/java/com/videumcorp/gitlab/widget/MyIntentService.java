package com.videumcorp.gitlab.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.videumcorp.gitlab.R;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                R.layout.projects_list_app_widget);
        Intent svcIntent = new Intent(getApplicationContext(), WidgetService.class);

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(R.id.listView, svcIntent);
        remoteViews.setEmptyView(R.id.listView, R.id.listView);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);

        Log.i("hola", "updateWidget: " + System.currentTimeMillis());
    }
}