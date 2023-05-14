package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity {
    AppCompatButton find_parking,manageparking,parkinghistory,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        find_parking=findViewById(R.id.findparking);
        manageparking=findViewById(R.id.manageparking);
        parkinghistory=findViewById(R.id.parkinghistory);
        logout=findViewById(R.id.logout);

        find_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage.this,CityArea.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage.this,Login.class);
                startActivity(i);
            }
        });

        manageparking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage.this,ManageParking.class);
                startActivity(i);
            }
        });
        parkinghistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage.this,ParkingHistory.class);
                startActivity(i);
            }
        });

    }
}