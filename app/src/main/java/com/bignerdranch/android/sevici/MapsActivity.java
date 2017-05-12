package com.bignerdranch.android.sevici;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.LocationListener;
import android.util.Log;

import javax.xml.parsers.ParserConfigurationException;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import java.io.InputStream;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



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
        LatLng sevilla = new LatLng(37.3754865, -6.025099);

        miUbicacion();

        List<Estacion> estaciones = generateEstacionInfoXML();

        for (Estacion e : estaciones) {
            LatLng latLng = new LatLng(e.getLat(), e.getLen());
            mMap.addMarker(new MarkerOptions().position(latLng).title(e.getNumero()+"-"+e.getName()).snippet("Bicis disponibles:" + e.getAvailable() +" - Bornetas libres: "+e.getFree())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.images)));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.5f));
    }


    //Obtener ubicacion actual
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    public void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi ubicación actual")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacionactual)));
        mMap.animateCamera(miUbicacion);
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, locListener);
    }

    public List<Estacion> generateEstacionesInfoCSV() {
        List<Estacion> res = new ArrayList<Estacion>();

        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for (String[] e : stations) {
            Estacion estacion = new Estacion();
            estacion.setName(e[4]);
            estacion.setNumero(e[3]);
            estacion.setLat(Double.parseDouble(e[6]));
            estacion.setLen(Double.parseDouble(e[7]));
            res.add(estacion);
            Log.d("CREATION", "Value" + estacion);
        }
        return res;

    }


    public List<Estacion> generateEstacionInfoXML() {

        List<Estacion> estaciones = generateEstacionesInfoCSV();

        for (Estacion e : estaciones) {
            try {
                URL url = new URL("http://www.sevici.es/service/stationdetails/seville/"+e.getNumero());

                URLConnection connection = url.openConnection();

                HttpURLConnection httpConnection = (HttpURLConnection)connection;
                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpConnection.getInputStream();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(in);
                    doc.getDocumentElement().normalize();

                    String available = doc.getDocumentElement().getElementsByTagName("available").item(0).getTextContent();
                    String free = doc.getDocumentElement().getElementsByTagName("free").item(0).getTextContent();

                    e.setAvailable(available);
                    e.setFree(free);
                }

               /* DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new URL("http://www.sevici.es/service/stationdetails/seville/" + e.getNumero()).openStream());
                doc.getDocumentElement().normalize();
*/


                } catch (Exception e1) {
                    System.out.println("XML Pasing Excpetion = " + e1);
                }

            }
        return estaciones;
    }

}