package com.example.goodsment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class VahicleType extends AppCompatActivity {

    Button addtocart1;
    private RecyclerView.Adapter adapter;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vahicle_type);

        initView();



    }

    private void initView() {

        addtocart1=findViewById(R.id.addtocart1);

    }

}