package com.guruprasad.notesuplaoder;

import static com.guruprasad.notesuplaoder.Constants.error_toast;
import static com.guruprasad.notesuplaoder.Constants.success_toast;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guruprasad.notesuplaoder.adapter.uploadAdpter;
import com.guruprasad.notesuplaoder.databinding.ActivitySolvedLabManualBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class solved_lab_manual extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Uri file_path;
    ActivitySolvedLabManualBinding binding ;
    FirebaseDatabase database ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseAuth auth;
    DatabaseReference databaseReference;


    List<String> files , status ;
    RecyclerView recview ;
    uploadAdpter uploadAdpter ;
    TextView page_name ;


    String name = "";



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivitySolvedLabManualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();


        files = new ArrayList<>();
        status = new ArrayList<>();
        recview = findViewById(R.id.recview);
        recview .setLayoutManager(new LinearLayoutManager(this));
        uploadAdpter = new uploadAdpter(files,status);
        recview.setAdapter(uploadAdpter);
        page_name = findViewById(R.id.page_name);

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
        page_name.setTextSize(18);
        page_name.setTextColor(Color.WHITE);



        binding.browse.setOnClickListener(view -> {
            Dexter.withContext(view.getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent();
                            intent.setType("application/pdf");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
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
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semester_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        binding.semesterSpinner.setAdapter(adapter);
        binding.semesterSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dep = ArrayAdapter.createFromResource(this, R.array.department_type, android.R.layout.simple_spinner_item);
        dep.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        binding.departmentSpinner.setAdapter(dep);
        binding.departmentSpinner.setOnItemSelectedListener(this);






    }
    public String get_file_name_from_uri(Uri file_path)
    {
        String result = null;
        if (file_path.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(file_path, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = file_path.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
            if (data.getClipData()!=null)
            {
                for (int i = 0 ; i<data.getClipData().getItemCount();i++)
                {
                    file_path = data.getClipData().getItemAt(i).getUri();
                    String filename = get_file_name_from_uri(file_path);
                    files.add(filename);
                    status.add("loading");
                    uploadAdpter.notifyDataSetChanged();

                    final int index = i ;

                    String sem = binding.semesterSpinner.getSelectedItem().toString();
                    String dep = binding.departmentSpinner.getSelectedItem().toString();


                    ProgressDialog pd = new ProgressDialog(solved_lab_manual.this);
                    pd.setTitle("Solved Lab Manual is Uploading");
                    pd.setMessage("PLease Wait ....");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();

                    StorageReference reference = storageReference.child("admin_solved_lab_manual/"+filename+".pdf");
                    reference.putFile(file_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    file_model filemodel = new file_model(filename, uri.toString(), auth.getCurrentUser().getUid());
                                    databaseReference.child(dep).child(sem).child(databaseReference.push().getKey()).setValue(filemodel);

                                            status.remove(index);
                                            status.add(index,"done");
                                            uploadAdpter.notifyDataSetChanged();
                                            success_toast(getApplicationContext(),filename+" Uploaded Successfully");
                                            pd.dismiss();
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
                            float totalBytes = snapshot.getTotalByteCount();
                            float per = 0.0f;
                            if (totalBytes != 0) {
                                per = 100 * snapshot.getBytesTransferred() / totalBytes;
                            }
                            pd.setMessage("File uploaded : " + (int)per + "%");
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}