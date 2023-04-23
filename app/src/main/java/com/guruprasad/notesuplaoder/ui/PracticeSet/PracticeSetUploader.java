package com.guruprasad.notesuplaoder.ui.PracticeSet;

import static android.app.Activity.RESULT_OK;
import static com.guruprasad.notesuplaoder.Constants.error_toast;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.guruprasad.notesuplaoder.databinding.FragmentPracticeSetUploaderBinding;
import com.guruprasad.notesuplaoder.file_model;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class PracticeSetUploader extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentPracticeSetUploaderBinding binding ;
    Uri filepath;
    FirebaseAuth auth;
    List<String> files, status;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    com.guruprasad.notesuplaoder.adapter.uploadAdpter uploadAdpter;

    ArrayList<Uri> FileList = new ArrayList<Uri>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        files = new ArrayList<>();
        status = new ArrayList<>();
        uploadAdpter = new uploadAdpter(files, status);
        auth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Practice Set");

        binding = FragmentPracticeSetUploaderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.recview.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.recview.setAdapter(uploadAdpter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.department_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.departmentSpinner.setAdapter(adapter);
        binding.departmentSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> sem_adapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.year, android.R.layout.simple_spinner_item);
        sem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(sem_adapter);
        binding.yearSpinner.setOnItemSelectedListener(this);




        return view ;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        ProgressDialog pd = new ProgressDialog(binding.getRoot().getContext());
        pd.setTitle("Practice Set's are Uploading");
        pd.setMessage("Please Wait ....");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        String department = binding.departmentSpinner.getSelectedItem().toString();
        String year = binding.yearSpinner.getSelectedItem().toString();


                if (department.equals("Computer") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_comp, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Computer") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_comp, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Computer") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_comp, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Mechanical") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_mech, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Mechanical") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_mech, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Mechanical") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_mech, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Civil") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_civil, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }

                if (department.equals("Civil") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_civil, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("Civil") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_civil, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("E amd TC") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_E_and_TC, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("E amd TC") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_E_and_TC, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("E amd TC") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_E_and_TC, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("IT") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_IT, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("IT") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_IT, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("IT") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_IT, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("Pharmacy") && year.equals("First Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.first_pharmacy, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("Pharmacy") && year.equals("Second Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.second_pharmacy, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }
                if (department.equals("Pharmacy") && year.equals("Third Year"))
                {
                    ArrayAdapter<CharSequence> sub = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.third_pharmacy, android.R.layout.simple_spinner_item);
                    sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.subjectSpinner.setAdapter(sub);

                }


        String subject = binding.subjectSpinner.getSelectedItem().toString();


        binding.browse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dexter.withContext(binding.getRoot().getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
                                        Toast.makeText(binding.getRoot().getContext(), "Please give the permission to browse the files", Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {

                if (FileList.size()==0)
                {
                    Toast.makeText(binding.getRoot().getContext(), "Select the Files To Upload ", Toast.LENGTH_SHORT).show();
                    return;
                }

                    pd.show();
                    for (int j = 0; j < FileList.size(); j++) {
                        Uri PerFile = FileList.get(j);
                        String filename = get_file_name_from_uri(PerFile);
                        files.add(filename);
                        status.add("loading");
                        uploadAdpter.notifyDataSetChanged();

                        final int index = j;

                        StorageReference reference = storageReference.child("Practice Set").child(department).child(year).child(subject).child(filename + ".pdf");
                        reference.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        file_model filemodel = new file_model(filename, uri.toString(), auth.getCurrentUser().getUid());

                                        databaseReference.child(department).child(year).child(subject).child(databaseReference.push().getKey()).setValue(filemodel);
                                        status.remove(index);
                                        status.add(index, "done");
                                        uploadAdpter.notifyDataSetChanged();
                                        pd.dismiss();
                                        Toast.makeText(binding.getRoot().getContext(), "Book Uploaded : " + filename, Toast.LENGTH_SHORT).show();
                                        FileList.clear();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(binding.getRoot().getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
            error_toast(binding.getRoot().getContext(), "Invalid Input");
    }

    public String get_file_name_from_uri(Uri file_path) {
        String result = null;
        if (file_path.getScheme().equals("content")) {
            Cursor cursor = binding.getRoot().getContext().getContentResolver().query(file_path, null, null, null, null);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                Toast.makeText(binding.getRoot().getContext(), "You Have Selected " + FileList.size() +" Files ", Toast.LENGTH_LONG).show();

            }
        }
    }


}