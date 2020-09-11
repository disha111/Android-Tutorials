package com.example.tutorial05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email,pass;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //*****************"Tutorial 07"***********************
    MyDatabaseHelper myDB;
    Cursor cursor;
    //*****************"Tutorial 07"***********************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.submit);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
         //Tut 6
        preferences = getSharedPreferences("Session",MODE_PRIVATE);
        editor = preferences.edit();
        // tut 6   get value from SharedPreferences
        String pref_email = preferences.getString("email","");
        if(!pref_email.equals("")){
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            startActivity(intent);
            finish();
        }
        //***************Tutorial07**************
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String user_pass = intent.getStringExtra("pass");
        email.setText(user_id);
        pass.setText(user_pass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_val = email.getText().toString();
                String password = pass.getText().toString();
                int count = 0;
                //*****************"Tutorial 07"***********************
                myDB = new MyDatabaseHelper(MainActivity.this);
                cursor = myDB.checkLogin(email_val, password);
                if(cursor.getCount()!=0){
                    Toast.makeText(MainActivity.this,"You have Authenticated Successfully", Toast.LENGTH_SHORT).show();
                    //*****************"Tutorial 07"***********************
                    Intent intent = new Intent(MainActivity.this, Welcome.class);
                   // tut 6   add value in SharedPreferences
                    editor.putString("email",email.getText().toString().trim());
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this,"Username or Password is incorrect",Toast.LENGTH_LONG).show();
//                    if(!Patterns.EMAIL_ADDRESS.matcher(email_val).matches()){
//                        email.setError("Email is Invalid");
//                    }
//                    if(password.length()<8){
//                        pass.setError("Password Invalid");
//                    }
                }
            }
        });
    }
    public void signup(View view) {
        startActivity(new Intent(MainActivity.this,Signup.class));

    }
}