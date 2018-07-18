package com.videumcorp.gitlab.classes.network;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.videumcorp.gitlab.activities.GoogleSignInActivity;
import com.videumcorp.gitlab.classes.InterfaceUtils;
import com.videumcorp.gitlab.classes.user.GoogleAccount;
import com.videumcorp.gitlab.classes.user.User;

import java.util.Objects;

/**
 * The type Firebase utils.
 */
public class FirebaseUtils {

    private static final String TAG = FirebaseUtils.class.getName();

    private static DatabaseReference databaseReferenceUID;

    /**
     * Instantiates a new Firebase utils.
     */
    private FirebaseUtils() {
    }

    private static FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    /**
     * Write {@link User} to database.
     *
     * @param user the user
     */
    public static void writeUserToDatabase(User user) {
        if (getAuthUser() != null) {
            databaseReferenceUID = getFirebaseDatabase().getReference(User.users_label)
                    .child(getAuthUser().getUid());
            databaseReferenceUID.setValue(user);
        } else {
            firebaseLaunchGoogleSignInActivity();
        }
    }


    /**
     * Read {@link User} from database.
     *
     * @param firebaseDatabaseGetUser the firebase database get user
     */
    public static void readUserFromDatabase(final FirebaseDatabaseGetUser firebaseDatabaseGetUser) {
        if (getAuthUser() != null) {
            databaseReferenceUID = getFirebaseDatabase().getReference(User.users_label)
                    .child(getAuthUser().getUid());
            databaseReferenceUID.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    firebaseDatabaseGetUser.onSuccess(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    firebaseDatabaseGetUser.onError(databaseError);
                }
            });
        } else {
            firebaseLaunchGoogleSignInActivity();
        }
    }

    /**
     * Gets {@link GoogleAccount}.
     *
     * @return the google account
     */
    public static GoogleAccount getGoogleAccount() {
        if (getAuthUser() != null) {
            return new GoogleAccount(getAuthUser().getDisplayName(), getAuthUser().getEmail(),
                    Objects.requireNonNull(getAuthUser().getPhotoUrl())
                            .toString());
        } else {
            firebaseLaunchGoogleSignInActivity();
            return null;
        }
    }

    /**
     * Gets {@link FirebaseAuth} {@link FirebaseUser}.
     *
     * @return the auth user
     */
    public static FirebaseUser getAuthUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser();
    }

    /**
     * The interface Firebase database get  {@link User}.
     */
    public interface FirebaseDatabaseGetUser {

        /**
         * On success.
         *
         * @param user the user
         */
        void onSuccess(User user);

        /**
         * On error.
         *
         * @param error the error
         */
        void onError(DatabaseError error);
    }


    private static void firebaseLaunchGoogleSignInActivity() {
        Context context = FirebaseDatabase.getInstance().getApp().getApplicationContext();
        InterfaceUtils.toastShowThemed(context, "Wops, you have to sign in with Google first!",
                Toast.LENGTH_LONG);
        context.startActivity(new Intent(context, GoogleSignInActivity.class));
    }
}
