package com.example.goodsment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MyProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText edtname, edtemail, edtcontact, edtcity;
    Button btnupdate;

    Uri filepath;
    ImageView imgprofile;
    FirebaseStorage storage;
    StorageReference storageReference;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        edtname = findViewById(R.id.edtUname);
        edtemail = findViewById(R.id.edtEmail);
        edtcontact = findViewById(R.id.edtCnumber);
        edtcity = findViewById(R.id.edtCity);
        btnupdate = findViewById(R.id.btnupdate);
        //toolbar = findViewById(R.id.toolbar23);
        imgprofile = findViewById(R.id.profileimage1);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("Edit Profile");
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        edtemail.setText(email);
        String st = email.replaceAll("\\.", ",");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User/Registration");

        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child(st).child("name").getValue(String.class);
                    String email = snapshot.child(st).child("email").getValue(String.class);
                    String contact = snapshot.child(st).child("contact").getValue(String.class);
                    String city = snapshot.child(st).child("city").getValue(String.class);

                    edtname.setText(name);
                    edtemail.setText(email);
                    edtcontact.setText(contact);
                    edtcity.setText(city);

                    btnupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!name.isEmpty()) {
                                edtname.setError(null);
                                if (!email.isEmpty()) {
                                    edtemail.setError(null);
                                    if (!contact.isEmpty()) {
                                        edtcontact.setError(null);
                                        if (!city.isEmpty()) {
                                            edtcity.setError(null);
                                            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                                edtemail.setError(null);
                                                if (contact.length() == 10) {
                                                    edtcontact.setError(null);

                                                    databaseReference.child(st).child("name").setValue(edtname.getText().toString().trim());
                                                    databaseReference.child(st).child("email").setValue(edtemail.getText().toString().trim());
                                                    databaseReference.child(st).child("contact").setValue(edtcontact.getText().toString().trim());
                                                    databaseReference.child(st).child("city").setValue(edtcity.getText().toString().trim());

                                                    Toast.makeText(MyProfile.this, "Uploaded.......!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    edtcontact.setError("Contact Number must be in proper formate");
                                                }
                                            } else {
                                                edtemail.setError("Email must be in proper Formate");
                                            }
                                        } else {
                                            edtcity.setError("Enter City");
                                        }
                                    } else {
                                        edtcontact.setError("Enter Contact Number");
                                    }
                                } else {
                                    edtemail.setError("Enter Email Id");
                                }
                            } else {
                                edtname.setError("Enter Name");
                            }
                        }
                    });
                } else {
                    Toast.makeText(MyProfile.this, "Data is not exists...Please check again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


}