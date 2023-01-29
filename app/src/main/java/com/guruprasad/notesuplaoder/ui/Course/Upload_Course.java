package com.guruprasad.notesuplaoder.ui.Course;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import com.guruprasad.notesuplaoder.databinding.FragmentUploadCourseBinding;
import com.guruprasad.notesuplaoder.model.upload_course_model;


public class Upload_Course extends Fragment implements AdapterView.OnItemSelectedListener {
        FragmentUploadCourseBinding binding ;

        FirebaseDatabase database;
        DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.radioSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.courseVideos.isChecked())
                {
                    binding.courseFrame.setVisibility(View.VISIBLE);
                    binding.courseFrame.setAnimation(AnimationUtils.loadAnimation(v.getContext(),R.anim.recycler_view_anime_2));
                }
                if (binding.courseNotes.isChecked())
                {
                    Intent intent = new Intent(getContext(),course_notes_upload.class);
                    startActivity(intent);
                    binding.courseFrame.setVisibility(View.INVISIBLE);

                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courseSpinner.setAdapter(adapter);
        binding.courseSpinner.setOnItemSelectedListener(this);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Course");


        return root ;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        binding.submitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog pd = new ProgressDialog(v.getContext());
                pd.setTitle("Uploading");
                pd.setMessage("Please Wait");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);

                String name = binding.courseName.getText().toString();
                String author = binding.authorName.getText().toString();
                String video_id = binding.videoId.getText().toString();
                String subject = binding.courseSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(name))
                {
                    binding.courseName.setError("Enter the Course Name");
                    return;
                }
                if (TextUtils.isEmpty(video_id))
                {
                    binding.videoId.setError("Enter the Video Id ");
                }

                pd.show();

                upload_course_model upload_course_model = new upload_course_model(name,author,video_id,subject);

                databaseReference.child(subject).child("Videos").child(databaseReference.push().getKey()).setValue(upload_course_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(v.getContext(), "Course Has Been Uploaded", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this.getContext() , "Please Select the Valid input ", Toast.LENGTH_SHORT).show();
    }
}