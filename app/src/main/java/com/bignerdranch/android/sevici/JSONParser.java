package com.bignerdranch.android.sevici;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CHUL on 14/05/2017.
 */

public class JSONParser {

    public static List<Estacion> generateEstacionInfoJason() {

        List<Estacion> estaciones = new ArrayList<Estacion>();

        try {
            JSONArray json = readJsonFromUrl("https://api.jcdecaux.com/vls/v1/stations?contract=Seville&apiKey=74b4b000eab8097de7f13de09a88e04706e2b99b");
            for(int i=0;i<json.length();i++){
                JSONObject obj = (JSONObject)json.get(i);
                Estacion estacion = new Estacion();
                estacion.setName(obj.getString("name"));
                estacion.setNumero(obj.getInt("number"));
                estacion.setAvailable(obj.getInt("available_bikes"));
                estacion.setFree(obj.getInt("available_bike_stands"));
                JSONObject pos = (JSONObject) obj.getJSONObject("position");
                estacion.setLat(pos.getDouble("lat"));
                estacion.setLen(pos.getDouble("lng"));
                estaciones.add(estacion);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }  catch (JSONException e1) {
            e1.printStackTrace();
        }
        return estaciones;
    }


    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        JSONArray json = null;
        try {

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONArray(jsonText);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return json;
    }


}
