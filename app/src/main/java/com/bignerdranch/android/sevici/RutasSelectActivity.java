package com.bignerdranch.android.sevici;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class RutasSelectActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    private Button btnSubmit1;
    private Button btnSubmitVer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_select);

        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addListenerOnButton();
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();

        List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
        for(Estacion e:estaciones){
            list.add(e.getNombre());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();

        List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
        for(Estacion e:estaciones){
            list.add(e.getNombre());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }



    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit1 = (Button) findViewById(R.id.btnSubmit1);


        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String origenSeleccionado = (String) spinner1.getSelectedItem();
                String destinoSeleccionado = (String) spinner2.getSelectedItem();

                Double latOrigen = 0.0;
                Double lngOrigen = 0.0;
                Double latDestino = 0.0;
                Double lngDestino = 0.0;

                int bicilibOrigen = 0;
                int bornlibDestino = 0;

                List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
                for (Estacion e : estaciones) {
                    if (e.getNombre().equals(origenSeleccionado)) {
                        latOrigen = e.getLatitud();
                        lngOrigen = e.getLongitud();
                        bicilibOrigen = e.getDisponibles();
                    }
                    if (e.getNombre().equals(destinoSeleccionado)) {
                        latDestino = e.getLatitud();
                        lngDestino = e.getLongitud();
                        bornlibDestino = e.getLibres();
                    }
                }


                Uri uriUrl = Uri.parse("http://maps.google.es/maps?saddr=" + latOrigen.toString() + "," + lngOrigen.toString()
                        + "&daddr=" + latDestino.toString() + "," + lngDestino.toString() + "?mode=bicycling");
                //Especificamos la accion a realizar con el ACTION_VIEW
                //para que elija lo mas razonable
                if ((latOrigen.toString().equals(latDestino.toString())) && (lngOrigen.toString().equals(lngDestino.toString()))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "La estacion origen y destino \n      deben ser distintas", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (bicilibOrigen < 3) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Hay menos de 3 bicicletas \n     disponible en la estacion origen", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (bornlibDestino < 3) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Hay menos de 3 bornetas \n      libres en la estacion destino", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(intent);
                }


            }

        });

        btnSubmit1.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {

                //Nos quedamos con el nombre de las estaciones.

                String origenSeleccionado = (String) spinner1.getSelectedItem();
                String destinoSeleccionado = (String) spinner2.getSelectedItem();



                Double latOrigen = 0.0;
                Double lngOrigen = 0.0;
                Double latDestino = 0.0;
                Double lngDestino = 0.0;

                int bicilibOrigen = 0;
                int bornlibDestino = 0;

                List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
                for (Estacion e : estaciones) {
                    if (e.getNombre().equals(origenSeleccionado)) {
                        latOrigen = e.getLatitud();
                        lngOrigen = e.getLongitud();
                        bicilibOrigen = e.getDisponibles();
                    }
                    if (e.getNombre().equals(destinoSeleccionado)) {
                        latDestino = e.getLatitud();
                        lngDestino = e.getLongitud();
                        bornlibDestino = e.getLibres();
                    }
                }

                if ((latOrigen.toString().equals(latDestino.toString())) && (lngOrigen.toString().equals(lngDestino.toString()))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "La estacion origen y destino \n      deben ser distintas", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    BDEstaciones estacionesbd = new BDEstaciones(getApplicationContext(),"BDEstaciones",null,1);
                    SQLiteDatabase db =  estacionesbd.getWritableDatabase();
                    db.execSQL("INSERT INTO Rutas (nombreOri, latOri, lonOri, nombreDes, latDes, lonDes) " +
                            "VALUES ('" + origenSeleccionado + "'," + latOrigen + "," + lngOrigen + ",'" + destinoSeleccionado + "'," + latDestino + "," + lngDestino + ")");

                    Toast toast = Toast.makeText(getApplicationContext(), "La ruta ha sido añadida a favoritos", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }

        });

        btnSubmitVer = (Button) findViewById(R.id.button6);
        btnSubmitVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(getApplicationContext(), ListRutasActivity.class);
                startActivity(mainIntent);
            }
        });

    }




}
