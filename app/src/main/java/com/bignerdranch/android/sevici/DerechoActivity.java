package com.bignerdranch.android.sevici;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class DerechoActivity extends AppCompatActivity {

    Button mAcceptButton;
    static final String KEY_IS_FIRST_TIME =  "com.<your_app_name>.first_time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean firstTime = getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("firstTime",true);
        if (firstTime) {
            setContentView(R.layout.activity_derecho);
            mAcceptButton = (Button) findViewById(R.id.button5);
            mAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent().setClass(DerechoActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            });
            getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("firstTime",false).commit();
        } else {
            Intent intent = new Intent(DerechoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

