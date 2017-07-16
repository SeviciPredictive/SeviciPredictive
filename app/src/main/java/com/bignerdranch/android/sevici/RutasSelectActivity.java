package com.bignerdranch.android.sevici;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    private GoogleMap map;
    private WebView webView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_select);

        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addListenerOnButton();
        // addListenerOnSpinnerItemSelection();
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();

      /*  InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[4]);
        }*/

        List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
        for(Estacion e:estaciones){
            list.add(e.getNombre());
        }
        //   list.add("list 1");
        //   list.add("list 2");//  list.add("list 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();

        /*InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[4]);
        }*/
        List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
        for(Estacion e:estaciones){
            list.add(e.getNombre());
        }
        //   list.add("list 1");
        //   list.add("list 2");//  list.add("list 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    // public void addListenerOnSpinnerItemSelection() {
    //      spinner1 = (Spinner) findViewById(R.id.spinner1);
    //       spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//   }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);




        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String origenSeleccionado = (String) spinner1.getSelectedItem();
                String destinoSeleccionado = (String) spinner2.getSelectedItem();

                Double latOrigen = 0.0;
                Double lngOrigen = 0.0;
                Double latDestino = 0.0;
                Double lngDestino = 0.0;

          /*      InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
                CSVFile csvFile = new CSVFile(inputStream);
                List<String[]> stations = csvFile.read();
                Double latOrigen = 0.0;
                Double lngOrigen = 0.0;
                Double latDestino = 0.0;
                Double lngDestino = 0.0;*/

                List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
                for(Estacion e:estaciones){
                    if(e.getNombre().equals(origenSeleccionado)){
                        latOrigen = e.getLatitud();
                        lngOrigen = e.getLongitud();
                    }
                    if(e.getNombre().equals(destinoSeleccionado)){
                        latDestino = e.getLatitud();
                        lngDestino = e.getLongitud();
                    }
                }

               /* for(String[] e: stations) {
                    if (e[4].toString().contains(origenSeleccionado)) {
                        latOrigen = Double.parseDouble(e[6]);
                        lngOrigen = Double.parseDouble(e[7]);
                        //LatLng latLng = new LatLng(latOrigen, lngOrigen);
                    }

                    if (e[4].toString().contains(destinoSeleccionado)) {
                        latDestino = Double.parseDouble(e[6]);
                        lngDestino = Double.parseDouble(e[7]);
                        //LatLng latLng = new LatLng(latOrigen, lngOrigen);
                    }*/



                Uri uriUrl = Uri.parse("http://maps.google.es/maps?saddr=" + latOrigen.toString() + "," + lngDestino.toString()
                        + "&daddr=" + latDestino.toString() + "," + lngDestino.toString()+"?mode=bicycling");
                //Especificamos la accion a realizar con el ACTION_VIEW
                //para que elija lo mas razonable
                if((latOrigen.toString().equals(latDestino.toString())) && (lngOrigen.toString().equals(lngDestino.toString()))){
                    Toast toast = Toast.makeText(getApplicationContext(), "La estacion origen y destino \n      deben ser distintas", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(intent);
                }


            }

        });
    }


}
