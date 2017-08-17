package com.bignerdranch.android.sevici;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListEstacionActivity extends Activity {

    ListView listView;
    private ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        //List<String> lista = new ArrayList<String>();
        //lista.add("Numero - Nombre - Disponible");

        BDEstaciones estaciones = new BDEstaciones(getApplicationContext(),"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();
        Cursor cursor=db.query("estaciones",new String[]{"numero","nombre","disponibles","libres","coordLat","coordLng","favest"},"favest=1",null,null,null,null);
        while(cursor.moveToNext()){
            Estacion estacion = new Estacion();
            estacion.setNombre(cursor.getString(1));
            estacion.setNumero(cursor.getInt(0));
            estacion.setDisponibles(cursor.getInt(2));
            estacion.setLibres(cursor.getInt(3));
            estacion.setLatitud(cursor.getDouble(4));
            estacion.setLongitud(cursor.getDouble(5));
            int favest = cursor.getInt(6);
            if(favest == 0){
                estacion.setFavest(false);
            }else{
                estacion.setFavest(true);
            }
            itemArrayAdapter.add(estacion);
           // lista.add(cursor.getInt(0)+"-"+cursor.getString(1)+" Disponible:"+cursor.getInt(2));
        }
        db.close();

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getListView().getContext(),R.layout.item_layout,lista);
        /* ArrayAdapter<Estacion> mAdapter = new ArrayAdapter<Estacion>(this,android.R.layout.simple_list_item_1, new ArrayList<Estacion>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                // Replace text with my own
                view.setText(getItem(position).getNumero()+" - "+getItem(position).getNombre());
                return view;
            }
        };*/
       // getListView().setAdapter(arrayAdapter);
        }
    }
