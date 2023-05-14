package com.example.parkease;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.resources.MaterialResources;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class truncatebookedparking {
    Context context;

    public truncatebookedparking(Context context) {
        this.context = context;
    }

    AlertDialog dialog1;
    ApiInterface apiInterface;

         public void gettruncateresult() {
            Retrofit retrofit= ApiClient.getclient();
            apiInterface =retrofit.create(ApiInterface.class);
             AlertDialog.Builder builder= new AlertDialog.Builder(context);
             View view1 = LayoutInflater.from(context).inflate(R.layout.loadingdialog,null);
             builder.setView(view1);
             dialog1=builder.create();
             dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
            // dialog1.getWindow().setBackgroundDrawable(MaterialResources.getDrawable(R.drawable.dialogbackground));
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
            apiInterface.truncatebookedparking().enqueue(new Callback<InsertResponse>() {
                @Override
                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                    try{
                        if(response!=null){
                            if(response.body().getStatus().equals("1")){
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
}
