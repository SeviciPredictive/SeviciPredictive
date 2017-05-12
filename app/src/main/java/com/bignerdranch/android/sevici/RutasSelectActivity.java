package com.bignerdranch.android.sevici;

import android.content.Intent;
import android.net.Uri;
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

        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[4]);
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

        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[4]);
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

                Toast.makeText(RutasSelectActivity.this,
                        "Calcular rutas entre : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();



                String origenSeleccionado = (String) spinner1.getSelectedItem();
                String destinoSeleccionado = (String) spinner2.getSelectedItem();



                InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
                CSVFile csvFile = new CSVFile(inputStream);
                List<String[]> stations = csvFile.read();
                Double latOrigen = 0.0;
                Double lngOrigen = 0.0;
                Double latDestino = 0.0;
                Double lngDestino = 0.0;
                for(String[] e: stations) {
                    if (e[4].toString().contains(origenSeleccionado)) {
                        latOrigen = Double.parseDouble(e[6]);
                        lngOrigen = Double.parseDouble(e[7]);
                        //LatLng latLng = new LatLng(latOrigen, lngOrigen);
                    }

                    if (e[4].toString().contains(destinoSeleccionado)) {
                        latDestino = Double.parseDouble(e[6]);
                        lngDestino = Double.parseDouble(e[7]);
                        //LatLng latLng = new LatLng(latOrigen, lngOrigen);
                    }

                    Uri uriUrl = Uri.parse("http://maps.google.com/maps?saddr=" + latOrigen.toString() + "," + lngDestino.toString()
                            + "&daddr=" + latDestino.toString() + "," + lngDestino.toString());
                    //Especificamos la accion a realizar con el ACTION_VIEW
                    //para que elija lo mas razonable
                    Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(intent);
                }


            }

        });
    }


}
