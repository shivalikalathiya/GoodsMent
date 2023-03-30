package com.example.goodsment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.goodsment.CardView;
import com.example.goodsment.Model.Labour;
import com.example.goodsment.R;
import com.example.goodsment.ownerLabourActivity;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class LabourDataAdapter extends RecyclerView.Adapter<LabourDataAdapter.MyViewHolder>{

    private Context context;
    private List<Labour> labourList;

    public LabourDataAdapter(Context context, List<Labour> labourList) {
        this.context = context;
        this.labourList = labourList;
    }

    @NonNull
    @Override
    public LabourDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_labour,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Labour labourClass = labourList.get(position);
        holder.name.setText(labourClass.getName());
        holder.mnumber.setText(labourClass.getMnumber());
        holder.aadhar.setText(labourClass.getAadhar());
        holder.address.setText(labourClass.getAddress());

        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context , ownerLabourActivity.class);
            intent.putExtra("Name",labourList.get(holder.getAdapterPosition()).getName());
            intent.putExtra("Conatct",labourList.get(holder.getAdapterPosition()).getMnumber());
            intent.putExtra("Adharcard Number",labourList.get(holder.getAdapterPosition()).getAadhar());
            intent.putExtra("Address",labourList.get(holder.getAdapterPosition()).getAddress());

            //  context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return labourList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,aadhar,address,mnumber;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.recCard);
            name = itemView.findViewById(R.id.recNameLabour);
            aadhar = itemView.findViewById(R.id.recAdharCard);
            mnumber = itemView.findViewById(R.id.recConatctLabour);
            address = itemView.findViewById(R.id.recAddress);
        }
    }

}
