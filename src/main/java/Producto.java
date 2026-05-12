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

    public void registrarProducto(String id, String nombre, float precio) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío.");
        }
        if (nombre == null || nombre.isEmpty()) {
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
    
    /*
    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Producto otro = (Producto) obj;

        return this.getId() == otro.getId();
    }
    
    @Override
    public int hashCode() {
    	
        int hash = 7; 
        
        hash = 31 * hash + (getId() == null ? 0 : getId().hashCode());
        
        return hash;
    } */

}
