package com.videumcorp.gitlab.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerProjectActivityAdapter extends FragmentStatePagerAdapter {

    private int project_id;
    private String private_token;

    public ViewPagerProjectActivityAdapter(FragmentManager fragmentManager, int project_id,
                                           String private_token) {
        super(fragmentManager);
        this.project_id = project_id;
        this.private_token = private_token;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ReadmeFragment.newInstance(project_id, "", private_token,
                        "master");
            case 1:
                return RepositoryTreeFragment.newInstance(project_id, "", private_token,
                        "master");
            case 2:
                return RepositoryCommitsFragment.newInstance(project_id, private_token,
                        "master");
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "README";
            case 1:
                return "FILES";
            case 2:
                return "COMMITS";
            default:
                return "README";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}