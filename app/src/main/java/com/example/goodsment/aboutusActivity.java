package com.example.goodsment;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class aboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));


        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Us Page");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}