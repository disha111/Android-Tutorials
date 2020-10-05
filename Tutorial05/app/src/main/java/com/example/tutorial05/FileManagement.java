package com.example.tutorial05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManagement extends AppCompatActivity {
//    //******"Extra session management (For setting menu for multiActivity)"*******
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;
//    String onlinedata;
    //    ********************TUTORIAL09***************************************************
    final String FILE_ASSETS = "data.json";
    final String FILE_INTERNAL = "data.txt";
    EditText editTextDataFile;
    TextView filesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_management);
        //    ********************TUTORIAL09***************************************************
        editTextDataFile = findViewById(R.id.editTextDataFile);
        filesView = findViewById(R.id.filesView);
        //******"Extra session management (For setting menu for multiActivity)"*******
//
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        //****************** preferences **********************
//        preferences = getSharedPreferences("Session",MODE_PRIVATE);
//        editor = preferences.edit();
//        onlinedata = preferences.getString("onlinedata","");
    }
    //******"Extra session management (For managing back button)*******
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent backIntent = new Intent(getApplicationContext(),Welcome.class);
//        //******** Tutorial 10 Back Arrow **************
//        if(onlinedata=="on"){
//            backIntent.putExtra("temp",3);
//        }
//        else{
//            backIntent.putExtra("temp",1);
//        }
//        startActivity(backIntent);
//        this.finish();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
    public void readfile(View view) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(FILE_INTERNAL);
            int c;
            String temp = "";
            while((c = fileInputStream.read())!= -1) {
                temp = temp + String.valueOf((char) c);
            }
            filesView.setText(temp);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writefile(View view) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_INTERNAL, Context.MODE_PRIVATE);
            String val = editTextDataFile.getText().toString();
            fileOutputStream.write(val.getBytes());
            fileOutputStream.close();
            editTextDataFile.setText("");
            Toast.makeText(this, "Data Successfully added", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void viewfile(View view) {

        try {
            InputStream inputStream = getAssets().open(FILE_ASSETS);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String temp = "";
            while ((temp=bufferedReader.readLine())!=null){
                stringBuilder.append(temp);
            }
            filesView.setText(stringBuilder.toString());
            inputStream.close();
            reader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}