package com.guruprasad.notesuplaoder.ui.Activities;

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
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

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
import com.guruprasad.notesuplaoder.databinding.ActivityAddMusicBinding;
import com.guruprasad.notesuplaoder.labmanuals;
import com.guruprasad.notesuplaoder.model.MusicModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

public class AddMusic extends AppCompatActivity {

    Uri filepath ;
    FirebaseDatabase database ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ActivityAddMusicBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference =FirebaseStorage.getInstance().getReference("Pomodoro Music");
        databaseReference = FirebaseDatabase.getInstance().getReference("Pomodoro Music");

        binding.ibtnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(view.getContext())
                        .withPermissions(Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO
                        ,Manifest.permission.READ_MEDIA_IMAGES)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                Intent intent = new Intent();
                                intent.setType("audio/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select MP3 File"),101);

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Mname  = binding.etMusicName.getText().toString();

                if (TextUtils.isEmpty(Mname))
                {
                    binding.etMusicName.setError("Name is Required ");
                    return;
                }

                ProgressDialog pd = new ProgressDialog(AddMusic.this);
                pd.setTitle("Uploading Music");
                pd.setMessage("Please Wait ....");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();


                StorageReference reference = storageReference.child(Mname+".mp3");
                reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                MusicModel model = new MusicModel(Mname,uri.toString(),auth.getUid());

                                databaseReference.child(databaseReference.push().getKey()).setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                binding.etMusicName.setText(null);
                                                binding.ivMusic.setBackgroundColor(Color.WHITE);
                                                success_toast(getApplicationContext(),"File Has Uploaded Successfully");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                binding.ivMusic.setBackgroundColor(Color.RED);
                                                error_toast(getApplicationContext(),"Failed To Upload : "+e.getMessage());
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                binding.ivMusic.setBackgroundColor(Color.RED);
                                error_toast(getApplicationContext(),"Failed To Upload : "+e.getMessage());

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
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
                filepath = data.getData();
                String name  = get_file_name_from_uri(filepath);
                binding.etMusicName.setText(name);
                binding.ivMusic.setBackgroundColor(Color.GREEN);

            }

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

}