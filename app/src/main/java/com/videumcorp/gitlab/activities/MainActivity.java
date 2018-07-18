package com.videumcorp.gitlab.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.lapism.searchview.widget.SearchAdapter;
import com.lapism.searchview.widget.SearchItem;
import com.videumcorp.gitlab.GlideApp;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.adapters.RecyclerViewGitLabAccountsAdapter;
import com.videumcorp.gitlab.adapters.RecyclerViewProjectsAdapter;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.MySharedPreferences;
import com.videumcorp.gitlab.classes.gson.gitlabproject.GitLabProject;
import com.videumcorp.gitlab.classes.network.FirebaseUtils;
import com.videumcorp.gitlab.classes.network.RetrofitUtils;
import com.videumcorp.gitlab.classes.user.GitLabAccount;
import com.videumcorp.gitlab.classes.user.User;
import com.videumcorp.gitlab.databinding.ActivityMainBinding;
import com.videumcorp.gitlab.databinding.NavHeaderMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    private static final String KEY_PROJECT_NAME = "PROJECT_NAME";
    private static final String KEY_PROJECT_ID = "PROJECT_ID";
    private static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";

    private ActivityMainBinding activityMainbinding;
    private NavHeaderMainBinding navHeaderMainBinding;

    private int gitLabAccountSelected = 0;
    private String gitLabAccountSelectedToken = "";

    private User user = new User();
    private List<GitLabAccount> gitLabAccountList = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManagerProjects;
    private RecyclerView.LayoutManager layoutManagerGitLabAccounts;
    private RecyclerViewProjectsAdapter recyclerViewProjectsAdapter = null;
    private RecyclerViewGitLabAccountsAdapter recyclerViewGitLabAccountsAdapter = null;
    private DividerItemDecoration dividerItemDecoration;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InterfaceUtils.setAppThemeNoActionBar(this);
        super.onCreate(savedInstanceState);
        activityMainbinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHeaderMainBinding = NavHeaderMainBinding
                .bind(activityMainbinding.navView.getHeaderView(0));

        activity = this;

        setSupportActionBar(activityMainbinding.includedAppBarMain.toolbar);

        setupAdmob();

        navigationDrawerSetup();

        recyclerViewsSetup();

        // If has GoogleAccount, get data from it.
        // Else launch Google Sign In activity
        checkUserHasGoogleAccount(FirebaseUtils.getAuthUser());

        // If has GitLabAccounts, get data from it.
        // Else launch Add GitLab Accounts activity
        checkUserHasGitLabAccounts();
    }

    private void setupAdmob() {
        AdRequest adRequest = new AdRequest.Builder().build();
        activityMainbinding.includedAppBarMain.includedContentMain.adView.loadAd(adRequest);
    }

    private void setupSearchView(List<SearchItem> suggestions,
                                 List<GitLabProject> gitLabProjectList, String access_token) {

        SearchAdapter searchAdapter = new SearchAdapter(this);
        searchAdapter.setSuggestionsList(suggestions);
        searchAdapter.setOnSearchItemClickListener((position, title, subtitle) -> {
            if (gitLabProjectList != null) {
                Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
                Bundle bundle = new Bundle();
                for (int i = 0; i < gitLabProjectList.size(); i++) {
                    if (title.toString().equals(gitLabProjectList.get(i).getName())) {
                        bundle.putString(KEY_PROJECT_NAME, gitLabProjectList.get(i).getName());
                        bundle.putInt(KEY_PROJECT_ID, gitLabProjectList.get(i).getId());
                        bundle.putString(KEY_ACCESS_TOKEN, access_token);
                        intent.putExtras(bundle);
                        break;
                    }
                }
                startActivity(intent);
            }
        });
        activityMainbinding.includedAppBarMain.searchView.setAdapter(searchAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getGitLabAccount(String token) {
        RetrofitUtils.getGitLabAccount(this, token, new RetrofitUtils.GitlabAccountInterface() {
            @Override
            public void onSuccess(GitLabAccount gitlabAccount) {
                gitLabAccountList.add(gitlabAccount);
                user.setGitLabAccountList(gitLabAccountList);
                FirebaseUtils.writeUserToDatabase(user);
                //updateRecyclerViewCircleAccountList();
            }

            @Override
            public void onError(Throwable t) {
                InterfaceUtils.toastShowThemed(getApplicationContext(),
                        getResources().getString(R.string.error_try_again_label)
                                + t.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    private void checkUserHasGoogleAccount(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            //Update Firebase user data
            user.setGoogleAccount(FirebaseUtils.getGoogleAccount());
        } else {
            //Send user to the Google Sign In activity
            startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
        }
    }

    private void checkUserHasGitLabAccounts() {
        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    if (user.getGitLabAccountList() != null) {
                        updateFirebaseDatabase(user);
                        updateRecyclerViews(false);
                    } else {
                        startActivity(
                                new Intent(MainActivity.this, AddGitLabAccountActivity.class));
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                }
            }

            @Override
            public void onError(DatabaseError error) {
                InterfaceUtils.toastShowThemed(getApplicationContext(),
                        getResources().getString(R.string.error_try_again_label)
                                + error.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    private void updateFirebaseDatabase(User user) {
        for (int i = 0; i < user.getGitLabAccountList().size(); i++) {
            getGitLabAccount(user.getGitLabAccountList().get(i).getToken());
        }
    }

    private void recyclerViewsSetup() {

        // Projects
        if (!InterfaceUtils.isTablet(this)) {
            layoutManagerProjects = new LinearLayoutManager(getApplicationContext());
            dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL);
            Drawable drawable = InterfaceUtils.drawableTint(this,
                    R.drawable.recyclerview_divider_decoration,
                    InterfaceUtils.colorGetAttrsColor(this, R.attr.recyclerItemDividerColor));

            dividerItemDecoration.setDrawable(drawable);
            activityMainbinding.includedAppBarMain.includedContentMain.recyclerViewProjects
                    .addItemDecoration(dividerItemDecoration);
        } else {
            layoutManagerProjects = new GridLayoutManager(getApplicationContext(), 3);
        }

        activityMainbinding.includedAppBarMain.includedContentMain.recyclerViewProjects
                .setLayoutManager(layoutManagerProjects);

        activityMainbinding.includedAppBarMain.includedContentMain.recyclerViewProjects
                .setItemAnimator(new DefaultItemAnimator());

        // GitLab accounts
        layoutManagerGitLabAccounts = new LinearLayoutManager(getApplicationContext());
        navHeaderMainBinding.recyclerViewGitLabAccounts.setLayoutManager(layoutManagerGitLabAccounts);
        navHeaderMainBinding.recyclerViewGitLabAccounts.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateRecyclerViews(boolean starred) {

        //Check if the user has changed the selected account to update, or not, the RecyclerViewAccounts
        int gitLabAccountSelectedTemp = gitLabAccountSelected;
        //Add the selected account data to the RecyclerViews
        gitLabAccountSelected = MySharedPreferences.getGitLabAccount(this);

        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(User user) {

                List<GitLabProject> gitLabProjectListTemp;
                List<GitLabAccount> gitLabAccountListTemp;

                if (user != null && user.getGitLabAccountList() != null) {

                    if ((gitLabAccountSelected < 0)
                            || (gitLabAccountSelected >= user.getGitLabAccountList().size())) {
                        gitLabAccountSelected = 0;
                    }

                    if (!starred) {
                        if (user.getGitLabAccountList().get(gitLabAccountSelected).getGitLabProjectList() != null) {
                            gitLabProjectListTemp = new ArrayList<>(user.getGitLabAccountList()
                                    .get(gitLabAccountSelected).getGitLabProjectList());
                        } else {
                            gitLabProjectListTemp = new ArrayList<>();
                        }
                    } else {
                        if (user.getGitLabAccountList().get(gitLabAccountSelected).getGitLabStarredProjectList() != null) {
                            gitLabProjectListTemp = new ArrayList<>(user.getGitLabAccountList()
                                    .get(gitLabAccountSelected).getGitLabStarredProjectList());
                        } else {
                            gitLabProjectListTemp = new ArrayList<>();
                        }
                    }
                    gitLabAccountListTemp = new ArrayList<>(user.getGitLabAccountList());
                } else {
                    gitLabProjectListTemp = new ArrayList<>();
                    gitLabAccountListTemp = new ArrayList<>();
                }

                Objects.requireNonNull(getSupportActionBar()).setTitle(gitLabAccountListTemp
                        .get(gitLabAccountSelected).getGitLabUser().getUsername());

                gitLabAccountSelectedToken = gitLabAccountListTemp
                        .get(gitLabAccountSelected).getToken();

                //Fill SearchView suggestion list
                List<SearchItem> searchItemList = new ArrayList<>();
                if (gitLabAccountListTemp.size() != 0) {
                    for (int i = 0; i < gitLabProjectListTemp.size(); i++) {
                        SearchItem searchItem = new SearchItem(getApplicationContext());
                        searchItem.setIcon1Resource(R.drawable.ic_book_white_24dp);
                        searchItem.setTitle(gitLabProjectListTemp.get(i).getName());
                        searchItem.setSubtitle(gitLabProjectListTemp.get(i).getDescription());
                        searchItemList.add(searchItem);
                    }
                    setupSearchView(searchItemList, gitLabProjectListTemp,
                            gitLabAccountSelectedToken);
                } else {
                    searchItemList.clear();
                    setupSearchView(searchItemList, null, "");
                }

                recyclerViewProjectsAdapter = new RecyclerViewProjectsAdapter(activity,
                        gitLabAccountSelectedToken,
                        gitLabProjectListTemp, (position, projectName, projectID) -> {
                    Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_PROJECT_NAME, projectName);
                    bundle.putInt(KEY_PROJECT_ID, projectID);
                    bundle.putString(KEY_ACCESS_TOKEN,
                            gitLabAccountSelectedToken);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
                activityMainbinding.includedAppBarMain.includedContentMain.recyclerViewProjects
                        .setAdapter(recyclerViewProjectsAdapter);

                // Check if the user changed the account of if this is the first update
                if (gitLabAccountSelectedTemp != gitLabAccountSelected || recyclerViewGitLabAccountsAdapter == null) {
                    recyclerViewGitLabAccountsAdapter = new RecyclerViewGitLabAccountsAdapter(
                            getApplicationContext(),
                            gitLabAccountListTemp, (position, token) -> {
                        MySharedPreferences.setGitLabAccount(MainActivity.this, position);
                        updateRecyclerViews(starred);
                    }, (position, gitLabAccount) ->
                            deleteGitLabAccount(user, gitLabAccountListTemp, gitLabAccount));


                    navHeaderMainBinding.recyclerViewGitLabAccounts
                            .setAdapter(recyclerViewGitLabAccountsAdapter);

                    GlideApp.with(getApplicationContext()).asBitmap()
                            .load(gitLabAccountListTemp.get(gitLabAccountSelected).getGitLabUser()
                                    .getAvatarUrl())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .listener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                            Target<Bitmap> target,
                                                            boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model,
                                                               Target<Bitmap> target,
                                                               DataSource dataSource,
                                                               boolean isFirstResource) {
                                    onPalette(Palette.from(resource).generate());
                                    navHeaderMainBinding.appCompatImageViewHeaderLogo
                                            .setImageBitmap(resource);
                                    return false;
                                }

                                void onPalette(Palette palette) {
                                    if (null != palette) {
                                        int color = palette.getDarkVibrantColor(getResources()
                                                .getColor(R.color.darkSecondaryDarkColor));
                                        navHeaderMainBinding.constrainlayoutNavHeaderUser
                                                .setBackgroundColor(color);
                                    }
                                }
                            }).into(navHeaderMainBinding.appCompatImageViewHeaderLogo);

                    navHeaderMainBinding.appCompatTextViewNavHeaderTitle
                            .setText(gitLabAccountListTemp.get(gitLabAccountSelected).getGitLabUser().getUsername());
                    navHeaderMainBinding.appCompatTextViewNavHeaderSubtitle
                            .setText(gitLabAccountListTemp.get(gitLabAccountSelected).getGitLabUser().getEmail());
                }
            }

            @Override
            public void onError(DatabaseError error) {
                InterfaceUtils.toastShowThemed(getApplicationContext(),
                        getResources().getString(R.string.error_try_again_label)
                                + error.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }

    private void deleteGitLabAccount(User user, List<GitLabAccount> gitLabAccountList,
                                     GitLabAccount gitLabAccount) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_account_label);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.yes_label, (dialog, id) -> {
            if (user != null) {
                gitLabAccountList.remove(gitLabAccount);
                user.setGitLabAccountList(gitLabAccountList);
                FirebaseUtils.writeUserToDatabase(user);
                checkUserHasGitLabAccounts();
            }
            dialog.cancel();
        });

        builder.setNegativeButton(R.string.no_label, (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void navigationDrawerSetup() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, activityMainbinding.drawerLayout,
                        activityMainbinding.includedAppBarMain.toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        activityMainbinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        activityMainbinding.navView.setNavigationItemSelectedListener(this);

        navHeaderMainBinding.appCompatImageViewNavigationViewAccountSwitcher
                .setOnClickListener(v -> {

                    if (navHeaderMainBinding.linearLayoutNavHeaderAccountList.getVisibility() ==
                            View.VISIBLE) {
                        navHeaderMainBinding.linearLayoutNavHeaderAccountList
                                .setVisibility(View.GONE);
                        navHeaderMainBinding.appCompatImageViewNavigationViewAccountSwitcher
                                .animate().rotation(0);
                    } else {
                        navHeaderMainBinding.linearLayoutNavHeaderAccountList
                                .setVisibility(View.VISIBLE);
                        navHeaderMainBinding.appCompatImageViewNavigationViewAccountSwitcher
                                .animate().rotation(180);
                    }
                });

        navHeaderMainBinding.linearLayoutAddGitLabAccount.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,
                        AddGitLabAccountActivity.class)));

        activityMainbinding.navView.setCheckedItem(R.id.navigationViewItemProjects);

        StateListDrawable stateListDrawableBackgrounds = new StateListDrawable();

        ColorDrawable colorDrawableItemBackgroundSelectedColor = InterfaceUtils
                .colorDrawableGetAttrsColor(this,
                        R.attr.navigationViewItemBackgroundSelectedColor);
        ColorDrawable colorDrawableItemBackgroundColor = InterfaceUtils
                .colorDrawableGetAttrsColor(this,
                        R.attr.navigationViewItemBackgroundColor);

        stateListDrawableBackgrounds
                .addState(new int[]{android.R.attr.state_checked},
                        colorDrawableItemBackgroundSelectedColor);
        stateListDrawableBackgrounds
                .addState(new int[]{},
                        colorDrawableItemBackgroundColor);

        activityMainbinding.navView
                .setItemBackground(stateListDrawableBackgrounds);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle toolbar item clicks here.
        switch (item.getItemId()) {
            case R.id.action_search:
                activityMainbinding.includedAppBarMain.searchView.open(item);
                final Handler handler = new Handler();
                handler.postDelayed(() -> activityMainbinding.includedAppBarMain.searchView
                        .setLogoHamburgerToLogoArrowWithAnimation(true), 150);
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.navigationViewItemProjects:
                updateRecyclerViews(false);
                break;
            case R.id.navigationViewItemStarred:
                updateRecyclerViews(true);
                break;
            case R.id.navigationViewItemGoogleSync:
                startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                break;
            case R.id.navigationViewItemSettings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.navigationViewItemAbout:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}