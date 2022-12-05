package com.guruprasad.notesuplaoder.ui.solved_lab_manual_upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guruprasad.notesuplaoder.databinding.FragmentSolvedLabmanualBinding;

public class solved_lab_manual_fragment extends Fragment {

    private FragmentSolvedLabmanualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSolvedLabmanualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}