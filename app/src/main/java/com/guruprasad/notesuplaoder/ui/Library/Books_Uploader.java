package com.guruprasad.notesuplaoder.ui.Library;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.adapter.uploadAdpter;
import com.guruprasad.notesuplaoder.file_model;
import com.guruprasad.notesuplaoder.navigation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Books_Uploader extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton back;
    TextView page_name;
    Spinner spinner;
    Uri filepath;
    FirebaseAuth auth;

    List<String> files, status;
    RecyclerView recview;
    com.guruprasad.notesuplaoder.adapter.uploadAdpter uploadAdpter;

    String name = "";

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayList<Uri> FileList = new ArrayList<Uri>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_uploader);
        Objects.requireNonNull(getSupportActionBar()).hide();

        back = findViewById(R.id.back_button);
        page_name = findViewById(R.id.page_name);
        spinner = findViewById(R.id.lib_lang);

        files = new ArrayList<>();
        status = new ArrayList<>();
        recview = findViewById(R.id.recview_lib);
        recview.setLayoutManager(new LinearLayoutManager(this));
        uploadAdpter = new uploadAdpter(files, status);
        recview.setAdapter(uploadAdpter);
        auth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Library");


        page_name.setText("Upload Library Books");
        page_name.setTextSize(18);
        page_name.setTextColor(Color.WHITE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), navigation.class));
                finish();
            }
        });


        Button browse = findViewById(R.id.browse_lib);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(Books_Uploader.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(Books_Uploader.this, "Please give the permission to browse the files", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    public String get_file_name_from_uri(Uri file_path) {
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
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {

                int count = data.getClipData().getItemCount();

                int i = 0;

                while (i < count) {
                    Uri File = data.getClipData().getItemAt(i).getUri();
                    FileList.add(File);
                    i++;
                }
                Toast.makeText(this, "You Have Selected " + FileList.size() +" Files ", Toast.LENGTH_LONG).show();





            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Button upload = findViewById(R.id.upload_lib);
        ProgressDialog pd = new ProgressDialog(Books_Uploader.this);
        pd.setTitle("Book's are Uploading");
        pd.setMessage("Please Wait ....");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);


        String name = spinner.getSelectedItem().toString();
        Toast.makeText(Books_Uploader.this, "Selected :"+name , Toast.LENGTH_SHORT).show();


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FileList.size()==0)
                {
                    Toast.makeText(Books_Uploader.this, "Select the Files To Upload ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (position==0 || position==1 || position==2 || position==3 || position==4 || position==5
                        || position==6 || position==7 || position==8 || position==9 || position==10 || position==11
                        || position==12 || position==13 || position==14 || position==15 || position==16 || position==17
                        || position==18 || position==19 || position==20 || position==21 || position==22 || position==23
                        || position==24 || position==25 || position==26 || position==27 || position==28 || position==29
                        || position==30 || position==31 || position==32 || position==33 || position==34 || position==35
                        || position==36)
                {
                    pd.show();
                    for (int j = 0; j < FileList.size(); j++) {
                        Uri PerFile = FileList.get(j);
                        String filename = get_file_name_from_uri(PerFile);
                        files.add(filename);
                        status.add("loading");
                        uploadAdpter.notifyDataSetChanged();

                        final int index = j;

                        StorageReference reference = storageReference.child("Library").child(name).child(filename + ".pdf");
                        reference.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        file_model filemodel = new file_model(filename, uri.toString(), auth.getCurrentUser().getUid());

                                        databaseReference.child(name).child(databaseReference.push().getKey()).setValue(filemodel);
                                        status.remove(index);
                                        status.add(index, "done");
                                        uploadAdpter.notifyDataSetChanged();
                                        pd.dismiss();
                                        Toast.makeText(Books_Uploader.this, "Book Uploaded : " + filename, Toast.LENGTH_SHORT).show();
                                        FileList.clear();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Books_Uploader.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        FileList.clear();
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
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Enter the Valid Input ", Toast.LENGTH_SHORT).show();
    }
}
