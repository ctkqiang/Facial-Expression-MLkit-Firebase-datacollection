package com.johnmelodyme.facialexpressionml;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Login extends AppCompatActivity {
    private static final String TAG = Login.class.getName();
    private Button LOGIN;
    private EditText EMAIL, PASSWORD;
    private TextView FORGOTPASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
