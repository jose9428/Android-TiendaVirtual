package com.example.pry_tiendaanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import clases.Serie;

public class AdaptaSerie extends ArrayAdapter<Serie> {

	public AdaptaSerie(Context context, int resource, List<Serie> datos) {
		super(context, resource, datos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater inflar=(LayoutInflater)parent.getContext().
			getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflar.inflate(R.layout.vista_serie, null);
		}
		Serie  item=(Serie) getItem(position);
		TextView tcodc=(TextView)convertView.findViewById(R.id.txtnombreSerie);
		ImageView foto=(ImageView)convertView.findViewById(R.id.txtImgSerie);


		tcodc.setText(item.getNombreSerie());
		foto.setImageBitmap(item.getImgSerie());

		return convertView;
	}

}
