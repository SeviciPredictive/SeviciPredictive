package com.bignerdranch.android.sevici;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

/**
 * Created by CHUL on 02/09/2017.
 */

public class ListRutasActivity extends Activity {

    ListView listView;
    private ItemArrayAdapter2 itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter2(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);


        //List<String> lista = new ArrayList<String>();
        //lista.add("Numero - Nombre - Disponible");

        BDEstaciones estaciones = new BDEstaciones(getApplicationContext(),"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();
        Cursor cursor=db.query("rutas",new String[]{"id","nombreOri","latOri","lonOri","nombreDes","latDes","lonDes"},null,null,null,null,null);
        while(cursor.moveToNext()){
            Rutas ruta = new Rutas();
            ruta.setId(cursor.getInt(0));
            ruta.setNombreOri(cursor.getString(1));
            ruta.setLatOri(cursor.getDouble(2));
            ruta.setLonOri(cursor.getDouble(3));
            ruta.setNombreDes(cursor.getString(4));
            ruta.setLatDes(cursor.getDouble(5));
            ruta.setLonDes(cursor.getDouble(6));

            itemArrayAdapter.add(ruta);
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

