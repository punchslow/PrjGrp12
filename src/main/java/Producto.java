public class Producto {
    private String id;
    private String nombre;
    private float precio;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void establecerPrecio(float precio) {
        this.precio = precio;
    }

    public void registrarProducto(String id, String nombre, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public void incrementarStock(int cantidad) {
    if (cantidad <= 0)
        throw new IllegalArgumentException("La cantidad debe ser positiva.");
    this.stock += cantidad;
}
}
