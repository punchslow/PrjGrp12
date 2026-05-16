import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class maquinaDAO {
    private final List<Maquina> maquinas = new ArrayList<>();

    public Maquina crear(String id, float longitud, float latitud, String modelo, String fabricante, int rango) {
        if (buscarPorId(id).isPresent())
            throw new IllegalArgumentException("Ya existe una máquina con id: " + id);

        Maquina m = new Maquina(id, longitud, latitud, modelo, fabricante, rango);
        maquinas.add(m);
        return m;
    }

    public List<Maquina> obtenerTodas() {
        return List.copyOf(maquinas);
    }

    public Optional<Maquina> buscarPorId(String id) {
        return maquinas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public List<Maquina> buscarPorFabricante(String fabricante) {
        return maquinas.stream()
                .filter(m -> m.getFabricante().equals(fabricante))
                .toList();
    }

    public void añadirStock(String idMaquina, Producto producto, int cantidad, int posicion) {
        Maquina m = buscarPorId(idMaquina)
                .orElseThrow(() -> new IllegalArgumentException("Máquina no encontrada: " + idMaquina));
        m.añadirStock(producto, cantidad, posicion);
    }

    public boolean eliminar(String id) {
        return maquinas.removeIf(m -> m.getId().equals(id));
    }
}
