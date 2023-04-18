package com.guruprasad.notesuplaoder.ui.solved_lab_manual_upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.adapter.lab_manual_adapter;
import com.guruprasad.notesuplaoder.file_model;

import java.util.Objects;

public class ShowSolvedLabManual extends AppCompatActivity {
    RecyclerView recyclerView;
    lab_manual_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_solved_lab_manual);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Solved Lab Manuals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.rvShowManual);
        Intent intent = getIntent();
        String sem = intent.getStringExtra("sem");
        String dep = intent.getStringExtra("dep");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance().getReference("admin_solved_lab_manual").child(dep).child(sem),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search , menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type Here To Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                process_search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                process_search(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void process_search(String query) {

        Intent intent = getIntent();
        String sem = intent.getStringExtra("sem");
        String dep = intent.getStringExtra("dep");

        FirebaseRecyclerOptions<file_model> options =
                new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance()
                        .getReference("admin_solved_lab_manual").child(dep).child(sem).orderByChild("file_title")
                        .startAt(query).endAt(query+"\uf8ff"), file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}