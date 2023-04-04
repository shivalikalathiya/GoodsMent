package com.example.goodsment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goodsment.Model.Charge;
import com.example.goodsment.Model.Charges;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VahicleType extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "VahicleType";
    RecyclerView recyclerView;

    TextView type, charge, textView;

    Context context;
    Button btnCheckout, btnsaveaddress;
    TextView paytext, selectGoodsType;
    TextView txtTotalCharge;
    FirebaseAuth mAuth;
    ArrayList<Charge> chargeArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    VehicleAdapter vehicleAdapter;
    List<Integer> chargeList = new ArrayList<>();

    // FirebaseFirestore db;
    String intValue;
    double intValue1;
    private String km[];

    public int price_data;
    public static double totalprice_data;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // firebaseDatabase.setPersistenceEnabled(true);
        setContentView(R.layout.activity_vahicle_type);
        mAuth = FirebaseAuth.getInstance();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));


        toolbar = findViewById(R.id.toolbar5);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Vahicle Type");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtTotalCharge = findViewById(R.id.totalCharge);
        recyclerView = findViewById(R.id.rv);
        type = findViewById(R.id.vType);
        charge = findViewById(R.id.vCharges);
        btnsaveaddress = findViewById(R.id.btnsaveaddress);

        textView = findViewById(R.id.txtTotalKm);
        Intent intent = getIntent();

         intValue = intent.getStringExtra("Kilometer");
        km = intValue.split(" ");
        Log.e("kmmmm",""+km[0]);

        intValue1= Double.parseDouble(km[0]);

        textView.setText(intValue+"");


        int intValue1 = 10;
        int price_data1 = 50;
//        String total = String.valueOf(Integer.parseInt(String.valueOf(kilomitar)) * Integer.parseInt(String.valueOf(price_data)));
//        String total = String.valueOf(Integer.parseInt(intValue) * Integer.parseInt(price_data));
//        txtTotalCharge.setText("Total Kilometr : "+total);
//        Toast.makeText(VahicleType.this, txtTotalCharge.getText().toString(), Toast.LENGTH_SHORT).show();


        btnCheckout = findViewById(R.id.btnCheckout);
        paytext = findViewById(R.id.paytext);
        // back=findViewById(R.id.back);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makepayment();
            }
        });


        btnsaveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VahicleType.this, AddAdress.class));
            }
        });

        Checkout.preload(getApplicationContext());


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        chargeArrayList = new ArrayList<Charge>();
        vehicleAdapter = new VehicleAdapter(chargeArrayList, context);
        recyclerView.setAdapter(vehicleAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Vehicle Type/Type");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemsnapshot : snapshot.getChildren()) {

                    //  String charge = snapshot.child("Type").getValue(String.class);
                    Charge charge = itemsnapshot.getValue(Charge.class);
                    chargeArrayList.add(charge);
                    //  txtTotalCharge.setText(charge);
                }
                vehicleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        String key = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.getRoot().child("Vehicle Type").child("Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Charges charges = child.getValue(Charges.class);
                        chargeList.add(Integer.parseInt(charges.Charge));
                    }

                    Log.d(TAG, "onDataChange: =============>> " + (Float.parseFloat(km[0]) * chargeList.get(0)));
                    Log.d(TAG, "onDataChange: =============>> " + (Float.parseFloat(km[0]) * chargeList.get(1)));
                    Log.d(TAG, "onDataChange: =============>> " + (Float.parseFloat(km[0]) * chargeList.get(2)));
                    Log.d(TAG, "onDataChange: =============>> " + (Float.parseFloat(km[0]) * chargeList.get(3)));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


/*
        databaseReference.child("Vehicle Type").child("Type").child("2-Wheeler").child("Charge").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value1 = snapshot.child("Vehicle Type/Type/2-Wheeler/Charge").getValue(String.class);

                databaseReference.child("Vehicle Type").child("Kilometer").child(key).child("kilometer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value2 = snapshot.child("Vehicle Type").child("Kilometer").child(key).child("kilometer").getValue(String.class);

                        String total = String.valueOf(Integer.parseInt(value1) * Integer.parseInt(value2));
                        txtTotalCharge.setText(total);
                        Toast.makeText(VahicleType.this, txtTotalCharge.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/


//        db = FirebaseFirestore.getInstance();
//        db.collection("VehicleType").get()
//
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        for (DocumentSnapshot d : list) {
//                            VehicleModel obj = d.toObject(VehicleModel.class);
//                            vehicleModelArrayList.add(obj);
//
//                        }
//                        vehicleAdapter.notifyDataSetChanged();
//                    }
//                });
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
            options.put("prefill.contact", "9081063320");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public void onPaymentSuccess(String s) {
        paytext.setText("Sucessful payment ID :" + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        paytext.setText("Failed and cause is :" + s);
    }


    public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.viewHolder> {
        @NonNull
        ArrayList<Charge> chargeArrayList;
        Context context;

        //            private OnProductItemClickListner listner;
        FirebaseFirestore fstore;
        FirebaseDatabase firebaseDatabase;
        String uri;

//    public interface OnProductItemClickListner {
//        void onFevoriteClick(int pos);
//    }


        public VehicleAdapter(@NonNull ArrayList<Charge> chargeArrayList, Context context) {
            this.chargeArrayList = chargeArrayList;
            this.context = context;

        }


        @Override
        public VehicleAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //  fstore = FirebaseFirestore.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vehicle_select, parent, false);
            return new VehicleAdapter.viewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull VehicleAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

            Charge charge = chargeArrayList.get(position);
            // String charge = chargeArrayList.get(position);
            holder.txtPrice.setText(charge.getCharge());
            holder.txtVehicleType.setText(charge.getType());
//        Charge charge = vehicleModelArrayList.get(position);
//        holder.txtVehicleType.setText(vehicleModel.getType());
//        holder.txtPrice.setText(vehicleModel.getCharge() + ".00");
            Log.e("charge1", "" + charge.getCharge());
            Log.e("charge2", "" + charge.getType());
            holder.ll_vehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    price_data = Integer.parseInt(charge.getCharge());
                    Log.e("deta_price", "" + charge.getCharge());
//                listner.onFevoriteClick(1);

                    totalprice_data =price_data*intValue1;

                    Log.e("totalprice_data", "" + totalprice_data);
                    txtTotalCharge.setText("Total Charges :"+totalprice_data);
                    Log.e("deta_price1", "" + totalprice_data);

                }
            });
//
            String imgUrl = charge.getImgUrl();
            Glide.with(holder.imgVehicleType.getContext()).load(imgUrl).into(holder.imgVehicleType);


        }

        @Override
        public int getItemCount() {
            return chargeArrayList.size();
        }


        public class viewHolder extends RecyclerView.ViewHolder {
            TextView txtPrice, txtVehicleType;
            ImageView imgVehicleType;
            LinearLayout ll_vehicle;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                txtPrice = itemView.findViewById(R.id.vCharges);
                txtVehicleType = itemView.findViewById(R.id.vType);
                imgVehicleType = itemView.findViewById(R.id.imgVehicleType);
                ll_vehicle = itemView.findViewById(R.id.ll_vehicle);

//            DocumentReference docRef1 = fstore.collection("VehicleType").document();
//            DocumentReference docRef2 = fstore.collection("Kilometer").document();
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(v.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
//                    /** Go through each item if you have few items within RecyclerView. */
//                    if (getLayoutPosition() == 0) {
//                        // Do whatever you want here
//
//                    } else if(getLayoutPosition() == 1) {
//                        // Do whatever you want here
//                    } else if(getLayoutPosition() == 2) {
//                        // Do whatever you want here
//                    }
//
//                    /** Or you can use For loop if you have long list of items. */
//                    for (int i = 0; i < vehicleModelArrayList.size(); i++) {
//                        // Do whatever you want here
//                    }
//                }
//            });
//        }


            }
        }
    }


}