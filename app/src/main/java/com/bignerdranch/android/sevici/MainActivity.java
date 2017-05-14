package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton mImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageButton = (ImageButton) findViewById(R.id.imageButton3);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(MainActivity.this, MapsActivity.class);
                startActivity(mainIntent);
            }
        });

        mImageButton = (ImageButton) findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(MainActivity.this, FavoriteActivity.class);
                startActivity(mainIntent);
            }
        });


        mImageButton = (ImageButton) findViewById(R.id.imageButton4);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(MainActivity.this, RutasSelectActivity.class);
                startActivity(mainIntent);
            }
        });

        mImageButton = (ImageButton) findViewById(R.id.imageButton2);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(MainActivity.this, TwitterActivity.class);
                startActivity(mainIntent);
            }
        });



        //Insertamos los datos en la bbdd

       // insertarDatosBD();



    }

  /*  private void insertarDatosBD() {

        BDSevici estaciones = new BDSevici(this,"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();



        //Insertamos primero los datos de las estaciones:

        List<Estacion> estacionesCSV = generateEstacionInfoXML();

        for(Estacion e: estacionesCSV){
            db.execSQL("INSERT INTO Estaciones (nombre, numero, borlib, bicidis, lat, lon)"+ "VALUES ('"+ e.getName() + "', '"+ e.getNumero() + "', '" +e.getFree()+"','" +e.getAvailable()+ "'," +
                    " "+e.getLat()+","+e.getLen()+")");
        }

        db.close();
    }*/




}
