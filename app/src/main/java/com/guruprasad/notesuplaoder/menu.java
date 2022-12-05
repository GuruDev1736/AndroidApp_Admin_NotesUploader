package com.guruprasad.notesuplaoder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class menu extends AppCompatActivity {
    RecyclerView recview ;
    myadapter adapter ;
    FloatingActionButton notes  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        notes = findViewById(R.id.notes);

        recview = findViewById(R.id.recview);

        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<filemodel> options = new FirebaseRecyclerOptions.Builder<filemodel>().setQuery(FirebaseDatabase.getInstance().getReference().child("pdf"),filemodel.class).build();

        adapter = new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Notes.class));
                finish();
            }
        });


    }
}