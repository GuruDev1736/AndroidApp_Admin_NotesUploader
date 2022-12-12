package com.guruprasad.notesuplaoder.ui.Lab_manual_upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guruprasad.notesuplaoder.Semester.semester_1;
import com.guruprasad.notesuplaoder.Semester.semester_2;
import com.guruprasad.notesuplaoder.Semester.semester_3;
import com.guruprasad.notesuplaoder.Semester.semester_4;
import com.guruprasad.notesuplaoder.Semester.semester_5;
import com.guruprasad.notesuplaoder.Semester.semester_6;
import com.guruprasad.notesuplaoder.databinding.FragmentLabManualBinding;

public class lab_manual_fragment extends Fragment {

    private FragmentLabManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLabManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();





        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.sem1Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_1.class));

                }



                else if (binding.sem2Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_2.class));
                }


                else if (binding.sem3Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_3.class));
                }


                else if (binding.sem4Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_4.class));
                }




                else if (binding.sem5Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_5.class));
                } 
                else if (binding.sem6Radio.isChecked() )
                {
                    startActivity(new Intent(view.getContext(), semester_6.class));
                }



            }
        });







        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}