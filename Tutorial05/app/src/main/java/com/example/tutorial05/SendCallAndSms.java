package com.example.tutorial05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class SendCallAndSms extends AppCompatActivity {

    EditText phone,text;
    String num;
    Boolean checkText;
    CountryCodePicker ccp;
    String numWithCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_call_and_sms);
        setTitle("Call And Sms");
        phone = findViewById(R.id.callPhoneNumber);
        text = findViewById(R.id.smsText);
//        num = numWithCode + " " + phone.getText().toString().trim();
        checkText = text.getText().toString().isEmpty();
        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                numWithCode = ccp.getSelectedCountryCodeWithPlus();
            }
        });


    }

    public void makecall(View view) {
        if(phone.getText().toString().length()!=10){
            phone.setError("Enter Valid Number");
        }
        else{
            if(iscallPermission()){
                Toast.makeText(SendCallAndSms.this, "calling....", Toast.LENGTH_SHORT).show();
                call();
            }
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.format("tel:%s %s", numWithCode, phone.getText().toString().trim())));
        startActivity(intent);
    }

    private boolean iscallPermission() {
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                return true;
            }
            else{
                ActivityCompat.requestPermissions(SendCallAndSms.this,new String[]{Manifest.permission.CALL_PHONE},11);
                return false;
            }
        }
        else{
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 11:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

                break;
            case 21:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sms();
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    public void sendsms(View view) {
        if(phone.getText().toString().length()!=10){
            phone.setError("Enter Valid Number");
        }
        if(text.getText().toString().isEmpty()){
            text.setError("Enter Text to send");
        }
        if(!text.getText().toString().isEmpty() && phone.getText().toString().length()==10 ){
            if(issmsPermission()){
                Toast.makeText(SendCallAndSms.this, "sending....", Toast.LENGTH_SHORT).show();
                sms();
            }
        }
    }

    private void sms() {
        String phonenum = numWithCode+" "+phone.getText().toString();
        String text_val = text.getText().toString().trim();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phonenum,null,text_val,null,null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    private boolean issmsPermission() {
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                return true;
            }
            else{
                ActivityCompat.requestPermissions(SendCallAndSms.this,new String[]{Manifest.permission.SEND_SMS},21);
                return false;
            }
        }
        else{
            return true;
        }
    }
}