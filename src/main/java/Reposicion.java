import java.time.LocalDate;
import java.util.List;

public class Reposicion {

    private int[] posiciones;
    private int[] cantidades;
    private Maquina maquina;
    private LocalDate fecha;

    private Reposicion(int[] productos, int[] cantidades, Maquina maquina, LocalDate fecha) {
        this.posiciones = productos;
        this.cantidades = cantidades;
        this.maquina = maquina;
        this.fecha = fecha;

        maquina.actualizarInventario(this);
    }

    public static Reposicion registrarReposicion(
            int[] posiciones,
            int[] cantidades,
            Maquina maquina,
            LocalDate fecha) {

        if (posiciones == null || posiciones.length == 0) {
            throw new IllegalArgumentException("Debe haber al menos un producto.");
        }
        if (cantidades == null || cantidades.length == 0) {
            throw new IllegalArgumentException("Debe haber al menos una cantidad.");
        }
        if (posiciones.length != cantidades.length) {
            throw new IllegalArgumentException("Productos y cantidades deben tener el mismo tamaño.");
        }
        if (maquina == null) {
            throw new IllegalArgumentException("La máquina no debe ser nula.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no debe ser nula.");
        }

        return new Reposicion(posiciones, cantidades, maquina, fecha);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int[] getPosicionesAsociados() {
        return posiciones;
    }

    public int[] getCantidades() {
        return cantidades;
    }
}