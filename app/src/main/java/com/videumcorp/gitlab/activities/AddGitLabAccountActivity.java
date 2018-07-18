package com.videumcorp.gitlab.activities;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.adapters.RecyclerViewCircleGitLabAccountsAdapter;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.network.FirebaseUtils;
import com.videumcorp.gitlab.classes.network.RetrofitUtils;
import com.videumcorp.gitlab.classes.user.GitLabAccount;
import com.videumcorp.gitlab.classes.user.User;
import com.videumcorp.gitlab.databinding.ActivityAddGitlabAccountBinding;

import java.util.ArrayList;
import java.util.List;

public class AddGitLabAccountActivity extends AppCompatActivity {

    private static final String TAG = AddGitLabAccountActivity.class.getName();

    private ActivityAddGitlabAccountBinding activityAddGitlabAccountBinding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewCircleGitLabAccountsAdapter recyclerViewCircleGitLabAccountsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InterfaceUtils.setAppTheme(this);
        super.onCreate(savedInstanceState);
        activityAddGitlabAccountBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_add_gitlab_account);

        activityAddGitlabAccountBinding.appCompatButtonCreateToken
                .setOnClickListener(v -> createGitlabToken());

        activityAddGitlabAccountBinding.appCompatButtonAddAccount
                .setOnClickListener(v -> addGitLabAccount());

        updateRecyclerViewCircleAccountList();
    }

    private void updateRecyclerViewCircleAccountList() {
        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(User user) {

                List<GitLabAccount> gitLabAccountListTemp;
                if (user != null) {
                    if (user.getGitLabAccountList() != null) {
                        gitLabAccountListTemp = new ArrayList<>(user.getGitLabAccountList());
                        recyclerViewCircleGitLabAccountsAdapter = new RecyclerViewCircleGitLabAccountsAdapter
                                (getApplicationContext(),
                                        gitLabAccountListTemp, null);

                        layoutManager = new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.HORIZONTAL, false);
                        activityAddGitlabAccountBinding.recyclerViewAccountList
                                .setLayoutManager(layoutManager);
                        activityAddGitlabAccountBinding.recyclerViewAccountList
                                .setHasFixedSize(true);
                        activityAddGitlabAccountBinding.recyclerViewAccountList
                                .setAdapter(recyclerViewCircleGitLabAccountsAdapter);

                        activityAddGitlabAccountBinding.appCompatEditTextAccessToken.setText("");
                    }
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

    private void createGitlabToken() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.darkPrimaryColor));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(
                getResources().getString(R.string.gitlab_access_token_url)));
    }

    private void addGitLabAccount() {
        final String token =
                activityAddGitlabAccountBinding.appCompatEditTextAccessToken.getText().toString();

        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(final User user) {
                RetrofitUtils.getGitLabAccount(getApplicationContext(), token,
                        new RetrofitUtils.GitlabAccountInterface() {
                            @Override
                            public void onSuccess(GitLabAccount gitlabAccount) {
                                if (user != null) {
                                    if (user.getGitLabAccountList() != null) {

                                        boolean account_added = false;

                                        for (int i = 0; i < user.getGitLabAccountList().size(); i++) {
                                            if (user.getGitLabAccountList()
                                                    .get(i).getGitLabUser().getUsername()
                                                    .equals(gitlabAccount.getGitLabUser()
                                                            .getUsername())) {
                                                account_added = true;
                                            }
                                        }

                                        if (!account_added) {
                                            user.getGitLabAccountList().add(gitlabAccount);
                                            FirebaseUtils.writeUserToDatabase(user);
                                            updateRecyclerViewCircleAccountList();
                                            InterfaceUtils.toastShowThemed(getApplicationContext(),
                                                    getString(R.string.add_account_success_label),
                                                    Toast.LENGTH_LONG);
                                        } else {
                                            InterfaceUtils.toastShowThemed(getApplicationContext(),
                                                    getString(R.string.add_account_added_label),
                                                    Toast.LENGTH_LONG);
                                        }
                                    } else {
                                        List<GitLabAccount> gitLabAccountsListTemp = new ArrayList<>();
                                        gitLabAccountsListTemp.add(gitlabAccount);
                                        user.setGitLabAccountList(gitLabAccountsListTemp);
                                        FirebaseUtils.writeUserToDatabase(user);
                                        updateRecyclerViewCircleAccountList();
                                    }
                                }
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

            @Override
            public void onError(DatabaseError error) {
                InterfaceUtils.toastShowThemed(getApplicationContext(),
                        getResources().getString(R.string.error_try_again_label) +
                                error.getMessage(),
                        Toast.LENGTH_LONG);
            }
        });
    }
}
