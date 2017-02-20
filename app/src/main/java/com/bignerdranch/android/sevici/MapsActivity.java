package com.bignerdranch.android.sevici;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        LatLng sevilla = new LatLng(37.375561, -6.0951493);

        LatLng sydney = new LatLng(37.3582148, -5.9892272);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in ETSII"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));

        LatLng sydney1 = new LatLng(37.2824363, -5.9385094);
        mMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in McDonals"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
    }

/*
    //Obtener ubicacion actual
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    public void agregarMarcador(double lat, double lng){
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if(marcador != null){
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title("Mi ubicación actual")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location){
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locListener = new LocationListener(){
        public void onLocationChanged(Location location){
            actualizarUbicacion(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras){

        }

        public void onProviderEnabled(String provider){

        }

        public void onProviderDisabled(String provider){

        }
    };


    private void miUbicacion{
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


    }
*/
}