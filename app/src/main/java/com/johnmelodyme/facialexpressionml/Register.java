package com.johnmelodyme.facialexpressionml;

import android.content.DialogInterface;
import android.content.Intent;
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

import dmax.dialog.SpotsDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getName();
    private Button REGISTER;
    private TextView EXISTING, FORGOT_PASS;
    private EditText EMAIL, PASSWORD;
    private FirebaseAuth FIREBASEAUTH;
    private LayoutInflater LAYOUTINFLATER;
    private View VIEW;
    private AlertDialog.Builder PROMPT;
    private android.app.AlertDialog ALERT_PROMPT, ALERT_RESET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.w(TAG, "FE" + "onCreate: REGISTRATION");

        EXISTING = findViewById(R.id.existing);
        REGISTER = findViewById(R.id.register);
        EMAIL = findViewById(R.id.email);
        PASSWORD = findViewById(R.id.password);
        FORGOT_PASS = findViewById(R.id.forgotpass);
        FIREBASEAUTH = FirebaseAuth.getInstance();
        LAYOUTINFLATER = LayoutInflater.from(Register.this);
        VIEW = LAYOUTINFLATER.inflate(R.layout.forgotpassword, null);
        PROMPT = new AlertDialog.Builder(Register.this);

        ALERT_PROMPT = new SpotsDialog
                .Builder()
                .setContext(Register.this)
                .setMessage("Registering.....")
                .setCancelable(false)
                .build();

        EXISTING.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TOLOGINPAGE;
                TOLOGINPAGE = new Intent(Register.this, Login.class);
                startActivity(TOLOGINPAGE);
            }
        });

        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, Password, err_email, err_pass, err_strong_pass;
                Email = EMAIL.getText()
                        .toString()
                        .trim();
                Password = PASSWORD.getText()
                        .toString()
                        .trim();
                err_email = "Please Enter Your Email Address";
                err_pass = "Please Enter Your Password";
                err_strong_pass = "Password Must Not Be < 6 Letters";

                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
                    EMAIL.setError(err_email);
                    PASSWORD.setError(err_pass);
                    if(Password.length() < 6){
                        PASSWORD.setError(err_strong_pass);
                    }
                } else {
                    FIREBASEAUTH.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                ALERT_PROMPT.show();
                                Intent TOMAIN;
                                TOMAIN = new Intent(Register.this, MainActivity.class);
                                startActivity(TOMAIN);
                                ROTIBAKAR(String.valueOf(FIREBASEAUTH.getCurrentUser()));
                                Log.w(TAG, "FE" + "onComplete: FIREBASE REGISTRATION =======> {ok} ");
                            } else {
                                ROTIBAKAR("Registration Failed :( ");
                                Log.w(TAG, "FE" + "onComplete: FIREBASE REGISTRATION =======> {failed} ");
                                // TODO : GUEST VERSION;
                            }
                        }
                    });
                }
            }
        });

        FORGOT_PASS.setOnClickListener(new View.OnClickListener() {
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
                        .setContext(Register.this)
                        .setMessage("Reset Email sent to  " + Reset_Email)
                        .setCancelable(false)
                        .build();
                PROMPT.setView(VIEW);
                PROMPT.setNegativeButton(Rem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogue, int ID) {
                        Log.w(TAG, "FE " + "User Suddenly, magically remembered his/her password");
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
                                        ROTIBAKAR("Password Reset Email Sent To " + Reset_Email);
                                    } else {
                                        ROTIBAKAR("FAILED");
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
        Toast.makeText(Register.this, string,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}