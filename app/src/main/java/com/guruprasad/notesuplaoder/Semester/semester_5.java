package com.guruprasad.notesuplaoder.Semester;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.adapter.lab_manual_adapter;
import com.guruprasad.notesuplaoder.file_model;
import com.guruprasad.notesuplaoder.navigation;

import java.util.Objects;

public class semester_5 extends AppCompatActivity {
    RecyclerView recyclerView;
    lab_manual_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sem_5);
        Objects.requireNonNull(getSupportActionBar()).hide();


        recyclerView = findViewById(R.id.sem_5_rec);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance()
                .getReference("admin_lab_manual").child("semester 5"),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), navigation.class));
        finish();
    }
}