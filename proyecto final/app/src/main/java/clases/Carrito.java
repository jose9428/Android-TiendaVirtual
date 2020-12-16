package clases;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Carrito  extends Application {
    private List<Compra> lista = new ArrayList<>();
    private String user;
    String apeP;
    private int idCliente;
private int id_factura;
    public List<Compra> getLista() {
        return lista;
    }

    public void setLista(List<Compra> lista) {
        this.lista = lista;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getApeP() {
        return apeP;
    }

    public void setApeP(String apeP) {
        this.apeP = apeP;
    }


    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }
}
