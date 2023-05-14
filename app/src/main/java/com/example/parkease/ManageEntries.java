package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageEntries extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterOwnerHistory adapter;
    ApiInterface apiInterface;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_entries);
        AlertDialog.Builder builder= new AlertDialog.Builder(ManageEntries.this);
        View view1 = LayoutInflater.from(ManageEntries.this).inflate(R.layout.loadingdialog,null);
        builder.setView(view1);
        dialog=builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        //dialog.getWindow().setLayout(600,400);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },10000);

        initialization();
        getresult();
    }
    private void initialization() {
        recyclerView = findViewById(R.id.recycler_view);
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }


    private void setadapter(List<ModelBookedParking> model) {
        adapter = new AdapterOwnerHistory(ManageEntries.this, model);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageEntries.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult()
    {
        apiInterface.getbookedparking().enqueue(new Callback<GetBookedParkingResponse>() {
            @Override
            public void onResponse(Call<GetBookedParkingResponse> call, Response<GetBookedParkingResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            setadapter(response.body().getData());
                            dialog.dismiss();

                        }
                        else{
                            Toast.makeText(ManageEntries.this, "No Parking History", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetBookedParkingResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });
    }
}