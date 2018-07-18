package com.videumcorp.gitlab.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.pddstudio.highlightjs.models.Theme;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.MySharedPreferences;
import com.videumcorp.gitlab.databinding.ActivityCodeViewBinding;

import java.util.Objects;

public class CodeViewActivity extends AppCompatActivity {

    private static final String TAG = CodeViewActivity.class.getName();

    private ActivityCodeViewBinding activityCodeViewbinding;

    private String example = "";
    private int theme_selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InterfaceUtils.setAppThemeNoActionBar(this);
        activityCodeViewbinding = DataBindingUtil
                .setContentView(this, R.layout.activity_code_view);

        setupToolbar();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupWebViewCodeView();

        setupSpinner();
    }

    private void setupWebViewCodeView() {

        activityCodeViewbinding.swipeRefreshLayoutCodeView.setEnabled(false);

        activityCodeViewbinding.swipeRefreshLayoutCodeView.setRefreshing(true);

        activityCodeViewbinding.webViewCodeView.getSettings();
        activityCodeViewbinding.webViewCodeView
                .setBackgroundColor(InterfaceUtils.colorGetAttrsColor(this, R.attr.colorPrimaryDark));

        activityCodeViewbinding.webViewCodeView
                .setTheme(Theme.values()[MySharedPreferences.getThemeCodeView(this)]);
        activityCodeViewbinding.webViewCodeView.setZoomSupportEnabled(true);
        activityCodeViewbinding.webViewCodeView.setShowLineNumbers(true);

        example = "package com.example.app;\n" +
                "import android.support.v7.app.AppCompatActivity;\n" +
                "import android.os.Bundle;\n" +
                "\n" +
                "public class ExampleActivity extends AppCompatActivity {\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "    }\n" +
                "}";

        activityCodeViewbinding.webViewCodeView.setOnContentChangedListener(() ->
                activityCodeViewbinding.swipeRefreshLayoutCodeView.setRefreshing(false));

        activityCodeViewbinding.webViewCodeView.setSource(example);
    }

    private void setupSpinner() {

        Theme theme[] = Theme.values();

        String themes[] = new String[theme.length];

        for (int i = 0; i < theme.length; i++) {
            themes[i] = theme[i].toString();
        }

        if (themes.length > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, themes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activityCodeViewbinding.spinnerCodeView.setAdapter(adapter);


            activityCodeViewbinding.spinnerCodeView
                    .setSelection(MySharedPreferences.getThemeCodeView(getApplicationContext()));

            activityCodeViewbinding.spinnerCodeView
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position,
                                                   long id) {
                            activityCodeViewbinding.swipeRefreshLayoutCodeView.setRefreshing(true);
                            activityCodeViewbinding.webViewCodeView.setTheme(theme[position]);
                            activityCodeViewbinding.webViewCodeView
                                    .setOnContentChangedListener(() ->
                                            activityCodeViewbinding.swipeRefreshLayoutCodeView
                                                    .setRefreshing(false));
                            activityCodeViewbinding.webViewCodeView.setSource(example);
                            theme_selected = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
        }

        activityCodeViewbinding.fab.setOnClickListener(view -> {
            Snackbar.make(view, "Selected theme: "
                    + themes[theme_selected], Snackbar.LENGTH_LONG).show();

            MySharedPreferences.setThemeCodeView(getApplicationContext(), theme_selected);
        });
    }

    private void setupToolbar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(InterfaceUtils
                .colorGetAttrsColor(this, R.attr.colorPrimaryDark));
        setSupportActionBar(activityCodeViewbinding.toolbar);
    }

}
