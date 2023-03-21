package com.example.goodsment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class legalActivity extends AppCompatActivity {

    TextView termsarrow ,privacyarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        termsarrow = findViewById(R.id.termsarrow);
        privacyarrow = findViewById(R.id.privacyarrow);

        termsarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(legalActivity.this,terms_condition.class));
            }
        });

        privacyarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(legalActivity.this,PrivacyActivity.class));
            }
        });
    }
}