package com.bignerdranch.android.sevici;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by RAFA on 21/03/2017.
 */

public class Estacion {

    private int available;
    private int free;
    private String name;
    private String numero;
    private Double lat;
    private Double len;

    // Proveedor estático de datos para el adaptador
    public static List<Estacion> ESTACIONES = new ArrayList<>();



    public Estacion() {
        super();
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getFree() {
        return free;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLen(Double len) {
        this.len = len;
    }

    public String getNumero(){
        return numero;

    }

    public Double getLat(){
        return lat;
    }

    public Double getLen(){
        return len;
    }




}
