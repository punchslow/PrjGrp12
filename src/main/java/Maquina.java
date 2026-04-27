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
        if(stock.containsKey(posicion))
            throw new IllegalArgumentException("Stock ya existente");
        stock.put(posicion,new Stock(this,p,cantidad,posicion));
    }

    public void eliminarStock(int posicion) {
        stock.remove(posicion);
    }

    public void consultarInventario() {
        ArrayList<Integer> posiciones = new ArrayList<>(stock.keySet());
        posiciones.sort(Integer::compareTo);

        for(Integer posicion : posiciones) {
            Stock s = stock.get(posicion);
            System.out.println("[Posición: "+s.getPosicion()+",Producto: "+s.getProducto()+",Cantidad: "+s.getCantidad()+"]");
        }
    }

    public void actualizarInventario(Venta venta) {

    }

    public void actualizarInventario(@NotNull Reposicion reposicion) {
        if(reposicion.getPosicionesAsociados().length != reposicion.getCantidades().length)
            throw new IllegalArgumentException("Reposición inválida");

        for(int i = 0; i < reposicion.getCantidades().length; i++) {
            Stock s = stock.get(reposicion.getPosicionesAsociados()[i]);
            s.actualizarCantidad(s.getCantidad() + reposicion.getCantidades()[i]);
        }

        reposiciones.add(reposicion);
    }


    public void mostrarHistoricoVentas() {

    }

    public void mostrarHistoricoReposiciones() {
        for(Reposicion reposicion : reposiciones) {
            System.out.println(reposicion);
        }
    }


    public List<Stock> listarStocksInsuficientes() {
        ArrayList<Stock> listaStocks = new ArrayList<>();
        for(Stock stock : stock.values()) {
            if(stock.cantidadBaja()) listaStocks.add(stock);
        }
        listaStocks.sort(Comparator.comparing(Stock::getPosicion));

        for(Stock stock : listaStocks) {
            System.out.println(stock);
        }

        return listaStocks;
    }

    public float calcularVelocidadConsumo(Producto producto) {


        return 0;
    }






	
}
