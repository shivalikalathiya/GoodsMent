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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.goodsment.Model.RegisterUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class signup extends AppCompatActivity {

    FirebaseAuth mAuth;
    private TextInputLayout txtinputemail, txtinputpass , txtinputname , txtinputcnumber , txtinputcity;
    Button btnSignup, btnbackarrow;
    TextView txtSignin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RegisterUser registerUser;


    //
//
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Register");
        registerUser = new RegisterUser();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        mAuth = FirebaseAuth.getInstance();
        txtinputemail = (TextInputLayout) findViewById(R.id.txtinputemail);
        txtinputname = (TextInputLayout)findViewById(R.id.txtinputname);
        txtinputcity = (TextInputLayout)findViewById(R.id.txtinputcity);
        txtinputcnumber = (TextInputLayout)findViewById(R.id.txtinputcnumber);
        txtinputpass = (TextInputLayout)findViewById(R.id.txtinputpass);
        //btnbackarrow = findViewById(R.id.btnbackarrow);
        txtSignin = findViewById(R.id.txtsignin);
        btnSignup=findViewById(R.id.btnSignup);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(txtinputname.getEditText()).getText().toString();
                String email = txtinputemail.getEditText().getText().toString();
                String contact = txtinputcnumber.getEditText().getText().toString();
                String city = txtinputcity.getEditText().getText().toString();
                String password = txtinputpass.getEditText().getText().toString();
                String em = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$");
                String pass = "^(?=.[0-9])(?=.[A-Z])(?=.* [@#$%^&+=!])(?=\\S+$).{4,}$";
                //  "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$";
                // "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                String st= email.replaceAll("\\.",",");

                if(!name.isEmpty()){
                    txtinputname.setError(null);
                    txtinputname.setErrorEnabled(false);
                    if (!email.isEmpty()){
                        txtinputemail.setError(null);
                        txtinputemail.setErrorEnabled(false);
                        if (!contact.isEmpty()){
                            txtinputcnumber.setError(null);
                            txtinputcnumber.setErrorEnabled(false);
                            if(!city.isEmpty()){
                                txtinputcity.setError(null);
                                txtinputcity.setErrorEnabled(false);
                                if(!password.isEmpty()){
                                    txtinputpass.setError(null);
                                    txtinputpass.setErrorEnabled(false);
                                    if (name.length() > 0){
                                        txtinputname.setError(null);
                                        txtinputname.setErrorEnabled(false);
                                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                            txtinputemail.setError(null);
                                            txtinputemail.setErrorEnabled(false);
                                            if (contact.length()==10){
                                                txtinputcnumber.setError(null);
                                                txtinputcnumber.setErrorEnabled(false);
                                                if (city.length() > 0){
                                                    txtinputcity.setError(null);
                                                    txtinputcity.setErrorEnabled(false);
                                                    if (password.length() >8){
                                                        txtinputpass.setError(null);
                                                        txtinputpass.setErrorEnabled(false);

                                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                                        databaseReference = firebaseDatabase.getReference("User/Registration");

                                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if (snapshot.child(st).exists()){
                                                                    Toast.makeText(signup.this, "User is alerady Exists", Toast.LENGTH_SHORT).show();

                                                                }
                                                                else {
                                                                    RegisterUser registeruser = new RegisterUser(name,email,contact,city,password);
                                                                    databaseReference.child(st).setValue(registeruser);

                                                                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                                                        @Override
                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                            if (task.isSuccessful()) {
                                                                                startActivity(new Intent(signup.this, signin.class));
                                                                                Toast.makeText(signup.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                                                                finish();

                                                                            } else {
                                                                                Toast.makeText(signup.this, "Registration Failed..", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });

                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(signup.this, "Failed to Add Data..", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                    else {
                                                        txtinputpass.setError("Please! Enter Valid Password");
                                                    }
                                                }
                                                else {
                                                    txtinputcity.setError("Please! Enter Valid City");
                                                }
                                            }
                                            else {
                                                txtinputcnumber.setError("Please! Enter Valid Contact Number");
                                            }
                                        }
                                        else {
                                            txtinputemail.setError("Please! Enter Valid Email Adress");
                                        }
                                    }
                                    else {
                                        txtinputname.setError("Please! Enter Valid Full Name");
                                    }
                                }
                                else {
                                    txtinputpass.setError("Please! Enter Password");
                                }
                            }
                            else {
                                txtinputcity.setError("Please! Enter City");
                            }
                        }
                        else {
                            txtinputcnumber.setError("Please! Enter Contact Number");
                        }
                    }
                    else {
                        txtinputemail.setError("Please! Enter Email Adress");
                    }
                }
                else {
                    txtinputname.setError("Please! Enter Name");
                }
            }
        });


        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, signin.class));
            }
        });
    }
}