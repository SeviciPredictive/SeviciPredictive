package com.bignerdranch.android.sevici;

/**
 * Created by CHUL on 17/03/2017.
 */

public class Station {

    private String lat;
    private String lng;
    private String idEstacion;
    private String direccion;
    private String prueba;

    public Station(){
        super();
    }

    public Station(String lat, String lng, String idEstacion, String direccion){
        this.lat=lat;
        this.lng=lng;
        this.idEstacion=idEstacion;
        this.direccion=direccion;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Estacion [lat=" + lat + ", lng=" + lng + ", idEstacion=" + idEstacion + ", direccion=" + direccion
                + "]";
    }
}
