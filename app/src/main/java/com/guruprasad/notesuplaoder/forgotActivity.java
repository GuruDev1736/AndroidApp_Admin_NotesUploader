package com.guruprasad.notesuplaoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class forgotActivity extends AppCompatActivity {
    EditText value ;
    Button username , password  ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        value = findViewById(R.id.value);
        username = findViewById(R.id.set_username);
        password = findViewById(R.id.set_password);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("username",name);
                startActivity(intent);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pass = Integer.parseInt(password.getText().toString()) ;

                Intent intent = new Intent();
                intent.putExtra("password",pass);
                startActivity(intent);
            }
        });


    }
}