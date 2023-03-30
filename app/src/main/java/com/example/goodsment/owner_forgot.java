package com.example.goodsment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class owner_forgot extends AppCompatActivity {

     EditText edtEmail;
     Button btnsend;
     FirebaseAuth mAuth;
     String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_forgot);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtEmail = findViewById(R.id.edtEmail);
        btnsend = findViewById(R.id.btnsend);
        mAuth = FirebaseAuth.getInstance();

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void validateData() {
        email = edtEmail.getText().toString();
        if (email.isEmpty()){
            edtEmail.setError("Required");
        } else {
            forgotpass();
        }
    }

    private void forgotpass() {

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(owner_forgot.this, "Check your Email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(owner_forgot.this,owner_login.class));
                        finish();
                    }else {
                        Toast.makeText(owner_forgot.this, "Something went Wrong"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });





    }
}