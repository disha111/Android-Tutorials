package com.example.tutorial05;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericDeclaration;

public class Display extends AppCompatActivity {
    //*******************"Tutorial 08"*******************
    TextView onlineDataView; //onlineDataView for Tutorial 10
    MyDatabaseHelper myDB;
    String userdata = "", valUserData = ""; //valUserData For Tutorial 10
    //*******************"Tutorial 08"*******************
    int temp;

    //****************************** Offline data display controls assign here.... *****************************************
    TextView OfflineUserName,OfflineUserEmail,OfflineUserPhone,OfflineUserCity,OfflineUserBranch,OfflineUserGender,OfflineProfileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //**************************** Offline data display controls Connection here.... ****************************************
        OfflineProfileName = findViewById(R.id.OfflineProfileName);
        OfflineUserName = findViewById(R.id.OfflineUserName);
        OfflineUserEmail = findViewById(R.id.OfflineDisplayEmail);
        OfflineUserPhone = findViewById(R.id.OfflineDisplayPhone);
        OfflineUserCity = findViewById(R.id.OfflineDisplayCity);
        OfflineUserBranch = findViewById(R.id.OfflineDisplayBranch);
        OfflineUserGender = findViewById(R.id.OfflineUserGender);


        //*******************"Tutorial 08"*******************
        Intent intent = getIntent();
        onlineDataView = findViewById(R.id.onlinedata_display);
        temp = intent.getIntExtra("temp",0);
        if(temp == 2){
            myDB = new MyDatabaseHelper(this);
            String username = intent.getStringExtra("username");
            Toast.makeText(Display.this, username, Toast.LENGTH_SHORT).show();
            Cursor cursor = myDB.getPartUserData(username);
            cursor.moveToFirst();
            OfflineProfileName.setText(cursor.getString(1).charAt(0)+""+cursor.getString(2).charAt(0));
            OfflineUserName.setText(cursor.getString(1) +" "+ cursor.getString(2));
            OfflineUserEmail.setText("Email \n"+cursor.getString(3));
            OfflineUserGender.setText(cursor.getString(5));
            OfflineUserBranch.setText("Branch \n"+cursor.getString(6));
            OfflineUserCity.setText("City \n"+cursor.getString(7));
            OfflineUserPhone.setText("Phone\n"+cursor.getString(8));
//            display.setText(userdata);
            onlineDataView.setVisibility(View.GONE);
            setTitle(cursor.getString(1)+" Details");
            //*******************"Tutorial 08"*******************
        }
        else{
            //*******************"Tutorial 10"*******************

            Toast.makeText(this, "Online data", Toast.LENGTH_SHORT).show();
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            int position = intent.getIntExtra("userPosition", 0);
            try {
                JSONObject object = MyUtil.jsonArray.getJSONObject(position);
                valUserData += "Id : " + object.getString("id");
                valUserData += "\nName : " + object.getString("name");
                valUserData += "\nUsername : " + object.getString("username");
                valUserData += "\nEmail : " + object.getString("email");
                JSONObject addressObj = object.getJSONObject("address");
                valUserData += "\nAddress : " +
                        addressObj.getString("street") + ", " +
                        addressObj.getString("suite") + ", " +
                        addressObj.getString("city") + ", " +
                        addressObj.getString("zipcode");
                valUserData += "\nPhone : " + object.getString("phone");
                valUserData += "\nWebsite : " + object.getString("website");
                JSONObject companyObj = object.getJSONObject("company");
                valUserData += "\nAddress : " +
                        companyObj.getString("name") + ", " +
                        companyObj.getString("catchPhrase") + ", " +
                        companyObj.getString("bs");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onlineDataView.setText(valUserData);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Welcome.class);
        if(temp == 4){
            intent.putExtra("temp",3);
        }else {
            intent.putExtra("temp",1);
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}