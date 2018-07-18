package com.videumcorp.gitlab.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.videumcorp.gitlab.GlideApp;
import com.videumcorp.gitlab.R;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.network.FirebaseUtils;
import com.videumcorp.gitlab.classes.user.User;
import com.videumcorp.gitlab.databinding.ActivityGoogleSignInBinding;

public class GoogleSignInActivity extends AppCompatActivity {

    private static final String TAG = GoogleSignInActivity.class.getName();

    private static final int REQUEST_CODE = 468;

    private ActivityGoogleSignInBinding activityGoogleSignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InterfaceUtils.setAppTheme(this);
        super.onCreate(savedInstanceState);
        activityGoogleSignInBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_google_sign_in);


        if (FirebaseUtils.getAuthUser() != null) {
            updateGoogleUserDataUI(FirebaseUtils.getAuthUser());
        } else {
            showSignInUI(true);
        }
    }

    //TODO animations
    private void showSignInUI(boolean show) {
        if (show) {
            activityGoogleSignInBinding.linearLayoutSigned.setVisibility(View.GONE);
            activityGoogleSignInBinding.linearLayoutSignIn.setVisibility(View.VISIBLE);
            activityGoogleSignInBinding.signInButton.setOnClickListener(v -> signIn());
        } else {
            activityGoogleSignInBinding.linearLayoutSigned.setVisibility(View.VISIBLE);
            activityGoogleSignInBinding.linearLayoutSignIn.setVisibility(View.GONE);
        }
    }

    private void updateGoogleUserDataUI(FirebaseUser firebaseUser) {
        activityGoogleSignInBinding.appCompatTextViewUsername
                .setText(firebaseUser.getDisplayName());
        activityGoogleSignInBinding.appCompatTextViewUsernameEmail.setText(firebaseUser.getEmail());

        GlideApp.with(this).load(firebaseUser.getPhotoUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(activityGoogleSignInBinding.appCompatImageViewUsernamePhoto);

        showSignInUI(false);

        activityGoogleSignInBinding.appCompatButtonSignOut.setOnClickListener(v -> {
            showSignInUI(true);
            FirebaseAuth.getInstance().signOut();
        });

        syncDatabase();
    }

    private void syncDatabase() {
        FirebaseUtils.readUserFromDatabase(new FirebaseUtils.FirebaseDatabaseGetUser() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    user = new User();
                }
                if (user.getGoogleAccount() == null) {
                    user.setGoogleAccount(FirebaseUtils.getGoogleAccount());
                }
                FirebaseUtils.writeUserToDatabase(user);
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

    private void signIn() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getResources().getString(R.string.google_web_client_id))
                        .requestEmail().build();

        Intent signInIntent = GoogleSignIn.getClient(this, googleSignInOptions).getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "onActivityResult: Google sign in failed: " + e);
                Snackbar.make(activityGoogleSignInBinding.linearLayoutSignIn,
                        "Google sign in failed", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i(TAG, "onComplete: signInWithCredential successful");
                        if (FirebaseUtils.getAuthUser() != null) {
                            updateGoogleUserDataUI(FirebaseUtils.getAuthUser());
                            showSignInUI(false);
                        } else {
                            showSignInUI(true);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "onComplete: signInWithCredential: Failure: " +
                                task.getException(), task.getException());
                        Snackbar.make(activityGoogleSignInBinding.linearLayoutSignIn,
                                "Authentication failed", Snackbar.LENGTH_SHORT).show();
                        showSignInUI(true);
                    }
                });
    }
}
