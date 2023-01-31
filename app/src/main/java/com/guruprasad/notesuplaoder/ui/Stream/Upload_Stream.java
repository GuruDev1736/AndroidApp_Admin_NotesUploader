package com.guruprasad.notesuplaoder.ui.Stream;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.databinding.FragmentUploadStreamBinding;
import com.guruprasad.notesuplaoder.model.upload_stream_model;


public class Upload_Stream extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentUploadStreamBinding binding ;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadStreamBinding.inflate(inflater, container, false);
        View root =binding.getRoot();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Stream");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.stream_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       binding.spinnerStream.setAdapter(adapter);
       binding.spinnerStream.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.stream_Type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStreamType.setAdapter(adapter1);
        binding.spinnerStreamType.setOnItemSelectedListener(this);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        binding.submitStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String category = binding.spinnerStream.getSelectedItem().toString();
                String type = binding.spinnerStreamType.getSelectedItem().toString();
                String name = binding.nameStream.getText().toString();
                String video_id = binding.videoStreamId.getText().toString();

                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setTitle("Uploading Stream");
                pd.setMessage("Please Wait");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);


                if (TextUtils.isEmpty(name))
                {
                    binding.nameStream.setError("Enter the name of the Stream ");
                    return;
                }
                if (TextUtils.isEmpty(video_id))
                {
                    binding.videoStreamId.setError("Enter the Video Id of the Stream");
                    return;
                }

                pd.show();

                upload_stream_model model = new upload_stream_model(name,video_id,category);


                    databaseReference.child(type).child(databaseReference.push().getKey()).setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(view.getContext(), "Stream Upload Successfully", Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                    binding.nameStream.setText("");
                                    binding.videoStreamId.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            });
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Select The Input", Toast.LENGTH_SHORT).show();
    }
}