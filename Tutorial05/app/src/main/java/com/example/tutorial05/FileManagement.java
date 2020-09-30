package com.example.tutorial05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManagement extends AppCompatActivity {

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
    }

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