import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ventaDAO {
    private final List<Venta> ventas = new ArrayList<>();

    public Venta crear(int posicionProducto, Maquina maquina) {
        return crear(posicionProducto, maquina, LocalDate.now());
    }

    public Venta crear(int posicionProducto, Maquina maquina, LocalDate fecha) {
        Venta v = Venta.registrarVenta(posicionProducto, maquina, fecha);
        ventas.add(v);
        return v;
    }

    public List<Venta> obtenerTodas() {
        return List.copyOf(ventas);
    }

    public List<Venta> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return ventas.stream()
                .filter(v -> !v.getFecha().isBefore(desde) && !v.getFecha().isAfter(hasta))
                .toList();
    }

    public Optional<Venta> buscarPorIndice(int indice) {
        if (indice < 0 || indice >= ventas.size()) return Optional.empty();
        return Optional.of(ventas.get(indice));
    }

    public void actualizar(int indice, Venta ventaNueva) {
        if (indice < 0 || indice >= ventas.size())
            throw new IndexOutOfBoundsException("Índice de venta fuera de rango: " + indice);
        ventas.set(indice, ventaNueva);
    }

    public boolean eliminar(int indice) {
        if (indice < 0 || indice >= ventas.size())
            throw new IndexOutOfBoundsException("Índice de venta fuera de rango: " + indice);
        ventas.remove(indice);
        return true;
    }
}
