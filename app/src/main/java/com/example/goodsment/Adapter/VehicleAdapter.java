package com.example.goodsment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goodsment.Model.Charge;
import com.example.goodsment.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.viewHolder> {
    @NonNull
    ArrayList<Charge> chargeArrayList;
    Context context;

//    private OnProductItemClickListner listner;
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
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //  fstore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vehicle_select, parent, false);
        return new viewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        Charge charge = chargeArrayList.get(position);
        // String charge = chargeArrayList.get(position);
        holder.txtPrice.setText(charge.getCharge());
        holder.txtVehicleType.setText(charge.getType());
//        Charge charge = vehicleModelArrayList.get(position);
//        holder.txtVehicleType.setText(vehicleModel.getType());
//        holder.txtPrice.setText(vehicleModel.getCharge() + ".00");

        holder.ll_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                listner.onFevoriteClick(1);

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
