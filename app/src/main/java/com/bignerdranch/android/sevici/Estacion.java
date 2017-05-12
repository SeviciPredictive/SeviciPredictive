package com.bignerdranch.android.sevici;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by RAFA on 21/03/2017.
 */

public class Estacion {

    private String available;
    private String free;
    private String name;
    private String numero;
    private Double lat;
    private Double len;



    public Estacion() {
        super();
    }


    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
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

    @Override
    public String toString() {
        return "Estacion{" +
                "available=" + available +
                ", free=" + free +
                ", name='" + name + '\'' +
                ", numero='" + numero + '\'' +
                ", lat=" + lat +
                ", len=" + len +
                '}';
    }
}
