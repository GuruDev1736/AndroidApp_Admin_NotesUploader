package com.guruprasad.notesuplaoder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.guruprasad.notesuplaoder.controller.Custom_progrsss_bar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
        EditText Email , password ;
        Button login ;
        FirebaseAuth firebaseAuth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Email= findViewById(R.id.email_login);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);
         firebaseAuth = FirebaseAuth.getInstance();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null)
        {
            Intent intent1 =new Intent(MainActivity.this,navigation.class);
            startActivity(intent1);
            finish();

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Custom_progrsss_bar custom_progrsss_bar = new Custom_progrsss_bar(MainActivity.this);


                String email = Email.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Email.setError("Enter the username....");
                    return;
                }

                if (TextUtils.isEmpty(pass))
                {
                    password.setError("Enter the password...");
                    return;
                }


                custom_progrsss_bar.start_progress();


                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),navigation.class));
                        finish();
                        custom_progrsss_bar.dismiss_progress();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        custom_progrsss_bar.dismiss_progress();
                    }
                });



            }
        });




    }
}