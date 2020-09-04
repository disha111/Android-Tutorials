package com.example.tutorial05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Display extends AppCompatActivity {
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        display = findViewById(R.id.wel_display);

        String Fname,Lname,Email,Pass,Gender,City;
        Boolean Status;
        Intent intent = getIntent();
        Fname = intent.getStringExtra("fname");
        Lname = intent.getStringExtra("lname");
        Email = intent.getStringExtra("email");
        Pass = intent.getStringExtra("password");
        Gender = intent.getStringExtra("gender");
        City = intent.getStringExtra("city");
        Status = intent.getBooleanExtra("status",false);
        String Status_val = "Inactive";
        if (Status) {
            Status_val = "Active";
        }
            display.setText("First Name : " + Fname+"\n"+"Last Name : " + Lname+"\n"+"Username : " + Email+"\n"+"Password : " + Pass+"\n"+"City : " + City+"\n"+"Gender : " + Gender+"\n"+"Status : "+Status_val);
    }
}