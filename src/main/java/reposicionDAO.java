import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class reposicionDAO {
    private final List<Reposicion> reposiciones = new ArrayList<>();

    public Reposicion crear(int[] posiciones, int[] cantidades, Maquina maquina) {
        return crear(posiciones, cantidades, maquina, LocalDate.now());
    }

    public Reposicion crear(int[] posiciones, int[] cantidades, Maquina maquina, LocalDate fecha) {
        Reposicion r = Reposicion.registrarReposicion(posiciones, cantidades, maquina, fecha);
        reposiciones.add(r);
        return r;
    }

    public List<Reposicion> obtenerTodas() {
        return List.copyOf(reposiciones);
    }

    public List<Reposicion> buscarPorFecha(LocalDate fecha) {
        return reposiciones.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .toList();
    }

    public List<Reposicion> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return reposiciones.stream()
                .filter(r -> !r.getFecha().isBefore(desde) && !r.getFecha().isAfter(hasta))
                .toList();
    }

    public Optional<Reposicion> buscarPorIndice(int indice) {
        if (indice < 0 || indice >= reposiciones.size()) return Optional.empty();
        return Optional.of(reposiciones.get(indice));
    }

    public void actualizar(int indice, Reposicion reposicionNueva) {
        if (indice < 0 || indice >= reposiciones.size())
            throw new IndexOutOfBoundsException("Índice de reposición fuera de rango: " + indice);
        reposiciones.set(indice, reposicionNueva);
    }

    public boolean eliminar(int indice) {
        if (indice < 0 || indice >= reposiciones.size())
            throw new IndexOutOfBoundsException("Índice de reposición fuera de rango: " + indice);
        reposiciones.remove(indice);
        return true;
    }
}
