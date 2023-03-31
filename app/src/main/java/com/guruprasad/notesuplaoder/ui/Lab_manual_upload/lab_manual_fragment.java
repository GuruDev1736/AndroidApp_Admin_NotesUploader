package com.guruprasad.notesuplaoder.ui.Lab_manual_upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.databinding.FragmentLabManualBinding;

public class lab_manual_fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentLabManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLabManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.semester_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        binding.spSem.setAdapter(adapter);
        binding.spSem.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dep = ArrayAdapter.createFromResource(getContext(), R.array.department_type, android.R.layout.simple_spinner_item);
        dep.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        binding.spDep.setAdapter(dep);
        binding.spDep.setOnItemSelectedListener(this);

        String semester = binding.spSem.getSelectedItem().toString();
        String department = binding.spDep.getSelectedItem().toString();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),ShowLabManualActivity.class);
                intent.putExtra("sem",semester);
                intent.putExtra("dep",department);
                startActivity(intent);

            }
        });







        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}