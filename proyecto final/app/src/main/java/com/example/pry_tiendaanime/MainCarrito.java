package com.example.pry_tiendaanime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import clases.Carrito;
import clases.Compra;

public class MainCarrito extends AppCompatActivity {
    ListView lis2;
    TextView total;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        lis2=(ListView)findViewById(R.id.listado);
        total = (TextView)findViewById(R.id.txtCarritoImporteTotal);
        cargaCarrito();
    }

    public void cargaCarrito(){
        List<Compra> lis = ((Carrito)getApplication()).getLista();
        String cadena = "";
        for(Compra c : lis){
            cadena+= c.getNombreSerie()+"\n";

        }

        AdaptaCarrito dp = new AdaptaCarrito(MainCarrito.this,0,lis);

        lis2.setAdapter(dp);
        total.setText("Importe Total S/."+ImporteTotal());
    }

    public double ImporteTotal(){
        double total = 0;
        List<Compra> lis = ((Carrito)getApplication()).getLista();

        for(Compra c : lis){
           total+= c.Total();
        }

        return total;
    }

    public int Tamanio(){
        List<Compra> lis = ((Carrito)getApplication()).getLista();
        return lis.size();
    }

    public void eliminarCarrito(View v){
        Toast.makeText(this, "Click en la posicion ",Toast.LENGTH_SHORT).show();
    }



    public void seguirComprando(View v){
        Intent it=new Intent(MainCarrito.this,MainActivity.class);
        startActivity(it);
    }

    public void aceptarCompra(View v){
        List<Compra> lis = ((Carrito)getApplication()).getLista();
        if(lis.isEmpty()){
            Toast.makeText(this, "El carrito de compras se encuentra vacio.!", Toast.LENGTH_SHORT).show();
        }else{
            Intent it=new Intent(MainCarrito.this,MainLogin.class);
            startActivity(it);
        }
    }

}
