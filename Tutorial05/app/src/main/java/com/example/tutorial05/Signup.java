package com.example.tutorial05;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Native;

public class Signup extends AppCompatActivity {
    EditText fname,lname,email,password;
    RadioGroup gender;
    RadioButton Gender;
//    RadioButton last;
    CheckBox checkBox;
    Button signup;
    Spinner city;
    Switch branch;
//    TextView ferror,lerror,eerror,perror;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = findViewById(R.id.signup_fname);
        lname = findViewById(R.id.signup_lname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        gender = findViewById(R.id.signup_gender);
        branch = findViewById(R.id.signup_branch);
        checkBox = findViewById(R.id.signup_checkbox);
        city = findViewById(R.id.signup_spinner);
//        last = findViewById(R.id.signup_btn2);
        signup = findViewById(R.id.submit);

//        ferror = findViewById(R.id.fname_error);
//        lerror = findViewById(R.id.lname_error);
//        eerror = findViewById(R.id.email_error);
//        perror = findViewById(R.id.password_error);

        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N_MR1)
            @Override
            public void onClick(View v) {
                Boolean Fname = fname.getText().toString().trim().isEmpty();
                Boolean Lname = lname.getText().toString().trim().isEmpty();
                String Email = email.getText().toString().trim();
                Boolean Email_val = email.getText().toString().trim().isEmpty();
                String Password = password.getText().toString().trim();
                Boolean Password_val = password.getText().toString().trim().isEmpty();
                Boolean Branch = branch.isChecked();
                int id = gender.getCheckedRadioButtonId();
                String set_city = city.getSelectedItem().toString();

                if(!Fname && !Lname && !Email_val && !Password_val && Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length()>=8 && !set_city.equals("Select Your City...")){
//                    fname.setBackgroundResource(R.drawable.valid_border);
//                    lname.setBackgroundResource(R.drawable.valid_border);
//                    email.setBackgroundResource(R.drawable.valid_border);
//                    password.setBackgroundResource(R.drawable.valid_border);
//                    branch.setBackgroundResource(R.drawable.valid_border);
//                    gender.setBackgroundResource(R.drawable.valid_border);
//                    checkBox.setBackgroundResource(R.drawable.valid_border);
                    Gender = findViewById(id);
                    Intent intent = new Intent(Signup.this, Display.class);
//                    intent.putExtra("which","signup");
                    intent.putExtra("fname",fname.getText().toString().trim());
                    intent.putExtra("lname",lname.getText().toString().trim());
                    intent.putExtra("email",email.getText().toString().trim());
                    intent.putExtra("password",password.getText().toString().trim());
                    intent.putExtra("gender",Gender.getText().toString());
                    intent.putExtra("branch",branch.isChecked());
                    intent.putExtra("city",city.getSelectedItem().toString());
                    intent.putExtra("status",checkBox.isChecked());
                    Toast.makeText(Signup.this,"Valid Credentials...",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    if(Fname){
//                        ferror.setText("Name is Required");
//                        ferror.setVisibility(View.VISIBLE);
                        fname.setError("Name is Require");
//                        fname.setBackgroundResource(R.drawable.invalid_border);
                    }
//                    else{
//                        ferror.setVisibility(View.GONE);
//                        fname.setBackgroundResource(R.drawable.valid_border);
//                    }
                    if(Lname){
                        lname.setError("Surname is Require");
//                        lerror.setText("Surname is Required");
//                        lerror.setVisibility(View.VISIBLE);
//                        lname.setBackgroundResource(R.drawable.invalid_border);
                    }
//                    else{
//                        lerror.setVisibility(View.GONE);
//                        lname.setBackgroundResource(R.drawable.valid_border);
//                    }
                    if(Email_val){
                        email.setError("Email is Require");
//                        eerror.setText("Email is Required");
//                        eerror.setVisibility(View.VISIBLE);
//                        email.setBackgroundResource(R.drawable.invalid_border);
                    }
                    else{
                        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                            email.setError("Email is Invalid");
//                            eerror.setVisibility(View.GONE);
//                            email.setBackgroundResource(R.drawable.valid_border);
                        }
//                        else{
//                            eerror.setText("Email is Invalid");
//                            eerror.setVisibility(View.VISIBLE);
//                            email.setBackgroundResource(R.drawable.invalid_border);
//                        }
                    }
                    if(Password_val){
                        password.setError("Password is Invalid");
//                        perror.setText("Password is Required");
//                        perror.setVisibility(View.VISIBLE);
//                        password.setBackgroundResource(R.drawable.invalid_border);
                    }
                    else{
                        if(Password.length()<8) {
                            password.setError("Password to short");
//                            perror.setText("Password to short");
//                            perror.setVisibility(View.GONE);
//                            password.setBackgroundResource(R.drawable.valid_border);
                        }
//                        else{
//                            perror.setVisibility(View.VISIBLE);
//                            perror.setText("Password is too short");
//                            password.setBackgroundResource(R.drawable.invalid_border);
//                        }
                    }
//                    if(id==-1){
//                        last.setError("Select Gender");
//                    }
                    if(set_city.equals("Select Your City...")){
                        TextView textView = (TextView) city.getSelectedView();
                        textView.setError("Selected Item is Invalid");
                    }
//                    branch.setBackgroundResource(R.drawable.valid_border);
//                    gender.setBackgroundResource(R.drawable.valid_border);
//                    checkBox.setBackgroundResource(R.drawable.valid_border);
                    Toast.makeText(Signup.this,"Invalid Credentials...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}