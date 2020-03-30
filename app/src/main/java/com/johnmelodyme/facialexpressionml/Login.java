package com.johnmelodyme.facialexpressionml;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muddzdev.styleabletoast.StyleableToast;

import dmax.dialog.SpotsDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Login extends AppCompatActivity {
    private static final String TAG = "ML";
    private Button LOGIN;
    private EditText EMAIL, PASSWORD;
    private TextView FORGOTPASSWORD;
    private FirebaseAuth FIREBASEAUTH;
    private LayoutInflater LAYOUTINFLATER;
    private View VIEW;
    private AlertDialog.Builder PROMPT;
    private android.app.AlertDialog ALERT_RESET, ALERT_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: LOGIN");

        LOGIN = findViewById(R.id.login_button);
        EMAIL = findViewById(R.id.email_login);
        PASSWORD = findViewById(R.id.password_login);
        FORGOTPASSWORD = findViewById(R.id.forgotpass_loginscreen);
        FIREBASEAUTH = FirebaseAuth.getInstance();
        LAYOUTINFLATER = LayoutInflater.from(Login.this);
        VIEW = LAYOUTINFLATER.inflate(R.layout.forgotpassword, null);
        PROMPT = new AlertDialog.Builder(Login.this);

        ALERT_LOGIN  = new SpotsDialog
                .Builder()
                .setContext(Login.this)
                .setMessage("Logging in...")
                .setCancelable(false)
                .build();

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, Password, err_email, err_password, err_pass_strong;
                Email = EMAIL.getText()
                        .toString()
                        .trim();
                Password = PASSWORD.getText()
                        .toString()
                        .trim();
                err_email = getResources().getString(R.string.a);
                err_password = getResources().getString(R.string.b);
                err_pass_strong = getResources().getString(R.string.c);

                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
                    EMAIL.setError(err_email);
                    PASSWORD.setError(err_password);
                    if (Password.length() < 6){
                        PASSWORD.setError(err_pass_strong);
                        if (Password.equals("admin")){
                            //ROTIBAKAR("GOOD TRY ON THE HACKING, YOU FAILED :) ");
                            new StyleableToast
                                    .Builder(Login.this)
                                    .text("GOOD TRY ON THE HACKING, YOU FAILED :) ")
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.rgb(255,20,147))
                                    .show();
                        }
                    }
                } else {
                    FIREBASEAUTH.signInWithEmailAndPassword(Email, Password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                ALERT_LOGIN.show();
//                                KProgressHUD hud = KProgressHUD.create(Login.this)
//                                        .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
//                                        .setLabel("Please wait")
//                                        .setMaxProgress(100)
//                                        .show();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                //ROTIBAKAR("Welcome back " + user);
                                new StyleableToast
                                        .Builder(Login.this)
                                        .text("Welcome back " + user.getEmail())
                                        .textColor(Color.WHITE)
                                        .backgroundColor(Color.rgb(255,20,147))
                                        .show();
                                Intent toMain;
                                toMain = new Intent(Login.this, FaceExpressionActivity.class);
                                startActivity(toMain);
                                Log.d(TAG, "onComplete: USER LOGIN SUCCEED");
                            } else {
                                //ROTIBAKAR("Problem Login :(");
                                new StyleableToast
                                        .Builder(Login.this)
                                        .text("Problem Login :(")
                                        .textColor(Color.WHITE)
                                        .backgroundColor(Color.rgb(255,20,147))
                                        .show();
                            }
                        }
                    });
                }
            }
        });

        FORGOTPASSWORD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText EMAIL_res;
                final String Reset_Email, Rem, yes, err;
                EMAIL_res = VIEW.findViewById(R.id.reset_pass_email);
                Rem = getResources().getString(R.string.itsok);
                yes = getResources().getString(R.string.rpass);
                err = getResources().getString(R.string.pls);
                Reset_Email = EMAIL_res.getText()
                        .toString()
                        .trim();
                ALERT_RESET = new SpotsDialog
                        .Builder()
                        .setContext(Login.this)
                        .setMessage("Reset Email sent to  " + Reset_Email)
                        .setCancelable(false)
                        .build();
                PROMPT.setView(VIEW);
                PROMPT.setNegativeButton(Rem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogue, int ID) {
                        Log.d(TAG,  "User Suddenly, magically remembered his/her password");
                    }
                }).setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogue, int ID) {
                        if (TextUtils.isEmpty(Reset_Email)){
                            EMAIL_res.setError(err);
                        } else {
                            FIREBASEAUTH.sendPasswordResetEmail(Reset_Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        ALERT_RESET.show();
                                        //ROTIBAKAR("Password Reset Email Sent To " + Reset_Email);
                                        new StyleableToast
                                                .Builder(Login.this)
                                                .text("Password Reset Email Sent To " + Reset_Email)
                                                .textColor(Color.WHITE)
                                                .backgroundColor(Color.rgb(255,20,147))
                                                .show();
                                    } else {
                                        //ROTIBAKAR("FAILED");
                                        new StyleableToast
                                                .Builder(Login.this)
                                                .text("Failed :( ")
                                                .textColor(Color.WHITE)
                                                .backgroundColor(Color.rgb(255,20,147))
                                                .show();
                                    }
                                }
                            });
                        }
                    }
                });
                AlertDialog alertDialog = PROMPT.create();
                alertDialog.show();
            }
        });
    }

    public void ROTIBAKAR(String string){
        Toast.makeText(Login.this, string,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
