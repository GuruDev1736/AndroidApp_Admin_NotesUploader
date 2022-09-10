package com.guruprasad.notesuplaoder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
        EditText username , password ;
        TextView forgot_password;
        Button login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username= findViewById(R.id.email_login);
        password = findViewById(R.id.password);
        forgot_password= findViewById(R.id.forgot);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                int pass =Integer.parseInt(password.getText().toString()) ;

                String name = "guruprasad" ;
                int code = 1736;

                if (TextUtils.isEmpty(user))
                {
                    username.setError("Enter the username please...");
                    return;
                }


                if (user.equals(name) && pass==code)
                {
                    Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),menu.class));
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login failed ", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}