package com.example.tutorial05;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import classes.MyDatabaseHelper;

public class Signup extends AppCompatActivity {
    EditText fname,lname,email,password,phone;
    RadioGroup gender;
    RadioButton Gender;
    CheckBox checkBox;
    Button signup;
    Spinner city;
    Switch branch;
    //*****************"Tutorial 07***********************
    MyDatabaseHelper mydb;
    //*****************"Tutorial 07"***********************
    CountryCodePicker ccp;
    String numWithCode;

    int editno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editno = getIntent().getIntExtra("edit",3);

        fname = findViewById(R.id.signup_fname);
        lname = findViewById(R.id.signup_lname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        gender = findViewById(R.id.signup_gender);
        branch = findViewById(R.id.signup_branch);
        checkBox = findViewById(R.id.signup_checkbox);
        city = findViewById(R.id.signup_spinner);
        phone = findViewById(R.id.signup_phone);
        signup = findViewById(R.id.submit);

        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                numWithCode = ccp.getSelectedCountryCodeWithPlus();
            }
        });

//************************** Edit Value set in controls ********************************************
        if(editno==1){
            RadioButton male = findViewById(R.id.signup_btn1);
            RadioButton female = findViewById(R.id.signup_btn2);

            fname.setText(getIntent().getStringExtra("FirstName"));
            lname.setText(getIntent().getStringExtra("LastName"));
            email.setText(getIntent().getStringExtra("Email"));
            password.setText(getIntent().getStringExtra("Password"));
            String Phone_val = getIntent().getStringExtra("Phone");
            int numCode = Integer.parseInt(Phone_val.substring(1,3));
            ccp.setCountryForPhoneCode(numCode);
            phone.setText(Phone_val.replace("+91 ",""));
            if(!getIntent().getStringExtra("Branch").equals("Other")){
                branch.setChecked(true);
            }
            if(getIntent().getStringExtra("Gender").equals("Female")){
                female.setChecked(true);
            }
            else{
                male.setChecked(true);
            }
            String city_select = getIntent().getStringExtra("City");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.city_names,android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(adapter);
            if(city_select != null){
                int spinnerPos = adapter.getPosition(city_select);
                city.setSelection(spinnerPos);
            }
            signup.setText("Update");
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N_MR1)
            @Override
            public void onClick(View v) {
                Boolean Fname = fname.getText().toString().trim().isEmpty();
                Boolean Lname = lname.getText().toString().trim().isEmpty();
                String Email = email.getText().toString().trim();
                Boolean Email_val = email.getText().toString().trim().isEmpty();
                String Password = password.getText().toString().trim();
                String Phone = numWithCode + " " + phone.getText().toString().trim();
                Boolean Password_val = password.getText().toString().trim().isEmpty();
                Boolean Branch = branch.isChecked();
                int id = gender.getCheckedRadioButtonId();
                String set_city = city.getSelectedItem().toString();

                if(!Fname && !Lname && !Email_val && !Password_val && Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length()>=8 && !set_city.equals("Select Your City...") && Phone.length()==14){
                    Gender = findViewById(id);
                    //*****************"Tutorial 07"***********************
                    mydb = new MyDatabaseHelper(Signup.this);
                    //************************** Insert Query ******************************
                    if(editno==0) {
                        Boolean res = mydb.reg_insert(fname.getText().toString().trim(), lname.getText().toString().trim(), Email, Branch, Password, Gender.getText().toString(), set_city, Phone);
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        if (res) {
                            Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            intent.putExtra("user_id", Email);
                            intent.putExtra("pass", Password);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //************************ Update query ***************************
                    else{
                        String old_email = getIntent().getStringExtra("Email");
                        Boolean res = mydb.reg_update(fname.getText().toString().trim(), lname.getText().toString().trim(), Email, Branch, Password, Gender.getText().toString(), set_city, Phone,old_email);
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        if (res) {
                            Toast.makeText(Signup.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            intent.putExtra("user_id", Email);
                            intent.putExtra("pass", Password);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //*****************"Tutorial 07"***********************
                }
                else {
                    if(Fname){
                        fname.setError("Name is Require");
                    }
                    if(Lname){
                        lname.setError("Surname is Require");
                    }
                    if(Email_val){
                        email.setError("Email is Require");
                    }
                    else{
                        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                            email.setError("Email is Invalid");
                        }
                    }
                    if(Password_val){
                        password.setError("Password is Invalid");
                    }
                    else{
                        if(Password.length()<8) {
                            password.setError("Password to short");
                        }
                    }
                    if(Phone.isEmpty()){
                        phone.setError("Phone No is Invalid");
                    }
                    else{
                        if(Phone.length()!=14) {
                            phone.setError("Phone No must be 10 Degits");
                        }
                    }
                    if(set_city.equals("Select Your City...")){
                        TextView textView = (TextView) city.getSelectedView();
                        textView.setError("Selected Item is Invalid");
                    }
                    Toast.makeText(Signup.this,"Invalid Credentials...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login_link(View view) {
        startActivity(new Intent(Signup.this,MainActivity.class));
        finish();
    }

}