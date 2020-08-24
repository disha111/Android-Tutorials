package com.example.tutorial04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.submit);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_val = email.getText().toString();
                String password = pass.getText().toString();
                //String reg_email = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                int count = 0;
                if(Patterns.EMAIL_ADDRESS.matcher(email_val).matches() && password.length()>=8){
                    startActivity(new Intent(MainActivity.this, Welcome.class));
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this,"UserName or Password are incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}