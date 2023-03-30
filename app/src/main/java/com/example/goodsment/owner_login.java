package com.example.goodsment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class owner_login extends AppCompatActivity {


        TextInputLayout txtinputemail;
        TextInputLayout txtinputpass;
        Button btnSignin;
        TextView txtSignup, txtForgotpassword;
        FirebaseAuth mAuth;
        FirebaseDatabase database;

//        @Override
//        protected void onStart() {
//            super.onStart();
//            if(mAuth.getCurrentUser()!=null){
//                startActivity(new Intent(owner_login.this,owner_main.class));
//                finish();
//            }
//            super.onStart();
//        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance("https://goodsment-default-rtdb.firebaseio.com/");
            btnSignin = findViewById(R.id.btnSignin);
            txtinputemail = (TextInputLayout) findViewById(R.id.txtinputemail);
            txtinputpass =(TextInputLayout) findViewById(R.id.txtinputpass);
            txtSignup = findViewById(R.id.txtSingup);
            txtForgotpassword = findViewById(R.id.txtForgotpassword);


            btnSignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = txtinputemail.getEditText().getText().toString();
                    String password = txtinputpass.getEditText().getText().toString();
                    String em = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$");
                    String pass = "^(?=.[0-9])(?=.[A-Z])(?=.* [@#$%^&+=!])(?=\\S+$).{4,}$";

                    if (!email.isEmpty()) {
                        txtinputemail.setError(null);
                        txtinputemail.setErrorEnabled(false);
                        if (!password.isEmpty()){
                            txtinputpass.setError(null);
                            txtinputpass.setErrorEnabled(false);
                            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                txtinputemail.setError(null);
                                txtinputemail.setErrorEnabled(false);
                                if (password.length() >8) {
                                    txtinputpass.setError(null);
                                    txtinputpass.setErrorEnabled(false);

                                    //Authentication
                                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(owner_login.this, owner_main.class));
                                                Toast.makeText(owner_login.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(owner_login.this, "Login Failed..", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                                }
                                else {
                                    txtinputpass.setError("Please! Enter Valid Password");
                                }
                            }
                            else {
                                txtinputemail.setError("Please! Enter Valid Email Address");
                            }
                        }
                        else {
                            txtinputpass.setError("Please! Enter Password");
                        }
                    }
                    else {
                        txtinputemail.setError("Please! Enter Email Address");
                    }
                }
            });


            txtSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(owner_login.this, owner_signup.class));
                }
            });


            txtForgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(owner_login.this, owner_forgot.class));
                }
            });


        }
    }
