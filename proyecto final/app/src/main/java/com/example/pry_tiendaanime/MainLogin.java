package com.example.pry_tiendaanime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.List;

import clases.Carrito;
import clases.Cliente;
import clases.Compra;
import clases.Usuario;

public class MainLogin extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public JSONArray lista;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";
    EditText email;
    EditText pass;
    List<Cliente> usuarioLogeado;

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.txtEmailUsuario);
        pass = (EditText)findViewById(R.id.txtContraseñaUsuario);
        email.setText("maria01@gmail.com");
        pass.setText("123456");

        // Crear Archivo

        /*
        SharedPreferences sharprefs = getSharedPreferences("logeado",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharprefs.edit();
        editor.putString("cliente","");
        */

    }

    public void irCarrito(View v){
        if(((Carrito)getApplication()).getUser() == null){
            Intent it=new Intent(MainLogin.this, MainCarrito.class);
            startActivity(it);
        }else{
            Intent it=new Intent(MainLogin.this,  MainProcesar.class);
            startActivity(it);
        }
    }

    public void iniciarSesion(View v){
        String usuario = email.getText().toString().trim();
        String contrasenia = pass.getText().toString().trim();

        if(usuario.isEmpty() && contrasenia.isEmpty()){
            Toast.makeText(this, "Los campos se encuentran vacios.", Toast.LENGTH_SHORT).show();
        }else if(usuario.isEmpty()){
            Toast.makeText(this, "El campo del email se encuentra vacio.", Toast.LENGTH_SHORT).show();
        }else if(contrasenia.isEmpty()){
            Toast.makeText(this, "El campo de la contraseña se encuentra vacio.", Toast.LENGTH_SHORT).show();
        }else{
            validarLogin(usuario , contrasenia);
        }

    }

    public void validarLogin(String correo,String contra){
        String enlace = url+"?tag=consulta4&correo="+correo+"&pass="+contra;

        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Cliente c = null;
                        try {
                            lista = response.getJSONArray("dato");
                            Log.w("res", lista.toString());
                            if(lista.length()>0){
                                c = new Cliente();
                                JSONObject ah=(JSONObject)lista.get(0);
                                c.setCodeCliente(ah.getInt("codc"));
                                c.setDni(ah.getString("dni"));
                                c.setNombres(ah.getString("nombre"));
                                c.setApePaterno(ah.getString("apeP"));
                                c.setApeMaterno(ah.getString("apeM"));
                                c.setDireccion(ah.getString("direccion"));
                                c.setTelefono(ah.getString("telefono"));
                                c.setCorreo(ah.getString("correo"));
                                c.setContrasenia(ah.getString("pass"));

                            }

                            if(c == null){
                                Toast.makeText(getApplicationContext(), "Correo y/o Contraseña incorrecto.!!", Toast.LENGTH_LONG).show();
                            }else{
                                // Logeado Correctamente
                               GuardarCliente(c);

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

    public void GuardarCliente(Cliente c){
        /*
        SharedPreferences sharpref = getSharedPreferences("logeado",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharpref.edit();
        editor.putBoolean("myBoolean",true);
        editor.putString("cliente",c.getNombres());
        editor.commit();
        finish();
        */

      // usuarioLogeado = ((Usuario)this.getApplication()).getLista();
       // usuarioLogeado.add(c);

       ((Carrito)this.getApplication()).setUser(c.getNombres());
       ((Carrito)this.getApplication()).setIdCliente(c.getCodeCliente());
        ((Carrito)this.getApplication()).setApeP(c.getApePaterno());

        Intent it=new Intent(MainLogin.this, MainProcesar.class);
        startActivity(it);
    }

    public void Registrarse(View v){

        Intent it=new Intent(MainLogin.this, MainRegistrarse.class);
        startActivity(it);
    }



}
