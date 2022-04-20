package com.example.android.drivedocupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Seconds till when the splash screen will be shown before redirecting to Home page.
    private static int SPLASH_SCREEN_TIME = 5000;

    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView appName, line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation on the Splash Screen
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Assigning Animations to logo and appName
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        line = findViewById(R.id.line);

        logo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
        line.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}