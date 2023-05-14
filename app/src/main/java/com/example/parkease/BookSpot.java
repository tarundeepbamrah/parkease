package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookSpot extends AppCompatActivity implements PaymentResultListener {
    ArrayAdapter<String> adapteritem;
    AutoCompleteTextView vehicletype;
    String type[]={"Car","Bike/Scooter","Mini Truck","Van"};
    AppCompatButton cancel,confirm,booknow,paycash;
    String selectedspot,vehicle,parkingname,amt;
    AlertDialog dialog1,dialog;
    ApiInterface apiInterface;
    TextView selectedspottxt,numbertxt,spottxt,typetxt,timetxt,amounttxt;
    EditText vehiclenumber,phone,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_spot);
        selectedspot=getIntent().getExtras().getString("selectedspot");
        parkingname=getIntent().getExtras().getString("parkingname");

        selectedspottxt=findViewById(R.id.selectedspottxt);
        booknow=findViewById(R.id.booknow);
        vehicletype=findViewById(R.id.vehicletype);
        selectedspottxt.setText(selectedspot);
        vehiclenumber=findViewById(R.id.vehiclenumber);
        phone=findViewById(R.id.phone);
        time=findViewById(R.id.time);


        adapteritem= new ArrayAdapter<String>(BookSpot.this, android.R.layout.simple_dropdown_item_1line,type);
        vehicletype.setAdapter(adapteritem);
        vehicletype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vehicle=adapterView.getItemAtPosition(i).toString();
            }
        });


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!time.getText().toString().equals("0")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookSpot.this);
                    View view1 = LayoutInflater.from(BookSpot.this).inflate(R.layout.bookspotdialog, null);
                    numbertxt=view1.findViewById(R.id.number);
                    spottxt=view1.findViewById(R.id.spot);
                    typetxt=view1.findViewById(R.id.type);
                    timetxt=view1.findViewById(R.id.time);
                    amounttxt=view1.findViewById(R.id.amount);
                    cancel = view1.findViewById(R.id.cancel);
                    confirm = view1.findViewById(R.id.confirm);
                    paycash=view1.findViewById(R.id.paycash);
                    builder.setView(view1);
                    dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                    dialog.setCancelable(false);

                    numbertxt.setText(vehiclenumber.getText().toString());
                    spottxt.setText(selectedspot);
                    typetxt.setText(vehicle);
                    timetxt.setText(time.getText().toString());
                    String t=time.getText().toString();
                    amt=String.valueOf(Integer.parseInt(t)*20);
                    amounttxt.setText(String.format("Rs %s", amt));

                    dialog.show();
                    Checkout.preload(getApplicationContext());
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            paynow(amt);
                        }
                    });

                    paycash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder= new AlertDialog.Builder(BookSpot.this);
                            View view1 = LayoutInflater.from(BookSpot.this).inflate(R.layout.loadingdialog,null);
                            builder.setView(view1);
                            dialog1=builder.create();
                            dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
                            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                            dialog1.setCancelable(false);
                            dialog1.getWindow().setGravity(Gravity.CENTER);
                            dialog1.show();
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1.dismiss();
                                }
                            },10000);
                            initialization();
                            gettruncateresult();
                        }
                    });
                }
                else{
                    Toast.makeText(BookSpot.this, "Time Cannot be 0", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }


    private void getresult(String vehicle_number,String phone,String time,String vehicle_type,String parking_name,String slot,String amount) {
        apiInterface.putBookedParking(vehicle_number,phone,time,vehicle_type,parking_name,slot,amount).enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            getdeletionresult(slot);
                        }
                        else{
                            Toast.makeText(BookSpot.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());
            }
        });
    }
    private void getdeletionresult(String slot) {
        apiInterface.deleteavailableslot(slot).enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(BookSpot.this, "Successfully Booked", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            dialog1.dismiss();
                            Intent i=new Intent(BookSpot.this,ManageParking.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(BookSpot.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());
            }
        });
    }
    private void gettruncateresult() {
        apiInterface.truncatebookedparking().enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            getresult(vehiclenumber.getText().toString(),phone.getText().toString(),time.getText().toString(),vehicle,parkingname,selectedspot,amt);
                        }
                        else{
                            Toast.makeText(BookSpot.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());
            }
        });
    }
    private void paynow(String amt){
        final Activity activity=this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_IouW5H0CJB0zNt");
        checkout.setImage(R.drawable.icon);
        double floatAmount = Float.parseFloat(amt)*100;
        try{
            JSONObject options = new JSONObject();
            options.put("name","PARKEASE");
            options.put("description","Reference No. #123456");
            options.put("theme.color","#3399cc");
            options.put("currency","INR");
            options.put("amount",floatAmount+"");
            checkout.open(activity,options);

        } catch (Exception e){
            Log.e("Tag","Error in Starting Payment Process");
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder= new AlertDialog.Builder(BookSpot.this);
        View view1 = LayoutInflater.from(BookSpot.this).inflate(R.layout.loadingdialog,null);
        builder.setView(view1);
        dialog1=builder.create();
        dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog1.setCancelable(false);
        dialog1.getWindow().setGravity(Gravity.CENTER);
        dialog1.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.dismiss();
            }
        },10000);
        initialization();
        getresult(vehiclenumber.getText().toString(),phone.getText().toString(),time.getText().toString(),vehicle,parkingname,selectedspot,amt);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Parking not Booked", Toast.LENGTH_SHORT).show();
    }
}