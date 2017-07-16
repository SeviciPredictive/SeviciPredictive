package com.bignerdranch.android.sevici;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHUL on 13/05/2017.
 */

/*private int available;
private int free;
private String name;
private int numero;
private Double lat;
private Double len;*/

public class BDEstaciones extends SQLiteOpenHelper{

    String sqlCreate = "CREATE TABLE Estaciones (numero INTEGER, nombre TEXT, disponibles INTEGER, libres INTEGER,coordLat DOUBLE, coordLng DOUBLE)";

    public BDEstaciones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //Se va a ejecutar en el momento que llamemos a la base de datos y detecte que no esta.
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crea si no existe la base de datos
        db.execSQL(sqlCreate);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Se ejecuta cuando tengo la version 1 y le pase la version 2. Ve una actualizacion
        db.execSQL("DROP TABLE IF EXISTS Estaciones");

        //Se crea la version nueva de la tabla
        db.execSQL(sqlCreate);

        //Añade los datos de las estaciones

    }


}
