package com.example.pry_tiendaanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import clases.Categoria;

public class Adapta1 extends ArrayAdapter<Categoria> {

	public Adapta1(Context context, int resource, List<Categoria> datos) {
		super(context, resource, datos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater inflar=(LayoutInflater)parent.getContext().
			getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflar.inflate(R.layout.vista_categoria, null);
		}

		Categoria  item=(Categoria) getItem(position);
		TextView tcodc=(TextView)convertView.findViewById(R.id.txtnomCategoria);
		ImageView foto=(ImageView)convertView.findViewById(R.id.txtimagenCategoria);


		tcodc.setText(item.getNombre() );
		foto.setImageBitmap(item.getFoto());

		return convertView;
	}
}
