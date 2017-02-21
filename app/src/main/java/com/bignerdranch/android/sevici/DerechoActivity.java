package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DerechoActivity extends AppCompatActivity {

    Button mAcceptButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derecho);
        mAcceptButton = (Button) findViewById(R.id.button5);
        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(DerechoActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
            }

}
