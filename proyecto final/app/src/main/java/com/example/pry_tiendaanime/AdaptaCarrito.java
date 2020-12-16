package com.example.pry_tiendaanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import clases.Compra;
import clases.Serie;

public class AdaptaCarrito extends ArrayAdapter<Compra> {
    public AdaptaCarrito(Context context, int resource, List<Compra> datos) {
        super(context, resource, datos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflar=(LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflar.inflate(R.layout.vista_carrito, null);
        }
        Compra  item=(Compra) getItem(position);
        ImageView foto=(ImageView)convertView.findViewById(R.id.ImgCarrito);
        TextView serie = (TextView)convertView.findViewById(R.id.txtnomCarrito);
        TextView precio = (TextView)convertView.findViewById(R.id.txtPrecioCarrito);
        TextView cantidad = (TextView)convertView.findViewById(R.id.txtCantidadCarrito);
        TextView total = (TextView)convertView.findViewById(R.id.DetalleTotal);

        foto.setImageBitmap(item.getImgSerie());
        serie.setText(item.getNombreSerie());
        precio.setText(String.valueOf(item.getPrecio()));
        cantidad.setText(String.valueOf(item.getCantidad()));
        total.setText(String.valueOf(item.Total()));

        return convertView;
    }
}
