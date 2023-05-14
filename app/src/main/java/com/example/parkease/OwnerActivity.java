package com.example.parkease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OwnerActivity extends AppCompatActivity {
    AppCompatButton findavail,manageentry,parkinghistory,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        findavail=findViewById(R.id.findavailability);
        manageentry=findViewById(R.id.manageentries);
        parkinghistory=findViewById(R.id.parkinghistory);
        logout=findViewById(R.id.logout);

        findavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OwnerActivity.this,SelectedParking.class);
                i.putExtra("parking_name","Classic Parking");
                startActivity(i);
            }
        });

        parkinghistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OwnerActivity.this,OwnerHistory.class);
                startActivity(i);
            }
        });
        manageentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OwnerActivity.this,ManageEntries.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(OwnerActivity.this,Login.class);
                startActivity(i);
            }
        });
    }
}