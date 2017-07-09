package com.bignerdranch.android.sevici;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by RAFA on 21/03/2017.
 */

public class Estacion {

        private int disponibles;
        private int libres;
        private String nombre;
        private int numero;
        private Double latitud;
        private Double longitud;

        // Proveedor estático de datos para el adaptador

        public static List<Estacion> estaciones = new ArrayList<>();


        public Estacion() {
            super();
        }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public int getLibres() {
        return libres;
    }

    public void setLibres(int libres) {
        this.libres = libres;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
