package com.example.goodsment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodsment.Model.VehicleModel;
import com.example.goodsment.R;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.viewHolder> {
    @NonNull
    ArrayList<VehicleModel> vehicleModelArrayList;
    Context context;

    public VehicleAdapter(@NonNull ArrayList<VehicleModel> vehicleModelArrayList, Context context) {
        this.vehicleModelArrayList = vehicleModelArrayList;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vehicle_select,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        VehicleModel vehicleModel = vehicleModelArrayList.get(position);
        holder.txtVehicleType.setText(vehicleModel.getVehicleType());
        holder.txtPrice.setText(vehicleModel.getCharge());


    }

    @Override
    public int getItemCount() {
        return vehicleModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtVehicleType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.vCharges);
            txtVehicleType = itemView.findViewById(R.id.vType);
        }
    }
}
