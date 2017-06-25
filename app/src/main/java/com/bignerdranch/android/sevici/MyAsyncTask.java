package com.bignerdranch.android.sevici;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by CHUL on 25/06/2017.
 */


class MyAsyncTask extends AsyncTask<String, Void, String> {

    HttpURLConnection urlConnection;
    List<Estacion> estaciones = new ArrayList<Estacion>();
    private MapsActivity mapsActivity;

    public MyAsyncTask(MapsActivity mapsActivity){
        this.mapsActivity=mapsActivity;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();


        try {
            URL url = new URL("https://api.jcdecaux.com/vls/v1/stations?contract=Seville&apiKey=74b4b000eab8097de7f13de09a88e04706e2b99b");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {


        try {

            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                int tamaño = jArray.length();
                String name = jObject.getString("name");
                int numero = jObject.getInt("number");
                int active = jObject.getInt("available_bikes");
                double lat = jObject.getJSONObject("position").getDouble("lat");
                double len = jObject.getJSONObject("position").getDouble("len");

                Estacion estacion = new Estacion();
                estacion.setAvailable(active);
                estacion.setName(name);
                estacion.setNumero(numero);
                estacion.setLat(lat);
                estacion.setLen(len);

                estaciones.add(estacion);

            }

            mapsActivity.setEstaciones(estaciones);

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }

    }
}