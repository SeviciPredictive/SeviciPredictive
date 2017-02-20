package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.Window.FEATURE_NO_TITLE;


public class SplashScreen extends AppCompatActivity {

        private static final long SPLASH_SCREEN_DELAY = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_splash_screen);
            getSupportActionBar().hide();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent().setClass(SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(task,SPLASH_SCREEN_DELAY);
        }
    }

