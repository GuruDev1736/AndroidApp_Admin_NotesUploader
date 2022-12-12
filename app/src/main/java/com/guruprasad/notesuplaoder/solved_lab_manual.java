package com.guruprasad.notesuplaoder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guruprasad.notesuplaoder.databinding.ActivitySolvedLabManualBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

public class solved_lab_manual extends AppCompatActivity {
    Uri file_path;
    ActivitySolvedLabManualBinding binding ;
    FirebaseDatabase database ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    RadioButton radioButton ;

    String[] sem_name = {"Select","Sem-1","Sem-2","Sem-3","Sem-4","Sem-5","Sem-6"};



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivitySolvedLabManualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference =FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin_solved_lab_manual");

        TextView pagename = findViewById(R.id.page_name);
        pagename.setText("Upload Solved Lab manual");
        pagename.setTextSize(15);

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigation.class));
                finish();
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cancel.setVisibility(View.INVISIBLE);
                binding.img1.setVisibility(View.INVISIBLE);
                binding.upload.setVisibility(View.INVISIBLE);
            }
        });

        binding.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(view.getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select PDF File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(solved_lab_manual.this, "Please give the permission to browse the files", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name_of_manual = String.valueOf(binding.title.getText());

                if (TextUtils.isEmpty(name_of_manual))
                {
                    binding.title.setError("Enter the name of the lab manual");
                    Toast.makeText(solved_lab_manual.this, "Enter the name of the lab manual", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    ProgressDialog pd = new ProgressDialog(view.getContext());
                    pd.setTitle("Solved Lab Manual is Uploading");
                    pd.setMessage("PLease Wait ....");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    StorageReference reference = storageReference.child("admin_solved_lab_manual/"+System.currentTimeMillis()+".pdf");
                    reference.putFile(file_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    file_model filemodel = new file_model(binding.title.getText().toString(), uri.toString(), auth.getCurrentUser().getUid());

                                    if (binding.sem1.isChecked()) {
                                        databaseReference.child("semester_1").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();
                                        return;

                                    }
                                    if (binding.sem2.isChecked()) {
                                        databaseReference.child("semester_2").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                                    if (binding.sem3.isChecked()) {
                                        databaseReference.child("semester_3").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                                    if (binding.sem4.isChecked()) {
                                        databaseReference.child("semester_4").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                                    if (binding.sem5.isChecked()) {
                                        databaseReference.child("semester_5").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();

                                        return;
                                    }
                                    if (binding.sem6.isChecked()) {
                                        databaseReference.child("semester_6").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(solved_lab_manual.this, "Solved manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(solved_lab_manual.this, "Error : "+ e, Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float per = 100*snapshot.getBytesTransferred() /snapshot.getTotalByteCount();
                            pd.setMessage("File uploaded : "+(int)per+"%");
                        }
                    });


                }


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
            file_path = data.getData();
            binding.img1.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
            binding.upload.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),navigation.class));
        finish();
    }


}