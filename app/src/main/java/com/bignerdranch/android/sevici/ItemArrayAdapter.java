package com.bignerdranch.android.sevici;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<Estacion> {

	private List<Estacion> scoreList = new ArrayList<Estacion>();

    static class ItemViewHolder {
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

    @Override
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
                /*View row = (View) v.getParent();
                ViewGroup container = ((ViewGroup)row.getParent());
                container.removeViewAt((int)v.getTag());*/


                Toast toast = Toast.makeText(getContext().getApplicationContext(), "La estación ha sido borrada" , Toast.LENGTH_SHORT);
                toast.show();

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
        });

        return row;
	}
}
