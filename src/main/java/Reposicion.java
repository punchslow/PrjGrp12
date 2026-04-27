import java.time.LocalDate;
import java.util.List;

public class Reposicion {

    private Producto[] productos;
    private int[] cantidades;
    private Maquina maquina;
    private LocalDate fecha;

    private Reposicion(Producto[] productos, int[] cantidades, Maquina maquina, LocalDate fecha) {
        this.productos = productos;
        this.cantidades = cantidades;
        this.maquina = maquina;
        this.fecha = fecha;
    }

    public static Reposicion registrarReposicion(
            Producto[] productos,
            int[] cantidades,
            Maquina maquina,
            LocalDate fecha) {

        if (productos == null || productos.length == 0) {
            throw new IllegalArgumentException("Debe haber al menos un producto.");
        }
        if (cantidades == null || cantidades.length == 0) {
            throw new IllegalArgumentException("Debe haber al menos una cantidad.");
        }
        if (productos.length != cantidades.length) {
            throw new IllegalArgumentException("Productos y cantidades deben tener el mismo tamaño.");
        }
        if (maquina == null) {
            throw new IllegalArgumentException("La máquina no debe ser nula.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no debe ser nula.");
        }

        return new Reposicion(productos, cantidades, maquina, fecha);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Producto[] getProductosAsociados() {
        return productos;
    }

    public int[] getCantidades() {
        return cantidades;
    }
}