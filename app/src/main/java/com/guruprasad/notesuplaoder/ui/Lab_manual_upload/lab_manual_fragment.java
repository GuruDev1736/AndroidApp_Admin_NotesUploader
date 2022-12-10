package com.guruprasad.notesuplaoder.ui.Lab_manual_upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guruprasad.notesuplaoder.Department_adapter.E_and_TC_FY;
import com.guruprasad.notesuplaoder.Department_adapter.E_and_TC_SY;
import com.guruprasad.notesuplaoder.Department_adapter.E_and_TC_TY;
import com.guruprasad.notesuplaoder.Department_adapter.IT_FY;
import com.guruprasad.notesuplaoder.Department_adapter.IT_SY;
import com.guruprasad.notesuplaoder.Department_adapter.IT_TY;
import com.guruprasad.notesuplaoder.Department_adapter.civil_FY;
import com.guruprasad.notesuplaoder.Department_adapter.civil_SY;
import com.guruprasad.notesuplaoder.Department_adapter.civil_TY;
import com.guruprasad.notesuplaoder.Department_adapter.comp_FY;
import com.guruprasad.notesuplaoder.Department_adapter.comp_SY;
import com.guruprasad.notesuplaoder.Department_adapter.comp_TY;
import com.guruprasad.notesuplaoder.Department_adapter.mech_FY;
import com.guruprasad.notesuplaoder.Department_adapter.mech_SY;
import com.guruprasad.notesuplaoder.Department_adapter.mech_TY;
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

                if (binding.computerRadio.isChecked() && binding.firstYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), comp_FY.class));

                }
                else if (binding.computerRadio.isChecked() && binding.secondYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), comp_SY.class));

                }
                else if (binding.computerRadio.isChecked() && binding.thirdYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), comp_TY.class));
                }

                // mechanical

                else if (binding.mechanicalRadio.isChecked() && binding.firstYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), mech_FY.class));
                }
                else if (binding.mechanicalRadio.isChecked() && binding.secondYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), mech_SY.class));

                }
                else if (binding.mechanicalRadio.isChecked() && binding.thirdYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), mech_TY.class));
                }

                // civil

                else if (binding.civilRadio.isChecked() && binding.firstYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), civil_FY.class));
                }
                else if (binding.civilRadio.isChecked() && binding.secondYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), civil_SY.class));

                }
                else if (binding.civilRadio.isChecked() && binding.thirdYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), civil_TY.class));
                }

                //E and TC

                else if (binding.eAndTcRadio.isChecked() && binding.firstYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), E_and_TC_FY.class));
                }
                else if (binding.eAndTcRadio.isChecked() && binding.secondYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), E_and_TC_SY.class));

                }
                else if (binding.eAndTcRadio.isChecked() && binding.thirdYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), E_and_TC_TY.class));
                }

                // IT

                else if (binding.ITRadio.isChecked() && binding.firstYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), IT_FY.class));
                }
                else if (binding.ITRadio.isChecked() && binding.secondYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), IT_SY.class));

                }
                else if (binding.ITRadio.isChecked() && binding.thirdYear.isChecked())
                {
                    startActivity(new Intent(view.getContext(), IT_TY.class));
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