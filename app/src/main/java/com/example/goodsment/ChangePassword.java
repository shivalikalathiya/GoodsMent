package com.example.goodsment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    RelativeLayout click;

    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        click = findViewById(R.id.click);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setTitle("Labour Detail");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View resetpasswordlayout = LayoutInflater.from(ChangePassword.this).inflate(R.layout.activity_security, null);
                EditText cpass = resetpasswordlayout.findViewById(R.id.Cpass);
                EditText newpass = resetpasswordlayout.findViewById(R.id.Newpassword);
                EditText newpassagain = resetpasswordlayout.findViewById(R.id.Newpasswordagain);
                Button btnSave = resetpasswordlayout.findViewById(R.id.btnsave);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                builder.setTitle("Change Password");
                builder.setView(resetpasswordlayout);
                AlertDialog dialog = builder.create();
                dialog.show();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Cpass = cpass.getText().toString().trim();
                        String Newpassword = newpass.getText().toString().trim();
                        String Newpasswordagain = newpassagain.getText().toString().trim();


                        if (Cpass.isEmpty() || Newpassword.isEmpty() || Newpasswordagain.isEmpty() ) {
                            Toast.makeText(ChangePassword.this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newpass.length() <6){
                            newpass.setError("Password Length Must be 6 Words");
                            return;
                        }
                        updatePasswordBtn(Cpass,Newpassword);
                    }

                    private void updatePasswordBtn(String Cpass, String Newpassword) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),Cpass);
                        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                user.updatePassword(Newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ChangePassword.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChangePassword.this, "Password Update Failed..", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

    }

    public void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}