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


public class Notes extends AppCompatActivity {
    ImageView img1 ;
    ImageButton cancel , back ;
    EditText filetitle ;
    Button browse , upload ;
    Uri filepath ;
    TextView page_name ;
    StorageReference storageReference ;
    DatabaseReference databaseReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        img1 = findViewById(R.id.img1);
        cancel = findViewById(R.id.cancel);
        back = findViewById(R.id.back_button);
        filetitle = findViewById(R.id.title);
        browse = findViewById(R.id.browse);
        upload = findViewById(R.id.upload);
        page_name = findViewById(R.id.page_name);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin_pdf");

        page_name.setText("Upload File ");

        img1.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img1.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                filetitle.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name  = filetitle.getText().toString();

                if (name.isEmpty())
                {
                    Toast.makeText(Notes.this, "Enter the title ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    processupload(filepath);
                }
            }
        });


    }

    private void processupload(Uri filepath) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File is Uploading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        StorageReference reference = storageReference.child("admin_pdf/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        filemodel filemodel = new filemodel(filetitle.getText().toString(),uri.toString());
                        databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(filemodel);
                        pd.dismiss();
                        Toast.makeText(Notes.this, " File successfully Uploaded To DataBase  ", Toast.LENGTH_SHORT).show();
                        img1.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        filetitle.setText("");

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float per = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("File uploaded : "+(int)per+"%");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
            filepath = Objects.requireNonNull(data).getData();
            img1.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
    }
}