package com.example.goodsment;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.goodsment.Model.Labour;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ownerAddLabourActivity extends AppCompatActivity {

    TextInputLayout txtinputname , txtinputcnumber , txtinputcity , txtinputadharcard;
    Button btnaddlabour;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_labour);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        Toolbar toolbar = findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add Labour Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        txtinputname = findViewById(R.id.txtinputname);
        txtinputcnumber = findViewById(R.id.txtinputcnumber);
        txtinputcity = findViewById(R.id.txtinputcity);
        txtinputadharcard = findViewById(R.id.txtinputaadhar);
        btnaddlabour = findViewById(R.id.btnaddlabour);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("VehicleOwner/Labour");


        btnaddlabour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = txtinputname.getEditText().getText().toString();
                String mnumber = txtinputcnumber.getEditText().getText().toString();
                String address = txtinputcity.getEditText().getText().toString();
                String aadhar = txtinputadharcard.getEditText().getText().toString();

                if (!name.isEmpty()){
                    txtinputname.setError(null);
                    txtinputname.setErrorEnabled(false);
                    if (!mnumber.isEmpty()){
                        txtinputcnumber.setError(null);
                        txtinputcnumber.setErrorEnabled(false);
                        if (!address.isEmpty()){
                            txtinputcity.setError(null);
                            txtinputcity.setErrorEnabled(false);
                            if (!aadhar.isEmpty()){
                                txtinputadharcard.setError(null);
                                txtinputadharcard.setErrorEnabled(false);
                                if (name.length()>0){
                                    txtinputname.setError(null);
                                    txtinputname.setErrorEnabled(false);
                                    if (mnumber.length()==10){
                                        txtinputcnumber.setError(null);
                                        txtinputcnumber.setErrorEnabled(false);
                                        if (address.length()>0){
                                            txtinputadharcard.setError(null);
                                            txtinputadharcard.setErrorEnabled(false);
                                            if (aadhar.length()==12){
                                                txtinputadharcard.setError(null);
                                                txtinputadharcard.setErrorEnabled(false);

                                                firebaseDatabase = FirebaseDatabase.getInstance();
                                                databaseReference = firebaseDatabase.getReference("VehicleOwner/Labour");

                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.child(name).exists()){
                                                            Toast.makeText(ownerAddLabourActivity.this, "Labour is Already Exists", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Labour labour = new Labour(name,mnumber,address,aadhar);
                                                            databaseReference.child(name).setValue(labour);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText(ownerAddLabourActivity.this, "Failed to Add Data", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                            else {
                                                txtinputadharcard.setError("Adharcard Number must be 12 Digits!");
                                            }
                                        }else {
                                            txtinputcity.setError("City is Requried");
                                        }
                                    }
                                    else {
                                        txtinputcnumber.setError("Contact Number must be 10 digits!");
                                    }
                                }
                                else {
                                    txtinputname.setError("Name is Requried!");
                                }
                            }else {
                                txtinputadharcard.setError("Please Enter Adharcard Number");
                            }
                        }else {
                            txtinputcity.setError("Please Enter Address");
                        }
                    }else {
                        txtinputcnumber.setError("Please Enter Contact Number");
                    }
                }
                else {
                    txtinputname.setError("Please Enter Name");
                }
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}