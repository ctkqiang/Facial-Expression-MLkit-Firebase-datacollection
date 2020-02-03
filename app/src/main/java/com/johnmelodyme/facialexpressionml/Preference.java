package com.johnmelodyme.facialexpressionml;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Preference extends AppCompatActivity {
    private static final String TAG = Preference.class.getName();
    private Switch ON_OFF_SCREEN;
    private TextView EmailToDev;

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

        ON_OFF_SCREEN = findViewById(R.id.orientation);
        EmailToDev = findViewById(R.id.emailus);

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
    }
}
