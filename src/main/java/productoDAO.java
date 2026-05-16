import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class productoDAO {
    private final List<Producto> productos = new ArrayList<>();

    public Producto crear(String id, String nombre, float precio) {
        if (buscarPorId(id).isPresent())
            throw new IllegalArgumentException("Ya existe un producto con id: " + id);
 
        Producto p = new Producto();
        p.registrarProducto(id, nombre, precio);
        productos.add(p);
        return p;
    }

    public List<Producto> obtenerTodos() {
        return List.copyOf(productos);
    }

    public Optional<Producto> buscarPorId(String id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void actualizarPrecio(String id, float nuevoPrecio) {
        Producto p = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        p.establecerPrecio(nuevoPrecio);
    }

    public boolean eliminar(String id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}
