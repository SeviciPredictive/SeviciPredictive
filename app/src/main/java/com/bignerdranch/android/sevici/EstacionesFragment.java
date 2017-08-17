package com.bignerdranch.android.sevici;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EstacionesFragment extends Fragment {

    private Spinner spinner1;
    private Button btnSubmit;
    private Button btnList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_estaciones, container,false);

        //Inicializamos el boton y el spinner

        spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        btnSubmit = (Button) v.findViewById(R.id.button2);

        List<String> list = new ArrayList<String>();

        //Añadimos todas las estaciones al spinner.

        BDEstaciones estaciones = new BDEstaciones(this.getActivity(),"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();
        List<Estacion>estacionesdb = new ArrayList<>();

        Cursor cursor=db.query("estaciones",new String[]{"numero","nombre","disponibles","libres","coordLat","coordLng","favest"},null,null,null,null,"numero"+" ASC");
        //numero INTEGER, nombre TEXT, disponibles INTEGER, libres INTEGER,coordLat DOUBLE, coordLng DOUBLE, favest INTEGER)

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
            estacionesdb.add(estacion);

        }
        db.close();

        //Añadimos las estaciones al desplegable(spinner)
        SpinnerEstacion adapter = new SpinnerEstacion(estacionesdb);
        spinner1.setAdapter(adapter);
       /* for(Estacion e:estacionesdb){
            list.add(e.getNumero()+" - "+e.getNombre());
        }*/

        spinner1.getDropDownHorizontalOffset();
        spinner1.getDropDownVerticalOffset();
       /* ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);*/

        //añadimos el metodo escucha al boton

        btnList = (Button) v.findViewById(R.id.button4);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(getActivity().getApplicationContext(), ListEstacionActivity.class);
                startActivity(mainIntent);
            }
        });

        addListenerOnButton();

        return v;
        }

    public void addListenerOnButton() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                Estacion estacionSeleccionada = (Estacion) spinner1.getSelectedItem();
                int numEsta = estacionSeleccionada.getNumero();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Has seleccionado la estacion "+numEsta, Toast.LENGTH_SHORT);
                toast.show();

                //Cambiamos el valor del atributo a true
                BDEstaciones estaciones = new BDEstaciones(getActivity(),"BDEstaciones",null,1);
                SQLiteDatabase db =  estaciones.getWritableDatabase();
                ContentValues  cv =  new ContentValues();
                cv.put("favest",1);
                db.update("estaciones",cv,"numero="+numEsta,null );
                db.close();
            }
        });
    }

    private class SpinnerEstacion implements SpinnerAdapter {


        List<Estacion> data;

        public SpinnerEstacion(List<Estacion> data){
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }


        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return android.R.layout.simple_spinner_dropdown_item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)         {
            TextView v = new TextView(getActivity().getApplicationContext());
            v.setTextColor(Color.BLACK);
            v.setText(data.get(position).getNumero()+" - "+data.get(position).getNombre());
            return v;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            // TODO Auto-generated method stub

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            // TODO Auto-generated method stub

        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return this.getView(position, convertView, parent);
        }

    }
}

