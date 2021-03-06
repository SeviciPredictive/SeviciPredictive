package com.bignerdranch.android.sevici;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.LocationListener;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.OkHttpClient;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        List<Estacion> estaciones =extraerDatosBDEstaciones();
        estaciones.size();
        LatLng sevilla = new LatLng(37.3754865, -6.025099);

        for (Estacion e : estaciones) {
            LatLng latLng = new LatLng(e.getLatitud(), e.getLongitud());
            mMap.addMarker(new MarkerOptions().position(latLng).title(e.getNumero() + "-" + e.getNombre()).snippet(
                    "Bicicletas disponibles:" + e.getDisponibles() + " - Bornetas libres: " + e.getLibres())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.images)));
        }

        miUbicacion();
     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
     //   mMap.animateCamera(CameraUpdateFactory.zoomTo(10.5f));
    }


    //Obtener ubicacion actual
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    boolean isFirstTime=true;

    public void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        if(isFirstTime) {
            mMap.animateCamera(miUbicacion);
            mMap.moveCamera(miUbicacion);
            isFirstTime = false;
        }

    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    };


    private void miUbicacion() {
        mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, locListener);
    }

    public List<Estacion> parserJsonToEstacion(){
        List<Estacion> estaciones = new ArrayList<>();
        JSONParser jp = new JSONParser();
        JSONArray jArray = jp.makeHttpRequest("https://api.jcdecaux.com/vls/v1/stations" +
                "?contract=Seville&apiKey=74b4b000eab8097de7f13de09a88e04706e2b99b", "GET", new HashMap<String, String>());

        try {
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObj = jArray.getJSONObject(i);
                Estacion estacion = new Estacion();

                String name = jObj.getString("name");
                int numero = jObj.getInt("number");
                int available = jObj.getInt("available_bikes");
                int free = jObj.getInt("available_bike_stands");
                double lat = jObj.getJSONObject("position").getDouble("lat");
                double len = jObj.getJSONObject("position").getDouble("lng");

                estacion.setNombre(name.substring(4));
                estacion.setNumero(numero);
                estacion.setDisponibles(available);
                estacion.setLibres(free);
                estacion.setLatitud(lat);
                estacion.setLongitud(len);

                estaciones.add(estacion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return estaciones;
    }


    private List<Estacion> extraerDatosBDEstaciones() {
        BDEstaciones estaciones = new BDEstaciones(this,"BDEstaciones",null,1);
        SQLiteDatabase db =  estaciones.getWritableDatabase();
        List<Estacion>estacionesdb = new ArrayList<>();

        Cursor cursor=db.query("estaciones",new String[]{"numero","nombre","disponibles","libres","coordLat","coordLng","favest"},null,null,null,null,null);
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

        return estacionesdb;
        }

}