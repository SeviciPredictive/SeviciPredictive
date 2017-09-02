package com.bignerdranch.android.sevici;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter2 extends ArrayAdapter<Rutas> {

	private List<Rutas> scoreList = new ArrayList<Rutas>();

    public static class ItemViewHolder {
        TextView numero;
        TextView nombre;
        TextView disponible;
        TextView libres;

    }

    public ItemArrayAdapter2(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(Rutas object) {
		scoreList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.scoreList.size();
	}

    @Override
	public Rutas getItem(int index) {
		return this.scoreList.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        ItemViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.numero = (TextView) row.findViewById(R.id.numero);
            //viewHolder.nombre = (TextView) row.findViewById(R.id.nombre);
            viewHolder.disponible = (TextView) row.findViewById(R.id.disponible);
           // viewHolder.libres = (TextView) row.findViewById(R.id.libres);
            row.setTag(viewHolder);

		} else {
            viewHolder = (ItemViewHolder)row.getTag();
        }

        Rutas stat = getItem(position);

        String origen = stat.getNombreDes();
        String destino = stat.getNombreOri();
        final int num = stat.getId();
        final Double latOrigen = stat.getLatOri();
        final Double latDestino = stat.getLatDes();
        final Double lngDestino = stat.getLonDes();
        final Double lngOrigen = stat.getLonOri();

        viewHolder.numero.setText("Estacion origen: "+origen);
        //viewHolder.nombre.setText("Nombre: "+stat.getNombre());
        viewHolder.disponible.setText("Estacion destino: "+destino);
        //viewHolder.libres.setText("Libres: "+libres);

        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(v.getRootView().getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(v.getRootView().getContext());
                }
                builder.setTitle("Que accion desea realizar")
                        .setMessage("¿Deseas calcular la ruta o eliminarla de favoritas?")
                        .setPositiveButton(R.string.calcular, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uriUrl = Uri.parse("http://maps.google.es/maps?saddr=" + latOrigen.toString() + "," + lngOrigen.toString()
                                        + "&daddr=" + latDestino.toString() + "," + lngDestino.toString() + "?mode=bicycling");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getContext().startActivity(intent);

                            }
                        })
                        .setNegativeButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int i;
                                for(i=0;i<scoreList.size();i++) {
                                    if(num==scoreList.get(i).getId()){
                                        scoreList.remove(i);
                                    }
                                    notifyDataSetChanged();
                                    BDEstaciones estaciones = new BDEstaciones(getContext().getApplicationContext(),"BDEstaciones",null,1);
                                    SQLiteDatabase db =  estaciones.getWritableDatabase();
                                    db.delete("rutas","id="+num,null);
                                    db.close();
                                }
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return row;
	}
}
