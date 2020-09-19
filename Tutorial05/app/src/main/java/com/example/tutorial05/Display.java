package com.example.tutorial05;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Display extends AppCompatActivity {
    //*******************"Tutorial 08"*******************
    TextView display;
    MyDatabaseHelper myDB;
    String userdata = "";
    //*******************"Tutorial 08"*******************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //*******************"Tutorial 08"*******************
        display = findViewById(R.id.wel_display);
        myDB = new MyDatabaseHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Toast.makeText(Display.this, username, Toast.LENGTH_SHORT).show();
        Cursor cursor = myDB.getPartUserData(username);
        cursor.moveToFirst();
        userdata += cursor.getString(1);
        userdata += "\n" + cursor.getString(2);
        userdata += "\n" + cursor.getString(3);
        userdata += "\n" + cursor.getString(4);
        userdata += "\n" + cursor.getString(5);
        userdata += "\n" + cursor.getString(6);
        userdata += "\n" + cursor.getString(7);
        display.setText(userdata);
        //*******************"Tutorial 08"*******************
    }
}