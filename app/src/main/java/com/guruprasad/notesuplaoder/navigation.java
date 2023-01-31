package com.guruprasad.notesuplaoder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guruprasad.notesuplaoder.databinding.ActivityNavigationBinding;
import com.guruprasad.notesuplaoder.ui.Library.Books_Uploader;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class navigation extends AppCompatActivity {



    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    FirebaseDatabase database ;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
      FloatingActionButton notes , labmanual , solved_labmanual , lib_books ;
      myadapter adapter ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
         auth= FirebaseAuth.getInstance();
         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(3,155,229));

        notes = findViewById(R.id.add_notes);
        labmanual = findViewById(R.id.add_lab_manual);
        solved_labmanual = findViewById(R.id.add_solved_lab_manual);
        lib_books = findViewById(R.id.add_books);


        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView user = findViewById(R.id.username_nav);
                 TextView email =findViewById(R.id.email_nav);
                 ImageView img  = findViewById(R.id.profile_img_nav);
                usermodel usermodel = snapshot.getValue(com.guruprasad.notesuplaoder.usermodel.class);

                user.setText(Objects.requireNonNull(usermodel).getFull_name());
                email.setText(usermodel.getEmail());
                Glide.with(navigation.this).load(usermodel.profile_pic).into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(navigation.this,"An Error Occurred : " + error.getMessage(),Toast.LENGTH_SHORT,true).show();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Notes.class));
                Toasty.info(navigation.this,"upload your notes here",Toast.LENGTH_LONG,true).show();

            }
        });

        labmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),labmanuals.class));
                Toasty.info(navigation.this,"upload your Manuals here",Toast.LENGTH_LONG,true).show();

            }
        });
        
        solved_labmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),solved_lab_manual.class));
                Toasty.info(navigation.this,"upload your Solved Manuals here",Toast.LENGTH_LONG,true).show();

            }
        });

        lib_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Books_Uploader.class));
                Toasty.info(navigation.this,"upload your Books here",Toast.LENGTH_LONG,true).show();

            }
        });
            
        

     


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow , R.id.nav_lib , R.id.nav_requested_books, R.id.nav_upload_course, R.id.nav_upload_stream)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);



        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
                Toasty.info(navigation.this,"This is Setting",Toast.LENGTH_LONG,true).show();
                break;

            case R.id.logout:
                FirebaseUser user = auth.getCurrentUser();
                if (user!=null)
                {
                    auth.signOut();
                    Toasty.success(navigation.this,"Sign Out Successfully",Toast.LENGTH_LONG,true).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }
                else
                {
                    Toasty.info(navigation.this,"You aren't login Yet!",Toast.LENGTH_LONG,true).show();
                }
                break;

            case R.id.add_admin:
                Toasty.info(navigation.this,"Register New Admin",Toast.LENGTH_LONG,true).show();
                startActivity(new Intent(getApplicationContext(),admin_regestration.class));
                break ;

        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    boolean backpress = false;

    @Override
    public void onBackPressed() {
        if (backpress) {
            super.onBackPressed();
            return;
        }

        this.backpress = true;

        Toasty.info(navigation.this,"Please click BACK again to exit",Toast.LENGTH_SHORT,true).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                backpress=false;
            }
        }, 2000);
    }
}