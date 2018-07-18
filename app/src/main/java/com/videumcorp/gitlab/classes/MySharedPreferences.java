package com.videumcorp.gitlab.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences {

    private static final String TAG = MySharedPreferences.class.getName();

    private static final String PREFERENCES = "AppPreferences";
    private static final String GITLAB_ACCOUNT_SELECTED = "gitlab_account_selected";
    private static final String THEME_CODE_VIEW_SELECTED = "theme_code_view_selected";

    private MySharedPreferences() {
    }

    public static int getGitLabAccount(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(GITLAB_ACCOUNT_SELECTED, 0);
    }

    public static void setGitLabAccount(Context context, int account_selected) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GITLAB_ACCOUNT_SELECTED, account_selected);
        editor.apply();
    }

    public static String getThemeApp(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("app_themes_list", "DARK");
    }

    public static int getThemeCodeView(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(THEME_CODE_VIEW_SELECTED, 0);
    }

    public static void setThemeCodeView(Context context, int theme_selected) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEME_CODE_VIEW_SELECTED, theme_selected);
        editor.apply();
    }
}
