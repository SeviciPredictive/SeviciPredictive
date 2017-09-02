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

    String sqlCreate = "CREATE TABLE Estaciones (numero INTEGER, nombre TEXT, direccion TEXT, disponibles INTEGER, libres INTEGER,coordLat DOUBLE, coordLng DOUBLE, favest INTEGER)";
    String sqlCreate1 = "CREATE TABLE Rutas (id INTEGER PRIMARY KEY AUTOINCREMENT,nombreOri TEXT, latOri DOUBLE, lonOri DOUBLE, nombreDes TEXT, latDes DOUBLE, lonDes DOUBLE)";


    public BDEstaciones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //Se va a ejecutar en el momento que llamemos a la base de datos y detecte que no esta.
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Crea si no existe la base de datos
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate1);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Se ejecuta cuando tengo la version 1 y le pase la version 2. Ve una actualizacion
        db.execSQL("DROP TABLE IF EXISTS Estaciones");
        db.execSQL("DROP TABLE IF EXISTS Rutas");

        //Se crea la version nueva de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate1);

        //Añade los datos de las estaciones

    }
}
