package com.bignerdranch.android.sevici;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RAFA on 11/05/2017.
 */

public class FavoriteActivity extends AppCompatActivity{
    private Spinner spinner1;
    private Button btnSubmit;
    public  static final String PREFS_FAVORITOS = "EstacionesFavoritas";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        addItemsOnSpinner1();
        addListenerOnButton();
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<Estacion> estaciones = MainActivity.parserJsonToEstacion();
        List<String> list = new ArrayList<String>();

        for(Estacion e:estaciones){
            list.add(e.getNumero()+"-"+e.getNombre());
        }

       /* InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[3]+" - "+e[4]);
        }*/

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        String itemText = (String) spinner1.getSelectedItem();
    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //String seleccionada = (String) spinner1.getSelectedItem();
                //metodoFavorito();
                createSharedPreferences();
                //readSharedPreferences();

            }

        });

    }


    private void createSharedPreferences(){
        SharedPreferences settings = getSharedPreferences(PREFS_FAVORITOS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(spinner1.getSelectedItem().toString(), "estacion");
        editor.commit();
        Toast.makeText(FavoriteActivity.this,"Estacion añadida a favoritos" + spinner1.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }


    private void readSharedPreferences(){
        SharedPreferences settings = getSharedPreferences(PREFS_FAVORITOS, 0);
        String valores = settings.getString("1 - Glorieta Olimpica", "No encontrado");
    }

}
