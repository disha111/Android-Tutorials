package com.example.tutorial05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // tut 6   get value from SharedPreferences
        preferences = getSharedPreferences("Session",MODE_PRIVATE);
        editor = preferences.edit();
        welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome, "+preferences.getString("email",""));
    }

//    public void logout(View view) {
//        // tut 6   get value from SharedPreferences
//        editor.remove("email");
////        editor.apply();
//        editor.commit();
//        startActivity(new Intent(Welcome.this,MainActivity.class));
//        finish();
//    }

    // Tut 6  Menu Linked
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Tut 6 Select menu item..
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.abt_menu:
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lgt_menu:
                editor.remove("email");
                editor.commit();
                startActivity(new Intent(Welcome.this,MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}