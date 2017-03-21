package com.bignerdranch.android.sevici;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by RAFA on 21/03/2017.
 */

public class Estacion {

    private int available;
    private int free;
    private int total;
    private int ticket;
    private int open;
    private int updated;
    private int connected;

    // Proveedor estático de datos para el adaptador
    public static List<Estacion> ESTACIONES = new ArrayList<>();

    public Estacion(int available, int free, int total, int ticket, int open, int updated, int connected) {

        this.available = available;
        this.free = free;
        this.total = total;
        this.ticket = ticket;
        this.open = open;
        this.updated = updated;
        this.connected = connected;
    }





    public int getAvailable() {
        return available;
    }

    public int getFree() {
        return free;
    }

    public int getTotal() {
        return total;
    }
    public int getTicket() {
        return ticket;
    }

    public int getOpen() {
        return open;
    }

    public int getUpdated() {
        return updated;
    }

    public int getConnected() {
        return connected;
    }

}
