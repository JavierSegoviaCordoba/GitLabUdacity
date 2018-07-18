package com.videumcorp.gitlab.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.databinding.ActivityProjectBinding;
import com.videumcorp.gitlab.fragments.ViewPagerProjectActivityAdapter;

import java.util.Objects;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = ProjectActivity.class.getName();

    private static final String KEY_PROJECT_NAME = "PROJECT_NAME";
    private static final String KEY_PROJECT_ID = "PROJECT_ID";
    private static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";

    private ActivityProjectBinding activityProjectBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InterfaceUtils.setAppThemeNoActionBar(this);
        super.onCreate(savedInstanceState);
        activityProjectBinding = DataBindingUtil.setContentView(this, R.layout.activity_project);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(InterfaceUtils
                .colorGetAttrsColor(this, R.attr.colorPrimaryDark));

        setSupportActionBar(activityProjectBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupAdmob();

        setupViewPager();
    }

    private void setupViewPager() {
        TabLayout tabLayout = activityProjectBinding.tabLayoutProject;
        final ViewPager viewPager = activityProjectBinding.viewPagerProject;

        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();

        String project_name = intent.getStringExtra(KEY_PROJECT_NAME);
        int project_id = intent.getIntExtra(KEY_PROJECT_ID, -1);
        String private_token = intent.getStringExtra(KEY_ACCESS_TOKEN);

        if (project_id != -1 || private_token != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(project_name);
            final ViewPagerProjectActivityAdapter adapter = new ViewPagerProjectActivityAdapter
                    (getSupportFragmentManager(), project_id, private_token);
            viewPager.setAdapter(adapter);
        } else {
            InterfaceUtils.toastShowThemed(this,
                    getResources().getString(R.string.error_try_again_label), Toast.LENGTH_LONG);
            startActivity(new Intent(ProjectActivity.this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupAdmob() {
        AdRequest adRequest = new AdRequest.Builder().build();
        activityProjectBinding.adView.loadAd(adRequest);
    }
}
