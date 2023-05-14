package com.example.parkease;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterParkingList extends RecyclerView.Adapter<AdapterParkingList.ViewHolder>{

        Context context;
        List<ModelAvailableParking> modelList;

        public AdapterParkingList(Context context, List<ModelAvailableParking> modelList) {
            this.context = context;
            this.modelList = modelList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.parking_list,null);
            ViewHolder myview= new ViewHolder(view);
            return myview;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.name.setText(String.valueOf(modelList.get(position).getParking_name()));
            holder.capacity.setText(modelList.get(position).getParking_capacity());
            holder.location.setText(modelList.get(position).getParking_address());
            holder.bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent j = new Intent(context, SelectedParking.class);
                    j.putExtra("parking_name",modelList.get(position).getParking_name());
                    context.startActivity(j);
                }
            });
        }
        @Override
        public int getItemCount() {
            return modelList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView name,capacity,location;
            LinearLayout bg;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name= itemView.findViewById(R.id.name);
                capacity= itemView.findViewById(R.id.capacity);
                location= itemView.findViewById(R.id.location);
                bg=itemView.findViewById(R.id.bg);
            }
        }

}
