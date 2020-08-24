package com.example.tutorial04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView splash = findViewById(R.id.tut04);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        splash.startAnimation(animation);


        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

                super.run();
            }
        };
        timer.start();
    }
}