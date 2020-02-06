package com.johnmelodyme.facialexpressionml;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Data_list extends AppCompatActivity {
    private static final String TAG = Data_list.class.getName();
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        // ACTIONBAR :
        //ActionBar
    }
}
