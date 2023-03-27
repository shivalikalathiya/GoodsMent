package com.example.goodsment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodsment.Adapter.VehicleAdapter;
import com.example.goodsment.Model.VehicleModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class VahicleType extends AppCompatActivity {



    RecyclerView recyclerView;

    Context context;
    Button btnCheckout;
    TextView txtTotalCharge;
    ArrayList<VehicleModel> arrayList;

    FirebaseFirestore dbroot;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vahicle_type);
        btnCheckout = findViewById(R.id.btnCheckout);
        txtTotalCharge = findViewById(R.id.totalCharge);
        recyclerView = findViewById(R.id.rv);

        arrayList = new ArrayList<>();

        dbroot=FirebaseFirestore.getInstance();


        LinearLayoutManager linearLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(linearLayoutManager);

        VehicleAdapter adapter = new VehicleAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);



    }


}