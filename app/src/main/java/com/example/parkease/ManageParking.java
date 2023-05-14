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

public class ManageParking extends AppCompatActivity implements PaymentResultListener {
    TextView parking,address,slot,number,time,amount,booking,vnotext,timetext,amounttext;
    AppCompatButton extendtime,cancel,paycash,confirm;
    ApiInterface apiInterface;
    AlertDialog dialog1,dialog;
    EditText timeedittext;
    String amt,vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking);
        parking=findViewById(R.id.parking);
        address=findViewById(R.id.address);
        slot=findViewById(R.id.slot);
        number=findViewById(R.id.number);
        time=findViewById(R.id.time);
        amount=findViewById(R.id.amount);
        extendtime=findViewById(R.id.extendtime);
        vnotext=findViewById(R.id.vnotext);
        booking=findViewById(R.id.booking);
        timetext=findViewById(R.id.timetext);
        amounttext=findViewById(R.id.amounttext);
        AlertDialog.Builder builder= new AlertDialog.Builder(ManageParking.this);
        View view1 = LayoutInflater.from(ManageParking.this).inflate(R.layout.loadingdialog,null);
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
        getresult();

        extendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageParking.this);
                View view1 = LayoutInflater.from(ManageParking.this).inflate(R.layout.extendtimedialog, null);
                timeedittext=view1.findViewById(R.id.time);
                cancel = view1.findViewById(R.id.cancel);
                confirm = view1.findViewById(R.id.confirm);
                paycash=view1.findViewById(R.id.paycash);
                builder.setView(view1);
                dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                dialog.setCancelable(false);
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
                        if (!time.getText().toString().equals("0")) {
                            String t = timeedittext.getText().toString();
                            amt = String.valueOf(Integer.parseInt(t) * 20);
                            paynow(amt);
                        }
                        else{
                            Toast.makeText(ManageParking.this, "Time Cannot be 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                paycash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!time.getText().toString().equals("0")) {
                            String t = timeedittext.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(ManageParking.this);
                            View view1 = LayoutInflater.from(ManageParking.this).inflate(R.layout.loadingdialog, null);
                            builder.setView(view1);
                            dialog1 = builder.create();
                            dialog1.getWindow().getAttributes().windowAnimations = R.style.animation;
                            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                            dialog1.setCancelable(false);
                            dialog1.getWindow().setGravity(Gravity.CENTER);
                            dialog1.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1.dismiss();
                                }
                            }, 10000);
                            initialization();
                            updatedata(timeedittext.getText().toString(),vehicle);

                        }
                        else{
                            Toast.makeText(ManageParking.this, "Time Cannot be 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    private void updatedata(String time,String vehicle_number) {
        apiInterface.updatedata(time,vehicle_number).enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(ManageParking.this, "Time Extended", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                            startActivity(new Intent(ManageParking.this,Homepage.class));
                        }
                        else{
                            Toast.makeText(ManageParking.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    private void getresult() {
        apiInterface.getbookedparking().enqueue(new Callback<GetBookedParkingResponse>() {
            @Override
            public void onResponse(Call<GetBookedParkingResponse> call, Response<GetBookedParkingResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            parking.setText(response.body().getData().get(0).getParking_name());
                            address.setText(response.body().getData().get(0).getParking_address());
                            slot.setText(response.body().getData().get(0).getSlot());
                            number.setText(response.body().getData().get(0).getVehicle_number());
                            vehicle=response.body().getData().get(0).getVehicle_number();
                            time.setText(response.body().getData().get(0).getTime());
                            amount.setText(response.body().getData().get(0).getAmount());
                            //paid.setText(response.body().getData().get(0).getParking_name());
                            dialog1.dismiss();
                        }
                        else{
                            //Toast.makeText(ManageParking.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            booking.setText("NO RECENT BOOKING");
                            vnotext.setText(" ");
                            timetext.setText(" ");
                            amounttext.setText(" ");
                            extendtime.setEnabled(false);
                            dialog1.dismiss();
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
        AlertDialog.Builder builder= new AlertDialog.Builder(ManageParking.this);
        View view1 = LayoutInflater.from(ManageParking.this).inflate(R.layout.loadingdialog,null);
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
        updatedata(timeedittext.getText().toString(),vehicle);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Parking not Booked", Toast.LENGTH_SHORT).show();
    }
}