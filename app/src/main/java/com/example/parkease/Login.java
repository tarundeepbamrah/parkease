package com.example.parkease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    AppCompatButton login;
    EditText email,password;
    TextView signup;
    private FirebaseAuth mAuth;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        password= findViewById(R.id.password);
        signup=findViewById(R.id.sign);
        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                if(mail.equals("singhtarundeep33@icloud.com")){
                    if(!mail.equals("")&& !pass.equals("")){
                        AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
                        View view1 = LayoutInflater.from(Login.this).inflate(R.layout.loadingdialog,null);
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
                        mAuth.signInWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(Login.this, OwnerActivity.class);
                                            startActivity(i);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Login.this,task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                    }
                    else{
                        Toast.makeText(Login.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    if(!mail.equals("")&& !pass.equals("")){
                        AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
                        View view1 = LayoutInflater.from(Login.this).inflate(R.layout.loadingdialog,null);
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
                        mAuth.signInWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(Login.this, Homepage.class);
                                            startActivity(i);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Login.this,task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                    }
                    else{
                        Toast.makeText(Login.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Login.this,Signup.class);
                startActivity(i);
            }
        });
    }
}