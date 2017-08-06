package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                Intent mainIntent = new Intent().setClass(MainActivity.this, RutasEstacionesFavoritasActivity.class);
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

    private void insertarDatosBD() {
        BDEstaciones estaciones = new BDEstaciones(this,"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();
        int fav = 0;
        List<Estacion> lestaciones = parserJsonToEstacion();
        if(db!=null){

            db.execSQL("DELETE FROM Estaciones");

            for(Estacion e: lestaciones){

                db.execSQL("INSERT INTO Estaciones (numero, nombre, disponibles, libres, coordLat, coordLng, favest) "+
                        "VALUES ("+e.getNumero()+",'"+e.getNombre()+"',"+e.getDisponibles()+","+e.getLibres()+","+e.getLatitud()+","+e.getLongitud()+","+fav+")");

            }
            db.close();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acercade, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_acercade) {
            Intent about = new Intent(this, AcercaDe.class);
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static List<Estacion> parserJsonToEstacion(){
        List<Estacion> estaciones = new ArrayList<>();
        JSONParser jp = new JSONParser();
        JSONArray jArray = jp.makeHttpRequest("https://api.jcdecaux.com/vls/v1/stations?contract=Seville&apiKey=74b4b000eab8097de7f13de09a88e04706e2b99b", "GET", new HashMap<String, String>());

        try {
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObj = jArray.getJSONObject(i);
                Estacion estacion = new Estacion();

                String name = jObj.getString("name");
                int numero = jObj.getInt("number");
                int available = jObj.getInt("available_bikes");
                int free = jObj.getInt("available_bike_stands");
                double lat = jObj.getJSONObject("position").getDouble("lat");
                double len = jObj.getJSONObject("position").getDouble("lng");

                estacion.setNombre(name.substring(4));
                estacion.setNumero(numero);
                estacion.setDisponibles(available);
                estacion.setLibres(free);
                estacion.setLatitud(lat);
                estacion.setLongitud(len);

                estaciones.add(estacion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return estaciones;
    }

}
