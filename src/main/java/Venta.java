import java.time.LocalDate;

public class Venta {
    public LocalDate fecha;
    public int posicion_producto;
    public Maquina maquinaAsociada;

    public Venta() {

    }
    public void registrarVenta(int pos_producto, Maquina maquina, LocalDate fecha) {
        if(pos_producto < 0) {
            System.out.println("Error: Posición inválida.");
            return;
        }
        if(maquina == null) {
            System.out.println("Error: La máquina no puede ser nula.");
            return;
        }
        if(fecha == null) {
            System.out.println("Error: La fecha no puede ser nula.");
            return;
        }
        this.posicion_producto = pos_producto;
        this.maquinaAsociada = maquina;
        this.fecha = fecha;
        
        maquinaAsociada.actualizarInventario(this);
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
