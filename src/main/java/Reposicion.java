import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reposicion {

    private int[] posiciones;
    private int[] cantidades;
    private Maquina maquina;
    private LocalDate fecha;

    private static final List<Reposicion> historial = new ArrayList<>();

    private Reposicion(int[] posiciones, int[] cantidades, Maquina maquina, LocalDate fecha) {
        this.posiciones = posiciones;
        this.cantidades = cantidades;
        this.maquina = maquina;
        this.fecha = fecha;

        maquina.actualizarInventario(this);
    }

    public static Reposicion registrarReposicion(int[] posiciones, int[] cantidades, Maquina maquina) {
        return registrarReposicion(posiciones, cantidades, maquina, LocalDate.now());
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
        
        // Validación de rango de posiciones y cantidades
        int rango = maquina.getRango();
        for (int pos : posiciones) {
            if (pos <= 0 || pos > rango) {
                throw new IllegalArgumentException("Posición fuera de rango: " + pos);
            }
        }
        for (int cant : cantidades) {
            if (cant <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser positiva.");
            }
        }
        Reposicion reposicion = new Reposicion(posiciones, cantidades, maquina, fecha);
        historial.add(reposicion);

        maquina.actualizarInventario(reposicion);

        System.out.println("Registrada "+reposicion);

        return reposicion;
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


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Reposición ").append(fecha);

        for(int i=0; i<posiciones.length; i++) {
            if(!maquina.getPosiciones().contains(posiciones[i])) continue;

            builder.append("\n\t")
                    .append(maquina.getStock(posiciones[i]).getProducto().getNombre())
                    .append(" (posición ").append(posiciones[i]).append(") x").append(cantidades[i]);
        }

        return builder.toString();
    }
}