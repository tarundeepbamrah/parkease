package com.example.parkease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterManageEntries extends RecyclerView.Adapter<AdapterManageEntries.ViewHolder> {
    Context context;
    List<ModelBookedParking> modelList;

    public AdapterManageEntries(Context context, List<ModelBookedParking> modelList) {
        this.context = context;
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.owner_history_list,null);
        ViewHolder myview= new ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.number.setText(modelList.get(position).getVehicle_number());
        holder.time.setText(modelList.get(position).getTime());
        holder.amount.setText(modelList.get(position).getAmount());
        holder.slot.setText(modelList.get(position).getSlot());
        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truncatebookedparking truncate = new truncatebookedparking(context);
                truncate.gettruncateresult();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView number,time,amount,slot;
        AppCompatButton exit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number= itemView.findViewById(R.id.vehicle);
            time= itemView.findViewById(R.id.time);
            amount= itemView.findViewById(R.id.amount);
            slot= itemView.findViewById(R.id.slot);
            exit=itemView.findViewById(R.id.exit);
        }
    }
}
