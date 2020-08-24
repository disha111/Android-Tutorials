package com.example.tutorial04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        welcome = findViewById(R.id.welcome);
        int unicode = 0x1F64F;
        String emoji = getEmojiByUnicode(unicode);
        String text = "Welcome! ";
        welcome.setText(text + emoji);

        ImageView splash = findViewById(R.id.Splash_id);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        splash.startAnimation(animation);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
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
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}