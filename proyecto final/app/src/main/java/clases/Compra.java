package clases;

public class Compra extends Serie {
    private int cantidad;

    public double Total(){
        return getCantidad() * super.getPrecio();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
