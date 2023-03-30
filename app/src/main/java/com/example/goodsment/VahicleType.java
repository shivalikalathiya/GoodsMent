package com.example.goodsment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodsment.Adapter.VehicleAdapter;
import com.example.goodsment.Model.VehicleModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VahicleType extends AppCompatActivity implements PaymentResultListener {



    RecyclerView recyclerView;

    TextView type , charge , textView;


    Context context;
    Button btnCheckout , back;
    TextView paytext , selectGoodsType ;
    TextView txtTotalCharge;
    ArrayList<VehicleModel> vehicleModelArrayList;

    VehicleAdapter vehicleAdapter;

    FirebaseFirestore db;

    Bundle intentExtras;

    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vahicle_type);



        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

        //////////////get extra //////////////////////////////////////////////////////
        textView=findViewById(R.id.textView);
        intentExtras = getIntent().getExtras();
        if (intentExtras != null){
            int number = getIntent().getExtras().getInt("Kilometer");
            textView.setText(number);
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        btnCheckout = findViewById(R.id.btnCheckout);
        paytext=findViewById(R.id.paytext);
       // back=findViewById(R.id.back);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                makepayment();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////
        txtTotalCharge = findViewById(R.id.totalCharge);
        recyclerView = findViewById(R.id.rv);
        type  = findViewById(R.id.vType);
        charge  = findViewById(R.id.vCharges);


        Checkout.preload(getApplicationContext());




        //New code
        ////////////////////////////////////////////////////////////



        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        vehicleModelArrayList = new ArrayList<>();
        //vehicleModelArrayList.add(new VehicleModel(R.drawable.tatatruck));
//        vehicleModelArrayList.add(new VehicleModel(R.drawable.cargotruck));
        vehicleAdapter = new VehicleAdapter(vehicleModelArrayList,context);
        recyclerView.setAdapter(vehicleAdapter);

        db = FirebaseFirestore.getInstance();
        db.collection("VehicleType").get()

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            VehicleModel obj = d.toObject(VehicleModel.class);
                            vehicleModelArrayList.add(obj);

                        }
                        vehicleAdapter.notifyDataSetChanged();
                    }
                });
//        DocumentReference docRef = db.collection("VehicleType").document("2-Wheeler");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
////                        Toast.makeText(context, document.getData().toString(), Toast.LENGTH_SHORT).show();
//                        LinearLayoutManager linearLayoutManager = new GridLayoutManager(context,2);
//                        recyclerView.setLayoutManager(linearLayoutManager);
//
//                        VehicleAdapter adapter = new VehicleAdapter(arrayList, context);
//                        recyclerView.setAdapter(adapter);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

      //  EventChangeListner();

    }

// payment////////////////////////////////////////////////////////////////////////////////////////////////


    private void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_sqrqKLzFmNULDF");

        checkout.setImage(R.drawable.applogo);
        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "GoodsMent");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//500*100
            options.put("prefill.email", "shivalikalathiya48@gmail.com");
            options.put("prefill.contact","9081063320");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }
    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public void onPaymentSuccess(String s) {paytext.setText("Sucessful payment ID :"+s);}

    @Override
    public void onPaymentError(int i, String s) {paytext.setText("Failed and cause is :"+s);}
}