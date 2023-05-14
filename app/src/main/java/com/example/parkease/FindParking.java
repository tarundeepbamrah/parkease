package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindParking extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterParkingList adapter;
    String city,area;
    ApiInterface apiInterface;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_parking);
        city=getIntent().getExtras().getString("city");
        area=getIntent().getExtras().getString("area");

        AlertDialog.Builder builder= new AlertDialog.Builder(FindParking.this);
        View view1 = LayoutInflater.from(FindParking.this).inflate(R.layout.loadingdialog,null);
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
        getresult(city,area);

    }
    private void initialization() {
        recyclerView = findViewById(R.id.recycler_view);
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }


    private void setadapter(List<ModelAvailableParking> model) {
        adapter = new AdapterParkingList(FindParking.this, model);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindParking.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getresult(String city,String area)
    {
        apiInterface.getAvailableParking(city,area).enqueue(new Callback<GetAvailableParkingResponse>() {
            @Override
            public void onResponse(Call<GetAvailableParkingResponse> call, Response<GetAvailableParkingResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            setadapter(response.body().getData());
                            dialog.dismiss();

                        }
                        else{
                            Toast.makeText(FindParking.this, "No Parking in this Area", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetAvailableParkingResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });
    }
}