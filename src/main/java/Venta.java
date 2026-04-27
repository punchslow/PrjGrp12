import java.time.LocalDate;

public class Venta {
    public LocalDate fecha;
    public Producto productoAsociado;
    public Maquina maquinaAsociada;

    public Venta() {

    }
    public void registrarVenta(Producto producto, Maquina maquina, LocalDate fecha) {
        if(producto == null) {
            System.out.println("Error: El producto no puede ser nulo.");
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
        this.productoAsociado = producto;
        this.maquinaAsociada = maquina;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Producto getProductoAsociado() {
        return this.productoAsociado;
    }
}
