package com.guruprasad.notesuplaoder.ui.Library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.adapter.library_adapter;
import com.guruprasad.notesuplaoder.databinding.FragmentPithosLibraryBinding;
import com.guruprasad.notesuplaoder.file_model;

public class pithos_library extends Fragment implements AdapterView.OnItemSelectedListener {

  private FragmentPithosLibraryBinding binding ;
     library_adapter adapter ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPithosLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.libLang.setAdapter(adapter);
        binding.libLang.setOnItemSelectedListener(this);

        return root ;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       binding.submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String name =binding.libLang.getSelectedItem().toString();

               if (position==0 || position==1 || position==2 || position==3 || position==4 || position==5
                       || position==6 || position==7 || position==8 || position==9 || position==10 || position==11
                       || position==12 || position==13 || position==14 || position==15 || position==16 || position==17
                       || position==18 || position==19 || position==20 || position==21 || position==22 || position==23
                       || position==24 || position==25 || position==26 || position==27 || position==28 || position==29
                       || position==30 || position==31 || position==32 || position==33 || position==34 || position==35
                       || position==36)
               {
                   binding.libShowRec.setLayoutManager(new LinearLayoutManager(getContext()));
                   FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance().getReference("Library").child(name),file_model.class).build();
                   adapter = new library_adapter(options);
                   adapter.startListening();
                   binding.libShowRec.setAdapter(adapter);

               }
           }
       });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Please Select valid Input ", Toast.LENGTH_SHORT).show();
    }
}