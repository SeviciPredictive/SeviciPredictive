package com.bignerdranch.android.sevici;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<Estacion> {

	private List<Estacion> scoreList = new ArrayList<Estacion>();

    public static class ItemViewHolder {
        TextView numero;
        TextView nombre;
        TextView disponible;
        TextView libres;

    }

    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(Estacion object) {
		scoreList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.scoreList.size();
	}

    @Override
	public Estacion getItem(int index) {
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


        Estacion stat = getItem(position);
        final int num = stat.getNumero();
        final String numero = Integer.toString(stat.getNumero());
        String disponibles = Integer.toString(stat.getDisponibles());
        String libres = Integer.toString(stat.getLibres());

        viewHolder.numero.setText("Numero: "+numero+ " Nombre: "+stat.getNombre());
        //viewHolder.nombre.setText("Nombre: "+stat.getNombre());
        viewHolder.disponible.setText("Disponibles: "+disponibles+ " Libres: "+libres);
        //viewHolder.libres.setText("Libres: "+libres);


        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(v.getRootView().getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(v.getRootView().getContext());
                }
                builder.setTitle("Borrar estación favorita")
                        .setMessage("¿Estas seguro que deseas borrar la estación seleccionada?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int i;
                                for(i=0;i<scoreList.size();i++) {
                                    if(num==scoreList.get(i).getNumero()){
                                        scoreList.remove(i);
                                    }
                                    notifyDataSetChanged();
                                }
                                BDEstaciones estaciones = new BDEstaciones(getContext().getApplicationContext(),"BDEstaciones",null,1);
                                SQLiteDatabase db =  estaciones.getWritableDatabase();
                                ContentValues cv =  new ContentValues();
                                cv.put("favest",0);
                                db.update("estaciones",cv,"numero="+num,null );
                                db.close();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return row;
	}
}
