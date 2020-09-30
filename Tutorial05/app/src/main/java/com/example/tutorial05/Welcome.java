package com.example.tutorial05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //*******************"Tutorial 08"*******************
    ListView lstData;
    MyDatabaseHelper myDB;
    ArrayAdapter<String> adapter;
    //    String data[]={"XYZ","ABC"};
    //*******************"Tutorial 08"*******************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // tut 6   get value from SharedPreferences
        preferences = getSharedPreferences("Session",MODE_PRIVATE);
        editor = preferences.edit();

        //*******************"Tutorial 08"*******************
        lstData = findViewById(R.id.lstDataView);
        myDB = new MyDatabaseHelper(this);

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
//                data,
                myDB.getUserList()
        ){@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the Item from ListView
            View view = super.getView(position, convertView, parent);

            // Initialize a TextView for ListView each Item
            TextView tv = (TextView) view.findViewById(android.R.id.text1);

            // Set the text color of TextView (ListView Item)
            tv.setTextColor(Color.RED);

            // Generate ListView Item using TextView
            return view;
        }
        };
        lstData.setAdapter(adapter);
        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String username = ((TextView)view).getText().toString();
                Intent intent = new Intent(Welcome.this,Display.class);
                intent.putExtra("username",username);
                startActivity(intent);

            }
        });
        //*******************"Tutorial 08"*******************
    }

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
            //*******************"Tutorial 09"*******************
            case R.id.file_menu:
                startActivity(new Intent(Welcome.this,FileManagement.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}