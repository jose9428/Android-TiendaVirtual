package com.example.pry_tiendaanime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import clases.*;

public class MainActivity extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public JSONArray lista;  //la lista q trae el gson del servidor
    List<Categoria> lisCategoria;
    ListView lis1;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lis1=(ListView)findViewById(R.id.listw1);
        LlenaCategoria();
    }

    public void irCarrito(View v){
        if(((Carrito)getApplication()).getUser() == null){
            Intent it=new Intent(MainActivity.this, MainCarrito.class);
            startActivity(it);
        }else{
            Intent it=new Intent(MainActivity.this,  MainProcesar.class);
            startActivity(it);
        }
    }

    public void LlenaCategoria(){
        lisCategoria=new ArrayList();
        String enlace = url+"?tag=consulta1";
        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            lista = response.getJSONArray("dato");
                            Log.w("res", lista.toString());
                            for(int f=0;f<lista.length();f++){
                                JSONObject ah=(JSONObject)lista.get(f);
                                Categoria c = new Categoria();
                                c.setCodigo(ah.getInt("codc"));
                                c.setNombre(ah.getString("nomc"));
                                byte[] decodedString = Base64.decode(ah.getString("foto"), Base64.DEFAULT);
                                final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                c.setFoto(decodedByte);
                                lisCategoria.add(c);
                            }
                            Adapta1 dp=new Adapta1(MainActivity.this,0,lisCategoria);
                            lis1.setAdapter(dp);

                            lis1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    int codigo = lisCategoria.get(position).getCodigo();
                                    String nombre = lisCategoria.get(position).getNombre();
                              //    Toast.makeText(getApplicationContext(), "Codigo : "+codigo , Toast.LENGTH_LONG).show();
                                    Intent it=new Intent(MainActivity.this,MainSerie.class);
                                    it.putExtra("datoCodigo",codigo);
                                    it.putExtra("nombreCat",nombre);
                                    startActivity(it);
                                }
                            });


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

}
