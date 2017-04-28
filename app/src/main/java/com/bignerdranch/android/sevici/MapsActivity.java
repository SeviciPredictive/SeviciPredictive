package com.bignerdranch.android.sevici;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.location.LocationListener;
import android.widget.ImageView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.InputStream;
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

        // Add a marker in Sydney and move the camera

        //LatLng sevilla = new LatLng(37.375561, -6.0951493);

        //LatLng sydney = new LatLng(37.3582148, -5.9892272);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in ETSII"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));

        //LatLng sydney1 = new LatLng(37.2824363, -5.9385094);
        //mMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in McDonals"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
        // miUbicacion();



        InputStream inputStream = getResources().openRawResource(R.raw.estaciones_sevici);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> stations = csvFile.read();
        for(String[] e: stations){

            /*if(e[4].equals("Glorieta Olimpica")){
                LatLng latLng = new LatLng(37.412983041853934, -5.988933251932231);
                mMap.addMarker(new MarkerOptions().position(latLng).title(e[4]));
            }
            String lats = e[1].substring(0,16);
            String lons = e[0].substring(1,16);
            Double lat = new Double(lats);
            Double lon = new Double(lons);
            Double t = -1.0;
            Double m = lon * t;

            //LatLng latLng = new LatLng(Double.parseDouble(e[0].toString()),Double.parseDouble(e[1].toString()));
            LatLng latLng = new LatLng(lat, m);
            */
            Double lat = Double.parseDouble(e[6]);
            Double lng = Double.parseDouble(e[7]);
            LatLng latLng = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(latLng).title(e[4]).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.images)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
        }

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
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,7000,0,locListener);
    }

}