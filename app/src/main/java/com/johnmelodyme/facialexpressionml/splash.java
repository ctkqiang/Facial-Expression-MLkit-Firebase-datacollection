package com.johnmelodyme.facialexpressionml;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class splash extends AppCompatActivity {
    private TextView Loading;
    private Handler handler;
    private int DELAY_TIME = 0xbb8;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Loading = findViewById(R.id.loading);
        Loading.setText("ENTERING SYSTEM...");

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                Intent toRegister;
                toRegister = new Intent(splash.this, Register.class);
                startActivity(toRegister);
            }
        },DELAY_TIME);
    }
}
