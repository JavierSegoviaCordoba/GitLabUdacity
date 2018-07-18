package com.videumcorp.gitlab.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding aboutActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InterfaceUtils.setAppTheme(this);
        super.onCreate(savedInstanceState);
        aboutActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        aboutActivityBinding.markdownAboutActivity
                .loadMarkdownFile("file:///android_asset/README.md",
                        InterfaceUtils.getMarkdownTheme(this));
    }
}
