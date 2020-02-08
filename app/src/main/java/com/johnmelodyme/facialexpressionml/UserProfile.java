package com.johnmelodyme.facialexpressionml;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.muddzdev.styleabletoast.StyleableToast;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class UserProfile extends AppCompatActivity {
    private static final String TAG = "ML";
    private CircleImageView ProfilePicture;
    private String DISPLAY_NAME = null;
    private String PROFILE_IMAGE_URL = null;
    private int TAKE_IMAGE_CODE = 0x2711;
    private TextView PROFILE_NAME;
    private Button LOGOUT;
    private FirebaseUser FIREBASE_USER;
    private FirebaseAuth firebaseAuth;
    private android.app.AlertDialog SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Log.d(TAG, "UserProfile:clicked:");
        FIREBASE_USER = FirebaseAuth.getInstance().getCurrentUser();
        ProfilePicture = findViewById(R.id.Profile_Picture);
        PROFILE_NAME = findViewById(R.id.userProfile);
        LOGOUT = findViewById(R.id.profile_logout);

        SignOut = new SpotsDialog
                .Builder()
                .setContext(UserProfile.this)
                .setMessage("Logging Out...")
                .setCancelable(false)
                .build();

        if (FIREBASE_USER != null){
            Log.d(TAG, "onCreate: " + FIREBASE_USER.getDisplayName());
            if (FIREBASE_USER.getDisplayName() != null){
                String USER;
                USER = FIREBASE_USER.getDisplayName();
                PROFILE_NAME.setText(USER);
            } else {
                PROFILE_NAME.setText(FIREBASE_USER.getEmail());
            }

            if (FIREBASE_USER.getPhotoUrl() != null){
                Glide.with(this)
                        .load(FIREBASE_USER.getPhotoUrl())
                        .into(ProfilePicture);
            }
        } else {
            PROFILE_NAME.setText(FIREBASE_USER.getEmail());
            Log.d(TAG, "onCreate: " + FIREBASE_USER.getEmail());
        }

        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                DISPLAY_NAME = PROFILE_NAME.getText().toString();
                FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(DISPLAY_NAME)
                        .build();

                USER.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new StyleableToast
                                .Builder(UserProfile.this)
                                .text("Successfully updated profile")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.rgb(255,20,147))
                                .show();
                        Log.d(TAG, "onSuccess: Successfully updated profile");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new StyleableToast
                                .Builder(UserProfile.this)
                                .text("Failed Updating Profile")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.rgb(255,20,147))
                                .show();
                        Log.d(TAG, "onFailure: Failed Updating Profile");
                    }
                });
            }
        });

        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                SignOut.show();
                auth.signOut();
                finish();
                System.exit(1);
                Intent toLogin;
                toLogin = new Intent(UserProfile.this, Login.class);
                startActivity(toLogin);
                auth.signOut();
            }
        });
    }
}
