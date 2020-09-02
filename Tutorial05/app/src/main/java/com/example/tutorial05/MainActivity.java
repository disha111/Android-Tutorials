package com.example.tutorial05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email,pass;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_val = email.getText().toString();
                String password = pass.getText().toString();
                int count = 0;
                if(Patterns.EMAIL_ADDRESS.matcher(email_val).matches() && password.length()>=8){
                    Intent intent = new Intent(MainActivity.this, Welcome.class);
//                    intent.putExtra("which","login");
//                    intent.putExtra("email",email.getText().toString().trim());
//                    intent.putExtra("password",pass.getText().toString().trim());

                   // tut 6   add value in SharedPreferences
                    editor.putString("email",email.getText().toString().trim());
                    editor.apply();
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
                else {
                    if(!Patterns.EMAIL_ADDRESS.matcher(email_val).matches()){
                        email.setError("Email is Invalid");
                    }
                    if(password.length()<8){
                        pass.setError("Password Invalid");
                    }
                }
            }
        });
    }
    public void signup(View view) {
        startActivity(new Intent(MainActivity.this,Signup.class));

    }
}