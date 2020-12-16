package com.example.pry_tiendaanime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import clases.Carrito;
import clases.Compra;
import clases.Serie;

public class MainDetalle extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public JSONArray lista;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";
    Serie serie;
    int codigo;
    ImageView imagen;
    TextView textoImagen;
    TextView precio;
    TextView stock;
    TextView cantidad;
    Bitmap img;
    List<Compra> lis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_detalle);
        codigo = getIntent().getIntExtra("CodigoSerie" ,0 );
        imagen = (ImageView)findViewById(R.id.txtImagenDetalle);
        textoImagen=(TextView)findViewById(R.id.tNombreSerie);
        precio=(TextView)findViewById(R.id.txtPrecio);
        cantidad=(TextView)findViewById(R.id.txtCantidad);
        stock=(TextView)findViewById(R.id.txtStock);
        cargaDatos();
    }

    public void irAInicicioCategoria(View v){
        Intent it=new Intent(MainDetalle.this,MainActivity.class);
        startActivity(it);
    }

    public void cargaDatos(){

        String enlace = url+"?tag=consulta3&code="+codigo;
        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Serie s = null;
                        try {
                            lista = response.getJSONArray("dato");
                            Log.w("res", lista.toString());
                            if(lista.length()>0){
                                s= new Serie();
                                JSONObject ah=(JSONObject)lista.get(0);
                                s.setIdSerie(ah.getInt("cods"));
                                s.setNombreSerie(ah.getString("nome"));
                                s.setPrecio(ah.getDouble("precio"));
                                s.setStock(ah.getInt("stock"));
                                byte[] decodedString = Base64.decode(ah.getString("foto"), Base64.DEFAULT);
                                final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                s.setImgSerie(decodedByte);
                            }

                            imagen.setImageBitmap(s.getImgSerie());
                            img = s.getImgSerie();
                            textoImagen.setText(String.valueOf(s.getNombreSerie()));
                            precio.setText(String.valueOf(s.getPrecio()));
                            stock.setText(String.valueOf(s.getStock()));

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

    public void agregarCarrito(View v){
        try{
            int cant = Integer.parseInt(cantidad.getText().toString());
            int stockProd = Integer.parseInt(stock.getText().toString());
            if(cant>stockProd){
                Toast.makeText(this , "Stock no disponible",Toast.LENGTH_SHORT).show();
            }else{
                if(cant==0){
                    Toast.makeText(this , "La cantidad ingresada no es valida.!",Toast.LENGTH_SHORT).show();
                }else{
                    Compra c = new Compra();
                    c.setIdSerie(codigo);
                    c.setNombreSerie(textoImagen.getText().toString());
                    c.setPrecio(Double.parseDouble(precio.getText().toString()));
                    c.setCantidad(cant);
                    c.setImgSerie(img);

                    // Guardar en una session
                    lis = ((Carrito)this.getApplication()).getLista();
                    int encontroPosicion = Buscar(lis , c);
                    if(encontroPosicion==-1){
                        lis.add(c);
                    }else{
                        int cantC = lis.get(encontroPosicion).getCantidad();
                        c.setCantidad(c.getCantidad() + cantC);
                        lis.set(encontroPosicion,c);
                    }

                    ((Carrito)this.getApplication()).setLista(lis);

                    String user = ((Carrito)getApplication()).getUser();

                    if(((Carrito)getApplication()).getUser() == null){
                        Intent it=new Intent(MainDetalle.this, MainCarrito.class);
                        startActivity(it);
                    }else{
                        Intent it=new Intent(MainDetalle.this,  MainProcesar.class);
                        startActivity(it);
                    }



                }
             }
        }catch (Exception ex){
            Toast.makeText(this , "Por favor ingrese una cantidad",Toast.LENGTH_LONG).show();
        }
    }

    public int Buscar(List<Compra>lista ,Compra c){
        for(int i = 0 ; i<lista.size();i++){
            Compra carrito = lista.get(i);
            if(carrito.getIdSerie() == c.getIdSerie()){
                return i;
            }

        }
        return -1;
    }
    public void irCarrito(View v){
        if(((Carrito)getApplication()).getUser() == null){
            Intent it=new Intent(MainDetalle.this, MainCarrito.class);
            startActivity(it);
        }else{
            Intent it=new Intent(MainDetalle.this,  MainProcesar.class);
            startActivity(it);
        }
    }
}
