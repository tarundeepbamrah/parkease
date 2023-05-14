package com.example.parkease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    FirebaseDatabase dbref = FirebaseDatabase.getInstance("https://login-154fc-default-rtdb.firebaseio.com/");

    EditText name,email,phone,password,repass;
    Button createacc;
    TextView login;
    //authentication
    private FirebaseAuth mAuth;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        repass=findViewById(R.id.repass);
        createacc=findViewById(R.id.createacc);
        login=findViewById(R.id.log);

        mAuth=FirebaseAuth.getInstance();
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String confirmpass= repass.getText().toString();
                String mobile = phone.getText().toString();
                //mAuth = FirebaseAuth.getInstance();


                if(pass.equals(confirmpass) && !uname.equals("")&&!mail.equals("")&&!pass.equals("")&&!confirmpass.equals("")&&!mobile.equals("")){
                    AlertDialog.Builder builder= new AlertDialog.Builder(Signup.this);
                    View view1 = LayoutInflater.from(Signup.this).inflate(R.layout.loadingdialog,null);
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

                    mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                UserDetails user = new UserDetails(uname,mail, mobile, pass);

                                String id = task.getResult().getUser().getUid();
                                dbref.getReference("Users").child(id).setValue(user);
                                dialog.dismiss();
                                Toast.makeText(Signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(Signup.this, Login.class);
                                startActivity(i);


                            } else {
                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                else{
                    Toast.makeText(Signup.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                }

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Signup.this, Login.class);
                startActivity(i);

            }
        });

    }
}