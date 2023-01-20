package com.guruprasad.notesuplaoder.ui.Requested_Data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.adapter.Request_adapter;
import com.guruprasad.notesuplaoder.databinding.FragmentRequestedBooksBinding;
import com.guruprasad.notesuplaoder.model.Request_model;


public class Requested_Books extends Fragment {
    private FragmentRequestedBooksBinding  binding ;
    Request_adapter adapter ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRequestedBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.requestData.setLayoutManager(new LinearLayoutManager(root.getContext()));
        FirebaseRecyclerOptions<Request_model> options =
                new FirebaseRecyclerOptions.Builder<Request_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Requested_Data"),Request_model.class).build();

        adapter = new Request_adapter(options);
        adapter.startListening();
        binding.requestData.setAdapter(adapter);

        return root ;
    }
}