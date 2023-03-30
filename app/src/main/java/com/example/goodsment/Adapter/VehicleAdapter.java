package com.example.goodsment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goodsment.Model.VehicleModel;
import com.example.goodsment.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.viewHolder> {
    @NonNull
    ArrayList<VehicleModel> vehicleModelArrayList;
    Context context;
    FirebaseFirestore fstore;
    String uri;

    public VehicleAdapter(@NonNull ArrayList<VehicleModel> vehicleModelArrayList, Context context) {
        this.vehicleModelArrayList = vehicleModelArrayList;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        fstore = FirebaseFirestore.getInstance();

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vehicle_select, parent, false);
        return new viewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        VehicleModel vehicleModel = vehicleModelArrayList.get(position);
        holder.txtVehicleType.setText(vehicleModel.getType());
        holder.txtPrice.setText(vehicleModel.getCharge() + ".00");

        String imgUrl = vehicleModel.getImg_url();
        Glide.with(holder.imgVehicleType.getContext()).load(imgUrl).into(holder.imgVehicleType);
    }

    @Override
    public int getItemCount() {
        return vehicleModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtVehicleType;
        ImageView imgVehicleType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.vCharges);
            txtVehicleType = itemView.findViewById(R.id.vType);
            imgVehicleType = itemView.findViewById(R.id.imgVehicleType);
        }



    }
}
