package com.example.parkease;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterParkingHistory extends RecyclerView.Adapter<AdapterParkingHistory.ViewHolder> {
    Context context;
    List<ModelBookedParking> modelList;

    public AdapterParkingHistory(Context context, List<ModelBookedParking> modelList) {
        this.context = context;
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.parking_history_list,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.parkingname.setText(modelList.get(position).getParking_name());
        holder.location.setText(modelList.get(position).getParking_address());
        holder.number.setText(modelList.get(position).getVehicle_number());
        holder.time.setText(modelList.get(position).getTime());
        holder.amount.setText(modelList.get(position).getAmount());
        holder.slot.setText(modelList.get(position).getSlot());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView parkingname,location,number,time,amount,slot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingname= itemView.findViewById(R.id.pname);
            number= itemView.findViewById(R.id.vehicle);
            time= itemView.findViewById(R.id.time);
            amount= itemView.findViewById(R.id.amount);
            slot= itemView.findViewById(R.id.slot);
            location= itemView.findViewById(R.id.address);
        }
    }
}
