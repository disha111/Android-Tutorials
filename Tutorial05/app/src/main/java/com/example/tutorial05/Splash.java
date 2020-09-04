package com.example.tutorial05;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

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

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}