package com.example.pry_tiendaanime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import clases.Serie;

public class MainSerie extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public JSONArray lista;  //la lista q trae el gson del servidor
    List<Serie> lisSerie;
    ListView lis2;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";

    TextView texto;
    int codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);
        texto = (TextView)findViewById(R.id.txtSeries);
        String nom=getIntent().getStringExtra("nombreCat");
        codigo = getIntent().getIntExtra("datoCodigo" ,0 );
        texto.setText("SERIE "+nom.toUpperCase());
        lis2=(ListView)findViewById(R.id.list2);
       LlenaSerie();

    }

    public void LlenaSerie(){
        lisSerie=new ArrayList();
        String enlace = url+"?tag=consulta2&code="+codigo;
        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            lista = response.getJSONArray("dato");
                            Log.w("res", lista.toString());
                            for(int f=0;f<lista.length();f++){
                                JSONObject ah=(JSONObject)lista.get(f);
                                Serie s = new Serie();
                                s.setIdSerie(ah.getInt("cods"));
                                s.setIdCategoria(ah.getInt("codc"));
                                s.setNombreSerie(ah.getString("nome"));
                                byte[] decodedString = Base64.decode(ah.getString("foto"), Base64.DEFAULT);
                                final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                s.setImgSerie(decodedByte);
                                lisSerie.add(s);
                            }
                            AdaptaSerie dp = new AdaptaSerie(MainSerie.this,0,lisSerie);

                            lis2.setAdapter(dp);

                            lis2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    int codigo = lisSerie.get(position).getIdSerie();

                                    //    Toast.makeText(getApplicationContext(), "Codigo : "+codigo , Toast.LENGTH_LONG).show();
                                    Intent it=new Intent(MainSerie.this,MainDetalle.class);
                                    it.putExtra("CodigoSerie",codigo);
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

    public void irCarrito(View v){
        if(((Carrito)getApplication()).getUser() == null){
            Intent it=new Intent(MainSerie.this, MainCarrito.class);
            startActivity(it);
        }else{
            Intent it=new Intent(MainSerie.this,  MainProcesar.class);
            startActivity(it);
        }
    }
}
