package com.guruprasad.notesuplaoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

public class Music extends AppCompatActivity {
        EditText title ;
        Button upload , browse ;
        ImageButton back , cancel ;
        TextView head ;
        ImageView music  ;
        Uri uri ;

    StorageReference storageReference ;
    DatabaseReference databaseReference ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        title = findViewById(R.id.title);
        upload = findViewById(R.id.upload);
        browse = findViewById(R.id.browse);
        cancel = findViewById(R.id.cancel);
        music = findViewById(R.id.img1);
        back = findViewById(R.id.back_button);
        head = findViewById(R.id.page_name);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("music");


        head.setText("Upload Music ");
        cancel.setVisibility(View.INVISIBLE);
        music.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),menu.class));
                finish();
            }
        });

        cancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel.setVisibility(View.INVISIBLE);
                music.setVisibility(View.INVISIBLE);
                title.setText("");
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
                                intent.setType("audio/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent,101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(Music.this, "Allowed permission to select the audio", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name  = title.getText().toString();

                if (name.isEmpty())
                {
                    Toast.makeText(Music.this, "Enter the title ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   processupload(uri);
                }
            }
        });


    }

    private void processupload(Uri uri) {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File is Uploading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        StorageReference reference = storageReference.child("Music/" + System.currentTimeMillis() + ".mp3");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        songmodel songmodel = new songmodel(title.getText().toString(), uri.toString());
                        databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(songmodel);
                        pd.dismiss();
                        Toast.makeText(Music.this, " File successfully Uploaded To DataBase  ", Toast.LENGTH_SHORT).show();
                        music.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        title.setText("");

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float per = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                pd.setMessage("File uploaded : " + (int) per + "%");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101 && resultCode==RESULT_OK)
        {
            uri = Objects.requireNonNull(data).getData();
            music.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
    }
}