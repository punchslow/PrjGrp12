import java.time.LocalDate;

public class Venta {
    private LocalDate fecha;
    private int posicion_producto;
    private Maquina maquinaAsociada;

    public Venta(int posicion_producto, LocalDate fecha, Maquina maquinaAsociada) {
        this.posicion_producto = posicion_producto;
        this.maquinaAsociada = maquinaAsociada;
        this.fecha = fecha;
    }

    public static Venta registrarVenta(int posicion_producto, Maquina maquina) {
        return registrarVenta(posicion_producto, maquina, LocalDate.now());
    }

    // Actualizada para crear una instancia de Venta desde un contexto estático y devolver esa instancia
    public static Venta registrarVenta(int pos_producto, Maquina maquina, LocalDate fecha) {
        if(maquina == null) {
            throw new IllegalArgumentException("La máquina no puede ser nula.");
        }
        if(pos_producto < 0 || !maquina.getPosiciones().contains(pos_producto)) {
            throw new IllegalArgumentException("Posición inválida.");
        }
        if(fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        Venta venta = new Venta(pos_producto, fecha, maquina);
        maquina.actualizarInventario(venta);
        System.out.println("Registrada venta: " + venta);


        return venta;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public int getPosicionProducto() {
        return this.posicion_producto;
    }
    
    @Override 
    public String toString() {
    	String nombre_producto = maquinaAsociada.getStock(posicion_producto).getProducto().getNombre();
    	return "{" + fecha + "; venta de un " + nombre_producto + ", en la posicion " + posicion_producto + "}";
    }
}
