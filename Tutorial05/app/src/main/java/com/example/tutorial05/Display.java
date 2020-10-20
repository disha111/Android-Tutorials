package com.example.tutorial05;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import classes.MyDatabaseHelper;
import classes.MyUtil;

public class Display extends AppCompatActivity {
    //*******************"Tutorial 08"*******************
    TextView onlineDataView; //onlineDataView for Tutorial 10
    MyDatabaseHelper myDB;
    String userdata = "", valUserData = ""; //valUserData For Tutorial 10
    String Address = "";
    String fname,lname,pass;
    android.app.AlertDialog.Builder builder;
    MyDatabaseHelper mydb;
    //*******************"Tutorial 08"*******************
    int temp;
    //***************************** Sharedpreferences... ****************************************
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //****************************** Offline data display controls assign here.... *****************************************
    TextView OfflineUserName,OfflineUserEmail,OfflineUserPhone,OfflineUserCity,OfflineUserBranch,OfflineUserGender,OfflineProfileName,OffineBranchLable,OfflineCityLable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //*********************** Assign value... ****************************
        preferences = getSharedPreferences("Session",MODE_PRIVATE);
        editor = preferences.edit();

        if(preferences.getString("email","").equals("")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        myDB = new MyDatabaseHelper(this);

        //**************************** Offline data display controls Connection here.... ****************************************
        OfflineProfileName = findViewById(R.id.OfflineProfileName);
        OfflineUserName = findViewById(R.id.OfflineUserName);
        OfflineUserEmail = findViewById(R.id.OfflineDisplayEmail);
        OfflineUserPhone = findViewById(R.id.OfflineDisplayPhone);
        OfflineUserCity = findViewById(R.id.OfflineDisplayCity);
        OfflineUserBranch = findViewById(R.id.OfflineDisplayBranch);
        OfflineUserGender = findViewById(R.id.OfflineUserGender);

        //*******************  Tutorial13 *********************


        TextView call = findViewById(R.id.OfflineDisplayCall);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iscallPermission()){
                    Toast.makeText(Display.this, "calling", Toast.LENGTH_SHORT).show();
                    makecall();
                }
            }
        });

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
            fname = cursor.getString(1);
            lname = cursor.getString(2);
            pass = cursor.getString(4);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        menu.findItem(R.id.abt_menu).setVisible(false);
        menu.findItem(R.id.file_menu).setVisible(false);
        menu.findItem(R.id.asyncTask).setVisible(false);
        menu.findItem(R.id.recyclerView).setVisible(false);
        menu.findItem(R.id.lgt_menu).setVisible(false);
        menu.findItem(R.id.callAndSMS).setVisible(false);
        menu.findItem(R.id.edit_menu).setVisible(true);
        menu.findItem(R.id.delete_menu).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
    // Tut 6 Select menu item..

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final String username = getIntent().getStringExtra("username");

        builder = new android.app.AlertDialog.Builder(this,R.style.DialogTheme);
        builder.setTitle("Confirm Delete?")
                .setMessage("Do you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean res = myDB.reg_delete(username);
                        if(res){
                            Toast.makeText(Display.this, "Deleted Successfull...", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Display.this, "Please try again...", Toast.LENGTH_SHORT).show();
                        }
                        Intent retryIntent = new Intent(Display.this, Welcome.class);
                        retryIntent.putExtra("temp", 1);
                        startActivity(retryIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Intent = new Intent(Display.this, Display.class);

                        Intent.putExtra("username",username);
                        Intent.putExtra("temp",2);
                        startActivity(Intent);
                        finish();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        switch (item.getItemId()){
            //*******************"Tutorial 09"*******************
            case R.id.delete_menu:
                if(preferences.getString("email","").equals(username)){
                    editor.putString("email","");
                    editor.commit();
                    android.app.AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                }
                android.app.AlertDialog errorDialog = builder.create();
                errorDialog.show();
                break;
            case R.id.edit_menu:
                Intent intent = new Intent(Display.this, Signup.class);
                intent.putExtra("edit",1);
                intent.putExtra("FirstName",fname);
                intent.putExtra("LastName",lname);
                intent.putExtra("Password",pass);
                intent.putExtra("Email",OfflineUserEmail.getText().toString());
                intent.putExtra("Phone",OfflineUserPhone.getText().toString());
                intent.putExtra("City",OfflineUserCity.getText().toString());
                intent.putExtra("Gender",OfflineUserGender.getText().toString());
                intent.putExtra("Branch",OfflineUserBranch.getText().toString());
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
//************************ Tutorial13 ************************
private boolean iscallPermission() {
    if(Build.VERSION.SDK_INT >= 23){
        if(checkSelfPermission(Manifest.permission.CALL_PHONE) ==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            ActivityCompat.requestPermissions(Display.this,new String[]{Manifest.permission.CALL_PHONE},11);
            return false;
        }
    }
    else{
        return true;
    }
}

    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 11:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    makecall();
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void makecall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+OfflineUserPhone.getText().toString()));
        startActivity(intent);
    }
//************************ Tutorial13 ************************
}