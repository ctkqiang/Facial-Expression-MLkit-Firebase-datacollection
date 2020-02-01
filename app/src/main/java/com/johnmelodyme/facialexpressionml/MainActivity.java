package com.johnmelodyme.facialexpressionml;
/*
 *  I Actually hate creating this app but My baby
 *  encourage me and say that I can ,
 *  SO I do So.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private FirebaseAuth FIREBASEAUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "FE" + "onCreate: Starting Application.");
        FIREBASEAUTH = FirebaseAuth.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.signout){
            FirebaseAuth.AuthStateListener AUTH;
            FIREBASEAUTH.signOut();
            AUTH = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FIREBASEAUTH.getCurrentUser();
                    if(user == null){
                        Intent backtologin;
                        backtologin = new Intent(MainActivity.this, Login.class);
                        startActivity(backtologin);
                        finish();
                    }
                }
            };
        }
        return super.onOptionsItemSelected(item);
    }
}
