package com.videumcorp.gitlab.classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.gitlab.R;

import java.util.Objects;

public class InterfaceUtils {

    public static int colorGetAttrsColor(Context context, int r_attr_color) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(r_attr_color, value, true);
        return value.data;
    }

    public static void toastShowThemed(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        View view = toast.getView();
        TextView textViewToast = view.findViewById(android.R.id.message);
        textViewToast.setTextColor(ContextCompat
                .getColor(context, R.color.darkPrimaryTextColor));
        view.setBackgroundTintList(ContextCompat
                .getColorStateList(context, R.color.darkSecondaryDarkColor));
        toast.show();
    }

    public static void snackbarShowThemed(Activity activity, String message, int duration) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), message, duration);
        //snackbar.setAction(android.R.string.ok, v -> snackbar.dismiss());
        //snackbar.setActionTextColor(activity.getResources()
        // .getColor(R.color.darkSecondaryLightColor));
        View view = snackbar.getView();
        TextView textViewToast = view.findViewById(android.support.design.R.id.snackbar_text);
        textViewToast.setTextColor(ContextCompat
                .getColor(activity, R.color.darkPrimaryTextColor));
        view.setBackgroundTintList(ContextCompat
                .getColorStateList(activity, R.color.darkPrimaryColor));
        Point point = getNavigationBarSize(activity);
        snackbar.getView().setPadding(0, 0, 0, point.y);
        snackbar.show();
    }

    public static Drawable drawableTint(Context context, int drawable_id, int color) {
        Drawable normalDrawable = context.getResources().getDrawable(drawable_id);
        normalDrawable.setTint(color);
        return normalDrawable;
    }

    public static ColorDrawable colorDrawableGetAttrsColor(Context context, int r_attr_color) {
        return new ColorDrawable(InterfaceUtils.colorGetAttrsColor(context,
                r_attr_color));
    }

    public static String setAppThemeNoActionBar(Context context) {
        switch (MySharedPreferences.getThemeApp(context)) {
            case "NORMAL":
                context.setTheme(R.style.AppTheme_Normal_NoActionBar);
                return "NORMAL";
            case "DARK":
                context.setTheme(R.style.AppTheme_Dark_NoActionBar);
                return "DARK";
            default:
                context.setTheme(R.style.AppTheme_Normal_NoActionBar);
                return "NORMAL";
        }
    }

    public static String setAppTheme(Context context) {
        switch (MySharedPreferences.getThemeApp(context)) {
            case "NORMAL":
                context.setTheme(R.style.AppTheme_Normal);
                return "NORMAL";
            case "DARK":
                context.setTheme(R.style.AppTheme_Dark);
                return "DARK";
            default:
                context.setTheme(R.style.AppTheme_Normal);
                return "NORMAL";
        }
    }

    public static String getMarkdownTheme(Context context) {
        String markdownTheme =
                "file:///android_asset/markdown_themes/dark.css";
        if (MySharedPreferences.getThemeApp(context).equals("NORMAL")) {
            markdownTheme =
                    "file:///android_asset/markdown_themes/light.css";
        }
        return markdownTheme;
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }


    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the side
        if (appUsableSize.x < Objects.requireNonNull(realScreenSize).x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    @SuppressWarnings("WeakerAccess")
    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
        Point size = new Point();
        if (display != null) {
            display.getSize(size);
        }
        return size;
    }

    @SuppressWarnings("WeakerAccess")
    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display;
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size);
            return size;
        }
        return null;
    }

    public static void setMargins(View view, int l, int t, int r, int b) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }
}

