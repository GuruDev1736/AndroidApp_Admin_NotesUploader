package com.guruprasad.notesuplaoder.ui.solved_lab_manual_upload;

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
import com.guruprasad.notesuplaoder.databinding.FragmentSolvedLabmanualBinding;
import com.guruprasad.notesuplaoder.file_model;

public class solved_lab_manual_fragment extends Fragment {

    lab_manual_adapter adapter;
    private FragmentSolvedLabmanualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSolvedLabmanualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.solvedLabManualRec.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance().getReference("admin_solved_lab_manual"),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        binding.solvedLabManualRec.setAdapter(adapter);

        return root;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        inflater.inflate(R.menu.search, menu);
//        MenuItem menuItem = menu.findItem(R.id.search);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Type Here To Search");
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                process_search(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                process_search(newText);
//                return false;
//            }
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//    private void process_search(String query) {
//        FirebaseRecyclerOptions<file_model> options =
//                new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance()
//                        .getReference("admin_solved_lab__manual").orderByChild("file_title")
//                        .startAt(query).endAt(query+"\uf8ff"),file_model.class).build();
//
//        adapter = new lab_manual_adapter(options);
//        adapter.startListening();
//        binding.solvedLabManualRec.setAdapter(adapter);
//
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}