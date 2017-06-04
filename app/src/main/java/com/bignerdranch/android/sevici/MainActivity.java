package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

        insertarDatosBD();



    }

    public List<Estacion> generateEstacionesInfoCSV()  {
        List<Estacion> res = new ArrayList<Estacion>();

        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for (String[] e : stations) {
            Estacion estacion = new Estacion();
            estacion.setName(e[4]);
            estacion.setNumero(Integer.parseInt(e[3]));
            estacion.setLat(Double.parseDouble(e[6]));
            estacion.setLen(Double.parseDouble(e[7]));
            res.add(estacion);
        }
        return res;

    }

    private void insertarDatosBD() {

        BDSevici estaciones = new BDSevici(this,"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();


        //Insertamos primero los datos de las estaciones:

       List<Estacion> estacionesCSV = generateEstacionesInfoCSV();

        if(db!=null){
            db.execSQL("DELETE FROM Estaciones");
            for(Estacion e:estacionesCSV){
                db.execSQL("INSERT INTO Estaciones (nombre, numero) VALUES ('"+ e.getName() + "', '" +e.getNumero()+"')");
            }
            db.close();
        }
    }



}
