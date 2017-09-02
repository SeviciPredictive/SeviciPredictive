package com.bignerdranch.android.sevici;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHUL on 02/09/2017.
 */

public class Rutas {

    //nombreOri TEXT, latOri DOUBLE, lonOri DOUBLE, nombreDes TEXT, latDes DOUBLE, lonDes DOUBLE

    private int id;
    private String nombreOri;
    private Double latOri;
    private Double lonOri;
    private String nombreDes;
    private Double latDes;
    private Double lonDes;

    public static List<Rutas> rutas = new ArrayList<>();

    public Rutas(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreOri() {
        return nombreOri;
    }

    public void setNombreOri(String nombreOri) {
        this.nombreOri = nombreOri;
    }

    public Double getLatOri() {
        return latOri;
    }

    public void setLatOri(Double latOri) {
        this.latOri = latOri;
    }

    public Double getLonOri() {
        return lonOri;
    }

    public void setLonOri(Double lonOri) {
        this.lonOri = lonOri;
    }

    public String getNombreDes() {
        return nombreDes;
    }

    public void setNombreDes(String nombreDes) {
        this.nombreDes = nombreDes;
    }

    public Double getLatDes() {
        return latDes;
    }

    public void setLatDes(Double latDes) {
        this.latDes = latDes;
    }

    public Double getLonDes() {
        return lonDes;
    }

    public void setLonDes(Double lonDes) {
        this.lonDes = lonDes;
    }
}
