package com.guruprasad.notesuplaoder.ui.Lab_manual_upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.adapter.lab_manual_adapter;
import com.guruprasad.notesuplaoder.databinding.FragmentLabManualBinding;
import com.guruprasad.notesuplaoder.file_model;

public class lab_manual_fragment extends Fragment {

    private FragmentLabManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLabManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lab_manual_adapter adapter ;

        binding.labManualRecview.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance().getReference("admin_lab_manual").child("semester_4"),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        binding.labManualRecview.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}