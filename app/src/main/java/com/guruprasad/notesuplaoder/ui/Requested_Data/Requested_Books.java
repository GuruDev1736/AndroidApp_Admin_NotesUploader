package com.guruprasad.notesuplaoder.ui.Requested_Data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guruprasad.notesuplaoder.databinding.FragmentRequestedBooksBinding;


public class Requested_Books extends Fragment {
    private FragmentRequestedBooksBinding  binding ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRequestedBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root ;
    }





}