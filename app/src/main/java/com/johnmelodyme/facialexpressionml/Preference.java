package com.johnmelodyme.facialexpressionml;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Preference extends AppCompatActivity {
    private static final String TAG = "ML";
    private Switch ON_OFF_SCREEN;
    private TextView EmailToDev;
    private Button LOGOUT;
    private android.app.AlertDialog ALERT_PROMPT;
    private FirebaseAuth FIREBASEAUTH;

    public static void contactHelpAndSupport(Context context, String[] to, String subject) {
        String body = "";
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "How can we help?\n\n\n\n\n\n\nPlease do not delete below contents\nDevice OS: Android(" +
                    Build.VERSION.RELEASE + ")\n App v" + body + "\n Device: " + Build.BRAND +
                    ", " + Build.MODEL;
        } catch (PackageManager.NameNotFoundException e) {
            //..
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, "Tell Developer What is up?"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        FirebaseUser USER;
        USER = FIREBASEAUTH.getCurrentUser();
        FIREBASEAUTH = FirebaseAuth.getInstance();
        ALERT_PROMPT = new SpotsDialog
                .Builder()
                .setContext(Preference.this)
                .setMessage("Logging Out...")
                .setCancelable(false)
                .build();

        ON_OFF_SCREEN = findViewById(R.id.orientation);
        EmailToDev = findViewById(R.id.emailus);
        LOGOUT = findViewById(R.id.logout_per);

        ON_OFF_SCREEN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        EmailToDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactHelpAndSupport(Preference.this,
                        new String[]{"Johnmelodyme@yandex.com",
                                "Johnmelodyme@icloud.com"},
                        "App help & support");
            }
        });

        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALERT_PROMPT.show();
                FIREBASEAUTH.signOut();
                FirebaseAuth.getInstance().signOut();
                System.exit(0x0);
            }
        });
    }
}
