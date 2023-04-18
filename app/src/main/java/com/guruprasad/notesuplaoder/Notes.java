package com.guruprasad.notesuplaoder;

import static com.guruprasad.notesuplaoder.Constants.error_toast;
import static com.guruprasad.notesuplaoder.Constants.success_toast;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guruprasad.notesuplaoder.adapter.uploadAdpter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Notes extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Uri filepath ;
    Button browse ;
        ImageButton back ;
        TextView pagename ;
    StorageReference storageReference ;
    DatabaseReference databaseReference ;
    List<String> files , status ;
    RecyclerView recview ;
    uploadAdpter uploadAdpter ;

    Spinner semester , department ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin_pdf");
        files = new ArrayList<>();
        status = new ArrayList<>();
        browse= findViewById(R.id.browse);
        back = findViewById(R.id.back_button);
        pagename = findViewById(R.id.page_name);
        semester = findViewById(R.id.semester_spinner);
        department = findViewById(R.id.department_spinner);

        recview = findViewById(R.id.recview);
        recview .setLayoutManager(new LinearLayoutManager(this));

        uploadAdpter = new uploadAdpter(files,status);
        recview.setAdapter(uploadAdpter);
        pagename.setText("Upload Notes");
        pagename.setTextSize(15);
        pagename.setTextColor(Color.WHITE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigation.class));
                finish();
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(view.getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

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
                    filepath= data.getClipData().getItemAt(i).getUri();
                    String filename = get_file_name_from_uri(filepath);
                    files.add(filename);
                    status.add("loading");
                    uploadAdpter.notifyDataSetChanged();

                  final int index = i ;

                  String sem = semester.getSelectedItem().toString();
                  String dep = department.getSelectedItem().toString();


                    ProgressDialog pd = new ProgressDialog(Notes.this);
                    pd.setTitle("Lab Manual is Uploading");
                    pd.setMessage("PLease Wait ....");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();


                    StorageReference uploader = storageReference.child("/admin_pdf").child(filename);
                    uploader.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            filemodel filemodel = new filemodel(filename,uri.toString());
                                            databaseReference.child(dep).child(sem).child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(filemodel);
                                            status.remove(index);
                                            status.add(index,"done");
                                            uploadAdpter.notifyDataSetChanged();
                                            success_toast(getApplicationContext(),filename+" Uploaded Successfully");
                                            pd.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            error_toast(getApplicationContext(),"Error : "+e.getMessage());
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

    public String get_file_name_from_uri(Uri filepath)
    {
        String result = null;
        if (filepath.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = filepath.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}