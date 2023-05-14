package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectedParking extends AppCompatActivity {
    String parkingname;
    ApiInterface apiInterface;
    List<String> ParkingList=  new ArrayList<String>();
    ArrayAdapter<String> adapter;
    AlertDialog dialog;
    ListView List;
    //String spots[] = {"1A","1B","1C","1D","1F","1G","1H","2A","2B","3B","3C","3D","3E","3F"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_parking);
        List=findViewById(R.id.List);
        parkingname=getIntent().getExtras().getString("parking_name");
        AlertDialog.Builder builder= new AlertDialog.Builder(SelectedParking.this);
        View view1 = LayoutInflater.from(SelectedParking.this).inflate(R.layout.loadingdialog,null);
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
        getresult(parkingname);

    }
    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }


    private void setadapter(List<String> ParkingList,String parkingname) {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SelectedParking.this, android.R.layout.simple_dropdown_item_1line,ParkingList);
        List.setAdapter(adapter);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent j= new Intent(SelectedParking.this,BookSpot.class);
                j.putExtra("selectedspot",ParkingList.get(i));
                j.putExtra("parkingname",parkingname);
                startActivity(j);
            }
        });
    }
    private void getresult(String parkingname) {
        apiInterface.getAvailableSpots(parkingname).enqueue(new Callback<GetAvailableSpotResponse>() {
            @Override
            public void onResponse(Call<GetAvailableSpotResponse> call, Response<GetAvailableSpotResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){

                            for(int i=0;i<response.body().getData().size();i++){
                                ParkingList.add(response.body().getData().get(i).getAvailable_spot());
                            }

                            setadapter(ParkingList,parkingname);
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(SelectedParking.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetAvailableSpotResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());
            }
        });
    }
}