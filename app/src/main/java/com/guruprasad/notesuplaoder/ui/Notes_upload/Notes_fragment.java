package com.guruprasad.notesuplaoder.ui.Notes_upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.databinding.FragmentNotesBinding;
import com.guruprasad.notesuplaoder.filemodel;
import com.guruprasad.notesuplaoder.myadapter;

public class Notes_fragment extends Fragment {

    private FragmentNotesBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        binding = FragmentNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        myadapter adapter;

       binding.recview.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<filemodel> options = new FirebaseRecyclerOptions.Builder<filemodel>().setQuery(FirebaseDatabase.getInstance().getReference().child("pdf"),filemodel.class).build();


       adapter = new myadapter(options);
        adapter.startListening();
       binding.recview.setAdapter(adapter);


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}