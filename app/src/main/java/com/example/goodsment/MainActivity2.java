package com.example.goodsment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    private static final int ERROR_DIALOG_REQUEST = 90001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (isServiceOK()){
            init();
        }
    }
    private void init(){

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,Map2.class);
                startActivity(intent);
            }
        });

    }
    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: checking google service version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity2.this);
        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOK: Google Play Service os working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity2.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You Can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}