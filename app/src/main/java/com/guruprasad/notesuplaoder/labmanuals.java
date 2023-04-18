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
import android.widget.Spinner;
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
import com.guruprasad.notesuplaoder.databinding.ActivityLabmanualsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class labmanuals extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Uri file_path;
    ActivityLabmanualsBinding binding ;
    FirebaseDatabase database ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ImageButton back ;

    List<String> files , status ;
    RecyclerView recview ;
   uploadAdpter uploadAdpter ;

   Spinner semester , department ;


    String name = "";



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLabmanualsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        files = new ArrayList<>();
        status = new ArrayList<>();
        recview = findViewById(R.id.recview);
        recview .setLayoutManager(new LinearLayoutManager(this));
        uploadAdpter = new uploadAdpter(files,status);
        recview.setAdapter(uploadAdpter);
        semester = findViewById(R.id.semester_spinner);
        department = findViewById(R.id.department_spinner);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference =FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin_lab_manual");

        TextView pagename = findViewById(R.id.page_name);
        pagename.setText("Upload Lab manual");
        pagename.setTextColor(Color.WHITE);
        pagename.setTextSize(15);

        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),navigation.class));
                finish();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semester_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        semester.setAdapter(adapter);
        semester.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dep = ArrayAdapter.createFromResource(this, R.array.department_type, android.R.layout.simple_spinner_item);
        dep.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        department.setAdapter(dep);
        department.setOnItemSelectedListener(this);






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
                           Toast.makeText(labmanuals.this, "Please give the permission to browse the files", Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                           permissionToken.continuePermissionRequest();
                       }
                   }).check();
       });






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

                    ProgressDialog pd = new ProgressDialog(labmanuals.this);
                    pd.setTitle("Lab Manual's are Uploading");
                    pd.setMessage("Please Wait ....");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();

                    String sem = semester.getSelectedItem().toString();
                    String dep = department.getSelectedItem().toString();


                    StorageReference reference = storageReference.child("admin_lab_manual/"+filename+".pdf");
                    reference.putFile(file_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    file_model filemodel = new file_model(filename, uri.toString(), auth.getCurrentUser().getUid());

                                    databaseReference.child(dep).child(sem).child(databaseReference.push().getKey()).setValue(filemodel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    status.remove(index);
                                                    status.add(index,"done");
                                                    uploadAdpter.notifyDataSetChanged();
                                                    pd.dismiss();
                                                    success_toast(getApplicationContext(),filename+" Uploaded Successfully");

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    error_toast(getApplicationContext(),"Failed to Add Manual : "+e.getMessage());
                                                    pd.dismiss();
                                                }
                                            });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(labmanuals.this, "Error : "+ e, Toast.LENGTH_LONG).show();
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