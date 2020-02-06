package com.johnmelodyme.facialexpressionml;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.johnmelodyme.facialexpressionml.Model.ViewHolder;
import com.johnmelodyme.facialexpressionml.Model.model;

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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Database");

        recyclerView = findViewById(R.id.RV_DATA);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<model, ViewHolder>(
                        model.class,
                        R.layout.result_view,
                        ViewHolder.class,
                        databaseReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, model model, int i) {
                        viewHolder.setData(getApplicationContext(), model.getUSER_NAME(), model.getUSER_EMOTION(),
                                model.getACCURACY(), model.getDATE(), model.getUSER_IMAGE());
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
