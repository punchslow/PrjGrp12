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
        if (id == null || id.isBlanck()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlanck()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public void establecerPrecio (float nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El nuevo precio debe ser positivo.");
        }
        this.precio = nuevoPrecio;
    }
}
