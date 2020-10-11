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

public class Display extends AppCompatActivity {
    //*******************"Tutorial 08"*******************
    TextView onlineDataView; //onlineDataView for Tutorial 10
    MyDatabaseHelper myDB;
    String userdata = "", valUserData = ""; //valUserData For Tutorial 10
    String Address = "";
    //*******************"Tutorial 08"*******************
    int temp;

    //****************************** Offline data display controls assign here.... *****************************************
    TextView OfflineUserName,OfflineUserEmail,OfflineUserPhone,OfflineUserCity,OfflineUserBranch,OfflineUserGender,OfflineProfileName,OffineBranchLable,OfflineCityLable;
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
            Cursor cursor = myDB.getPartUserData(username);
            cursor.moveToFirst();
            OfflineProfileName.setText(cursor.getString(1).charAt(0)+""+cursor.getString(2).charAt(0));
            OfflineUserName.setText(cursor.getString(1) +" "+ cursor.getString(2));
            OfflineUserEmail.setText(cursor.getString(3));
            OfflineUserGender.setText(cursor.getString(6));
            OfflineUserBranch.setText(cursor.getString(5));
            OfflineUserCity.setText(cursor.getString(7));
            OfflineUserPhone.setText(cursor.getString(8));
//            display.setText(userdata);
            onlineDataView.setVisibility(View.GONE);
            setTitle(cursor.getString(1)+" Details");
//            Toast.makeText(Display.this, ""+cursor.getString(1), Toast.LENGTH_SHORT).show();
            //*******************"Tutorial 08"*******************
        }
        else{
            //*******************"Tutorial 10"*******************
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            int position = intent.getIntExtra("userPosition", 0);
            try {

                JSONObject object = MyUtil.jsonArray.getJSONObject(position);
//                Toast.makeText(this, ""+object.getString("username"), Toast.LENGTH_SHORT).show();
                setTitle(object.getString("username")+" Details");
                OfflineProfileName.setText(object.getString("id"));
                OfflineUserName.setText(object.getString("name"));
                OfflineUserEmail.setText(object.getString("email"));
                OfflineUserGender.setText(object.getString("username"));
                JSONObject addressObj = object.getJSONObject("address");
                OfflineUserCity.setText(addressObj.getString("street") + ", " +
                        addressObj.getString("suite") + ", " +
                        addressObj.getString("city") + ", " +
                        addressObj.getString("zipcode"));
                OfflineUserPhone.setText(object.getString("phone"));
                onlineDataView.setText(object.getString("website"));
                JSONObject companyObj = object.getJSONObject("company");
                OfflineUserBranch.setText(companyObj.getString("name") + ", " +
                        companyObj.getString("catchPhrase") + ", " +
                        companyObj.getString("bs"));
                OfflineCityLable = findViewById(R.id.CityLable);
                OfflineCityLable.setText("Address");
                OffineBranchLable = findViewById(R.id.BranchLable);
                OffineBranchLable.setText("Company Address");
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