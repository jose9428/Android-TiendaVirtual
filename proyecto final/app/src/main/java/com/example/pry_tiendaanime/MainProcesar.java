package com.example.pry_tiendaanime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import clases.Carrito;
import clases.Compra;
import clases.Serie;

public class MainProcesar extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public JSONArray lista;
    static String nroFactura;
    String generoFactura;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";
    ListView lis2;
    TextView total;
    TextView datosCLiente;
    Context context = this;
    int codigoCliente;
    int nro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesar_compra);
        lis2=(ListView)findViewById(R.id.ListadoCompra);
        total = (TextView)findViewById(R.id.txtCarritoImporteTotal);
        cargaCarrito();
        datosCLiente = (TextView)findViewById(R.id.txtDatosCliente);

        String user = ((Carrito)getApplication()).getUser();
        String apePaterno = ((Carrito)getApplication()).getApeP();
        codigoCliente = ((Carrito)getApplication()).getIdCliente();
        datosCLiente.setText("Cliente : "+user+ " , "+apePaterno);

       int nro = NroFactura();

       int aaa= ((Carrito)getApplication()).getId_factura();
       Toast.makeText(this, "Numero : "+aaa, Toast.LENGTH_SHORT).show();
    }

    public void cargaCarrito(){
        List<Compra> lis = ((Carrito)getApplication()).getLista();
        String cadena = "";
        for(Compra c : lis){
            cadena+= c.getNombreSerie()+"\n";

        }

        AdaptaCarrito dp = new AdaptaCarrito(MainProcesar.this,0,lis);

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
        Intent it=new Intent(MainProcesar.this,MainActivity.class);
        startActivity(it);
    }

    public void confirmarCompra(View v){
        List<Compra> lis = ((Carrito)getApplication()).getLista();
        if(lis.isEmpty()){
            Toast.makeText(this, "El carrito de compras se encuentra vacio.!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Procesando Compra.!", Toast.LENGTH_SHORT).show();

             generaNroFactura(codigoCliente,ImporteTotal());
             int nroFactura = NroFactura();

            for(Compra c : lis){
           generaDetalleFactura(nroFactura , c.getIdSerie() , c.getPrecio() , c.getCantidad());
            }
        }
    }

    public void generaDetalleFactura(int nroFactura,int nroSerie , double precio,int cantidad ){
        String enlace = url+"?tag=consulta8&nroFactura="+nroFactura+"&nroSerie="+nroSerie+"&precio="+precio+"&cantidad="+cantidad;
        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                          String genera =  response.getString("dato").toString();


                            if(genera.equalsIgnoreCase("SI")){
                                Toast.makeText(MainProcesar.this, "Generando Detalle..!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("erxx",""+error.getMessage().toString());
            }
        });

        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jsobj);

    }

    public void generaNroFactura(int idCliente,double total){
        String enlace = url+"?tag=consulta6&idCliente="+idCliente+"&montoTotal="+total;
        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            generoFactura = response.getString("dato").toString();
                            Log.w("res", generoFactura.toString());




                            if(generoFactura.equalsIgnoreCase("SI")){
                                Toast.makeText(MainProcesar.this, "Generando Factura..!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("erxx",""+error.getMessage().toString());
            }
        });

        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jsobj);

    }

    public int NroFactura(){
        nro = 0;
        String enlace = url+"?tag=consulta7";

        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
int numero;
                        try {
                            nroFactura = response.getString("dato");
                            Log.w("res", nroFactura.toString());

                            Toast.makeText(MainProcesar.this, "Mostrando Numero >: "+nroFactura, Toast.LENGTH_SHORT).show();

                            ((Carrito)getApplication()).setId_factura(Integer.parseInt(nroFactura));

                            nro = Integer.parseInt(nroFactura.toString());

                            Toast.makeText(context, "Guardado : "+nro, Toast.LENGTH_SHORT).show();
                        } catch (JSONException ex) {
                            Toast.makeText(MainProcesar.this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("erxx",""+error.getMessage().toString());
            }

        });

        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jsobj);

        Toast.makeText(context, "Salio : "+nro, Toast.LENGTH_SHORT).show();


        return nro;
    }

    public void cerrarSesion(View v){
        ((Carrito)this.getApplication()).setUser(null);
        Intent it=new Intent(MainProcesar.this,MainCarrito.class);
        startActivity(it);

    }

}
