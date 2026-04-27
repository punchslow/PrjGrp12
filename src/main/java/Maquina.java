import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Maquina {

    private final String id;
    private float longitud;
    private float latitud;
    private String modelo;
    private String fabricante;
    private HashMap<Integer,Stock> stock;

    private ArrayList<Venta> ventas;
    private ArrayList<Reposicion> reposiciones;

    public Maquina(String id, float longitud, float latitud, String modelo, String fabricante) {
        this.id = id;
        this.longitud = longitud;
        this.latitud = latitud;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }


    public String getId() {return id;}
    public float getLongitud() {return longitud;}
    public float getLatitud() {return latitud;}
    public String getModelo() {return modelo;}
    public String getFabricante() {return fabricante;}

    public void añadirStock(Producto p, int cantidad, int posicion) {

    }

    public void eliminarStock(int posicion) {
        stock.remove(posicion);
    }

    public void consultarInventario() {
        ArrayList<Integer> posiciones = new ArrayList<>(stock.keySet());
        posiciones.sort(Integer::compareTo);

        for(Integer posicion : posiciones) {
            System.out.println(stock.get(posicion));
        }
    }

    public void actualizarInventario(Venta venta) {

    }

    public void actualizarInventario(@NotNull Reposicion reposicion) {
        if(reposicion.getPosicionesAsociados().length != reposicion.getCantidades().length)
            throw new IllegalArgumentException("Reposición inválida");

        for(int i = 0; i < reposicion.getCantidades().length; i++) {

        }

    }


    public void mostrarHistoricoVentas() {

    }

    public void mostrarHistoricoReposiciones() {

    }


    public List<Stock> listarStocksInsuficientes() {
        for(Stock stock : stock.values()) {

        }

        return null;
    }

    public Number calcularVelocidadConsumo(Producto producto) {


        return 0;
    }






	
}
