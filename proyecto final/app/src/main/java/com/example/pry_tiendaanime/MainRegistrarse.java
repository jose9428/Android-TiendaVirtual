package com.example.pry_tiendaanime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import clases.Carrito;
import clases.Cliente;
import clases.Serie;

public class MainRegistrarse extends AppCompatActivity {
    JsonObjectRequest jsobj;
    public String  lista;
    private String url="http://10.0.2.2:8083//pry_bdanime/Controla.php";
    TextView dni;
    TextView nombres;
    TextView apeP;
    TextView apeM;
    TextView direccion;
    TextView telefono;
    TextView email;
    TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        dni =(TextView)findViewById(R.id.txtDNIRegistro);
        nombres =(TextView)findViewById(R.id.txtNombresRegistro);
        apeP =(TextView)findViewById(R.id.txtApellidoPaternoRegistro);
        apeM =(TextView)findViewById(R.id.txtApellidoMaternoRegistro);
        direccion =(TextView)findViewById(R.id.txtDireccionRegistro);
        telefono =(TextView)findViewById(R.id.txtTelefonoRegistro);
        email =(TextView)findViewById(R.id.txtEmailRegistro);
        pass =(TextView)findViewById(R.id.txtContraseñaRegistro);

        dni.setText("19345644");
        nombres.setText("Raul");
        apeP.setText("Cardenas");
        apeM.setText("Rodriguez");
        direccion.setText("San Juan");
        telefono.setText("987567123");
        email.setText("raul@gmail.com");
        pass.setText("123456");


    }

    public void registrarse(View v){
        String DNI = dni.getText().toString().trim();
        String nome = nombres.getText().toString().trim();
        String paterno = apeP.getText().toString().trim();
        String materno = apeM.getText().toString().trim();
        String direcc = direccion.getText().toString().trim();
        String tel = telefono.getText().toString().trim();
        String correo = email.getText().toString().trim();
        String contrasenia = pass.getText().toString().trim();


        if(validarCampos()){
            Cliente c = new Cliente();
            c.setDni(DNI);
            c.setNombres(nome);
            c.setApePaterno(paterno);
            c.setApeMaterno(materno);
            c.setDireccion(direcc);
            c.setTelefono(tel);
            c.setCorreo(correo);
            c.setContrasenia(contrasenia);
            procesarRegistro(c);

        }
    }

    public void procesarRegistro(Cliente c){

        String enlace = url+"?tag=consulta5&dni="+c.getDni()+"&nombres="+c.getNombres()+"&apeP="+c.getApePaterno()+"&apeM="+c.getApeMaterno()+"&direcc="+c.getDireccion()+"&telefono="+c.getTelefono()+"&correo="+c.getCorreo()+"&pass="+c.getContrasenia();
       // String enlace = url+"?tag=consulta7";

        jsobj=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            lista = response.getString("dato").toString();
                            Log.w("res", lista.toString());

                            if(lista.equalsIgnoreCase("SI")){
                                Toast.makeText(MainRegistrarse.this, "El cliente se registro correctamente", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainRegistrarse.this, "A ocurrido un error al procesar los datos", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException ex) {
                            Toast.makeText(MainRegistrarse.this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    public boolean validarCampos(){
        String DNI = dni.getText().toString().trim();
        String nome = nombres.getText().toString().trim();
        String paterno = apeP.getText().toString().trim();
        String materno = apeM.getText().toString().trim();
        String direcc = direccion.getText().toString().trim();
        String tel = telefono.getText().toString().trim();
        String correo = email.getText().toString().trim();
        String contrasenia = pass.getText().toString().trim();

        if(DNI.length()<=0){
            Toast.makeText(this, "El campo del DNI se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(nome.length()<=0){
            Toast.makeText(this, "El campo del nombre se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(paterno.length()<=0){
            Toast.makeText(this, "El campo del apellido Paterno se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(materno.length()<=0){
            Toast.makeText(this, "El campo del apellido Materno se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(direcc.length()<=0) {
            Toast.makeText(this, "El campo de la direccion se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(tel.length()<=0) {
            Toast.makeText(this, "El campo del Telefono se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(correo.length()<=0) {
            Toast.makeText(this, "El campo del Correo se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }else if(contrasenia.length()<=0) {
            Toast.makeText(this, "El campo del Contraseña se encuentra vacio.!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void irCarrito(View v){
        if(((Carrito)getApplication()).getUser() == null){
            Intent it=new Intent(MainRegistrarse.this, MainCarrito.class);
            startActivity(it);
        }else{
            Intent it=new Intent(MainRegistrarse.this,  MainProcesar.class);
            startActivity(it);
        }
    }

    public void inciarSesion(View v){
        Intent it=new Intent(MainRegistrarse.this, MainLogin.class);
        startActivity(it);
    }
}
