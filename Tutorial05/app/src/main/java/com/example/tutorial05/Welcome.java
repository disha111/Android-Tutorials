package com.example.tutorial05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Welcome extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mState;
    //*******************"Tutorial 08"*******************
    ListView lstData;
    MyDatabaseHelper myDB;
    ArrayAdapter<String> adapter;
    //    String data[]={"XYZ","ABC"};
    //*******************"Tutorial 08"*******************
    //*******************"Tutorial 10"*******************
    CustomAdapter onlineDataAdapter;
    ListView onlineUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // tut 6   get value from SharedPreferences
        preferences = getSharedPreferences("Session",MODE_PRIVATE);
        editor = preferences.edit();
        lstData = findViewById(R.id.lstDataView);
        onlineUsersList = findViewById(R.id.onlineUsersView);


        int temp = getIntent().getIntExtra("temp",0);

        if(temp == 1){
//            editor.putString("onlinedata", "off");
//            editor.commit();
            //*******************"Tutorial 08"*******************
            onlineUsersList.setVisibility(View.GONE);
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
                    intent.putExtra("temp",2);
                    startActivity(intent);

                }
            });
            //*******************"Tutorial 08"*******************
        }
        else{
//            editor.putString("onlinedata", "on");
//            editor.commit();
            mState = "HIDE_MENU";
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            lstData.setVisibility(View.GONE);
            setTitle("Online Users");
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
            onlineUsersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Welcome.this, Display.class);
                    intent.putExtra("userPosition",i);
                    intent.putExtra("temp",4);
                    Toast.makeText(Welcome.this, "on in welcome", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            });
            new MyAsyncTask().execute();
        }



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(),Welcome.class);
        backIntent.putExtra("temp",1);
        startActivity(backIntent);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    // Tut 6  Menu Linked
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        if(mState == "HIDE_MENU"){
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
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
                break;
            case R.id.asyncTask:
                Intent intent = new Intent(Welcome.this, Welcome.class);
                intent.putExtra("temp",3);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAsyncTask extends AsyncTask {

        ProgressDialog dialog;
        StringBuilder strb;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Welcome.this);
            dialog.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                URL url = new URL(MyUtil.URL_USERS);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(reader);
                strb = new StringBuilder();
                String onlineData = "";
                while((onlineData = br.readLine())!=null){
                    strb.append(onlineData);
                }
                Log.i("jsonString",strb.toString());
                MyUtil.jsonArray = new JSONArray(strb.toString());

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            onlineDataAdapter = new CustomAdapter(Welcome.this,MyUtil.jsonArray);
            onlineUsersList.setAdapter(onlineDataAdapter);
            if(dialog.isShowing()){dialog.dismiss();}
        }
    }
}