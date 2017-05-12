/*
package com.bignerdranch.android.sevici;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
*/
/**
 * Created by RAFA on 21/03/2017.
 *//*


public class ParseXMLEstacion {

    // Namespace general. null si no existe
    private static final String ns = null;

    // Constantes del archivo Xml
    private static final String ETIQUETA_ESTACIONES = "hoteles";

    private static final String ETIQUETA_ESTACION = "estacion";

    private static final String ETIQUETA_AVAILABLE = "available";
    private static final String ETIQUETA_FREE = "free";
    private static final String ETIQUETA_TOTAL = "total";
    private static final String ETIQUETA_TICKET = "ticket";
    private static final String ETIQUETA_OPEN = "open";
    private static final String ETIQUETA_UPDATED = "updated";
    private static final String ETIQUETA_CONNECTED = "connected";


    */
/**
     * Parsea un flujo XML a una lista de objetos {@link Estacion}
     *
     * @param in flujo
     * @return Lista de estaciones
     * @throws XmlPullParserException
     * @throws IOException
     *//*


    public Estacion parsear(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(in, null);
            parser.nextTag();
            return leerEstacion(parser);
        } finally {
            in.close();
        }
    }


*/
/*
    public List<Estacion> parsear(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(in, null);
            parser.nextTag();
            return leerEstaciones(parser);
        } finally {
            in.close();
        }
    }


    */
/**
     * Convierte una serie de etiquetas <hotel> en una lista
     *
     * @param parser
     * @return lista de hoteles
     * @throws XmlPullParserException
     * @throws IOException
     *//*

    */
/*
    private List<Hotel> leerHoteles(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<Hotel> listaHoteles = new ArrayList<Hotel>();

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_HOTELES);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nombreEtiqueta = parser.getName();
            // Buscar etiqueta <hotel>
            if (nombreEtiqueta.equals(ETIQUETA_HOTEL)) {
                listaHoteles.add(leerHotel(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }
        return listaHoteles;
    }
*//*

    */
/**
     * Convierte una etiqueta <estacion> en un objeto Estacion
     *
     * @param parser parser XML
     * @return nuevo objeto Hotel
     * @throws XmlPullParserException
     * @throws IOException
     *//*

    private Estacion leerEstacion(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_ESTACION);
        int available=0;
        int free=0;
        int total=0;
        int ticket=0;
        int open=0;
        int updated=0;
        int connected=0;


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (name) {
                case ETIQUETA_AVAILABLE:
                    available = leerAvailable(parser);
                    break;
                case ETIQUETA_FREE:
                    free = leerFree(parser);
                    break;
                case ETIQUETA_TOTAL:
                    total = leerTotal(parser);
                    break;
                case ETIQUETA_TICKET:
                    ticket = leerTicket(parser);
                    break;
                case ETIQUETA_OPEN:
                    open = leerOpen(parser);
                    break;
                case ETIQUETA_UPDATED:
                    updated = leerUpdated(parser);
                    break;
                case ETIQUETA_CONNECTED:
                    connected = leerConnected(parser);
                    break;
                default:
                    saltarEtiqueta(parser);
                    break;
            }
        }
        return new Estacion(available,
                free,
                total,
                ticket,
                open,
                updated,
                connected);
    }



    // Procesa la etiqueta <available> de las estaciones
    private int leerAvailable(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_AVAILABLE);
        int available = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_AVAILABLE);
        return available;
    }

    // Procesa la etiqueta <free> de las estaciones
    private int leerFree(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_FREE);
        int free = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_FREE);
        return free;
    }

    // Procesa la etiqueta <total> de las estaciones
    private int leerTotal(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TOTAL);
        int total = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TOTAL);
        return total;
    }

    // Procesa la etiqueta <ticket> de las estaciones
    private int leerTicket(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TICKET);
        int ticket = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TICKET);
        return ticket;
    }

    // Procesa la etiqueta <open> de las estaciones
    private int leerOpen(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_OPEN);
        int open = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_OPEN);
        return open;
    }

    // Procesa la etiqueta <updated> de las estaciones
    private int leerUpdated(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_UPDATED);
        int updated = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_UPDATED);
        return updated;
    }

    // Procesa la etiqueta <connected> de las estaciones
    private int leerConnected(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_CONNECTED);
        int connected = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_CONNECTED);
        return connected;
    }


    // Obtiene el texto de los atributos
    private String obtenerTexto(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    // Salta aquellos objeteos que no interesen en la jerarquía XML.
    private void saltarEtiqueta(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}*/
