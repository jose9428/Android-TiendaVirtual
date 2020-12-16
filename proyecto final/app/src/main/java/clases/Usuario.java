package clases;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Application {
   int code;
   String nombres;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }


}
