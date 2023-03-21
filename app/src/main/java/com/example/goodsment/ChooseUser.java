package com.example.goodsment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ChooseUser extends AppCompatActivity {

    ImageView imgUser,imgOwner;
    TextView User,Owner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        setContentView(R.layout.activity_choose_user);

        imgUser = findViewById(R.id.imgUser);
        imgOwner = findViewById(R.id.imgOwner);

        User = findViewById(R.id.User);
        Owner = findViewById(R.id.Owner);



        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseUser.this,signin.class));
            }
        });

        imgOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseUser.this,signin.class));
            }
        });



        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseUser.this,signin.class));
            }
        });

        Owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseUser.this,signin.class));
            }
        });
    }
}