package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CityArea extends AppCompatActivity {
    AppCompatButton findparkings;
    String city,area,selectcity;
    ApiInterface apiInterface;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    List<String> ListCity=  new ArrayList<String>();
    List<String> ListArea=  new ArrayList<String>();
    ArrayAdapter<String> adapteritem,adapteritem2;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_area);
        findparkings=findViewById(R.id.findparkings);
        autoCompleteTextView=findViewById(R.id.auto_complete_txt_city);
        autoCompleteTextView2=findViewById(R.id.auto_complete_txt_area);

        AlertDialog.Builder builder= new AlertDialog.Builder(CityArea.this);
        View view1 = LayoutInflater.from(CityArea.this).inflate(R.layout.loadingdialog,null);
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

        findparkings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city=autoCompleteTextView.getText().toString();
                area=autoCompleteTextView2.getText().toString();
                if (city.equals("")||area.equals("")){
                    Toast.makeText(CityArea.this, "Select City and Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i= new Intent(CityArea.this,FindParking.class);
                    i.putExtra("city",city);
                    i.putExtra("area",area);
                    startActivity(i);
                }

            }
        });

    }
    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    private void getresult() {
        apiInterface.getCityData().enqueue(new Callback<GetStringResponse>() {
            @Override
            public void onResponse(Call<GetStringResponse> call, Response<GetStringResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){

                            for(int i=0;i<response.body().getData().size();i++){
                                ListCity.add(response.body().getData().get(i).getCity());
                            }

                            adapteritem= new ArrayAdapter<String>(CityArea.this,R.layout.list_item,ListCity);
                            autoCompleteTextView.setAdapter(adapteritem);
                            dialog.dismiss();
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectcity=adapterView.getItemAtPosition(i).toString();
                                    ListArea.clear();
                                    dialog.show();
                                    autoCompleteTextView2.setText("");
                                    apiInterface.getAreaData(selectcity).enqueue(new Callback<GetAreaResponse>() {
                                        @Override
                                        public void onResponse(Call<GetAreaResponse> call, Response<GetAreaResponse> response) {
                                            try{
                                                if(response!=null){
                                                    if(response.body().getStatus().equals("1")){

                                                        for(int i=0;i<response.body().getData().size();i++){
                                                            ListArea.add(response.body().getData().get(i).getArea());
                                                        }

                                                        dialog.dismiss();
                                                        adapteritem2= new ArrayAdapter<String>(CityArea.this,R.layout.list_item,ListArea);
                                                        autoCompleteTextView2.setAdapter(adapteritem2);
                                                        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                String area=adapterView.getItemAtPosition(i).toString();
                                                            }
                                                        });
                                                    }
                                                    else{
                                                        Toast.makeText(CityArea.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            catch(Exception e){
                                                Log.e("Exception",e.getLocalizedMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GetAreaResponse> call, Throwable t) {
                                            Log.e("Failure",t.getLocalizedMessage());
                                        }
                                    });
                                }
                            });

                        }
                        else{
                            Toast.makeText(CityArea.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetStringResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });


    }
    }