package com.johnmelodyme.facialexpressionml;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getName();
    private Button REGISTER;
    private TextView EXISTING;
    private EditText EMAIL, PASSWORD;
    private FirebaseAuth FIREBASEAUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.w(TAG, "FE" + "onCreate: REGISTRATION");

        EXISTING = findViewById(R.id.existing);
        REGISTER = findViewById(R.id.register);
        EMAIL = findViewById(R.id.email);
        PASSWORD = findViewById(R.id.password);
        FIREBASEAUTH = FirebaseAuth.getInstance();

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
