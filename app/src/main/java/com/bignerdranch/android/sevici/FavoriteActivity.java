package com.bignerdranch.android.sevici;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAFA on 11/05/2017.
 */

public class FavoriteActivity extends AppCompatActivity{
    private Spinner spinner1;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        addItemsOnSpinner1();
        addListenerOnButton();
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();

        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){
            list.add(e[3]+" - "+e[4]);
        }
        //   list.add("list 1");
        //   list.add("list 2");//  list.add("list 3");

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

                Toast.makeText(FavoriteActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                String seleccionada = (String) spinner1.getSelectedItem();
            }

        });



    }
}
