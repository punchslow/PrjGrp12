import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Maquina {

    private final String id;
    private float longitud;
    private float latitud;
    private String modelo;
    private String fabricante;
    private HashMap<Integer,Stock> stock;

    private ArrayList<Venta> ventas;
    private ArrayList<Reposicion> reposiciones;
    private int rango; // número máximo de posiciones de la máquina

    public Maquina(String id, float longitud, float latitud, String modelo, String fabricante, int rango) {
        this.id = id;
        this.longitud = longitud;
        this.latitud = latitud;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.stock = new HashMap<>();
        this.ventas = new ArrayList<>();
        this.reposiciones = new ArrayList<>();
        this.rango = rango;
    }


    public String getId() {return id;}
    public float getLongitud() {return longitud;}
    public float getLatitud() {return latitud;}
    public String getModelo() {return modelo;}
    public String getFabricante() {return fabricante;}
    // Añadido para comprobar las posiciones que tiene la máquina
    // Debería ser una función que devuelva un boolean para impedir que otras clases modifiquen el Set
    public Set<Integer> getPosiciones() {return stock.keySet();}
    public HashMap<Integer, Stock> getStock() {return stock;}
    public ArrayList<Venta> getVentas() {return ventas;}
    public ArrayList<Reposicion> getReposiciones() {return reposiciones;}
    public int getRango() {return rango;}

    public Stock getStock(Integer pos) {
        return stock.get(pos);
    }

    public void añadirStock(Producto p, int cantidad, int posicion) {
        if(stock.containsKey(posicion))
            throw new IllegalArgumentException("Stock ya existente");
        stock.put(posicion,new Stock(this,p,cantidad,posicion));
    }

    public void eliminarStock(int posicion) {
        if (posicion <= 0 || posicion > rango)
            throw new IllegalArgumentException(
                "Posición fuera de rango: debe estar entre 1 y " + rango + ".");
        if (!stock.containsKey(posicion))
            throw new IllegalArgumentException(
                "Posición vacía: no hay stock en la posición " + posicion + ".");
        stock.remove(posicion);
    }

    public void moverStock(int posOrigen, int posDestino) {
        if (posOrigen <= 0 || posOrigen > rango)
            throw new IllegalArgumentException(
                "Posición origen fuera de rango: debe estar entre 1 y " + rango + ".");
        if (!stock.containsKey(posOrigen))
            throw new IllegalArgumentException(
                "Posición origen vacía: no hay stock en la posición " + posOrigen + ".");
     
        if (posDestino <= 0 || posDestino > rango)
            throw new IllegalArgumentException(
                "Posición destino fuera de rango: debe estar entre 1 y " + rango + ".");
        if (stock.containsKey(posDestino))
            throw new IllegalArgumentException(
                "Posición destino ocupada: ya existe stock en la posición " + posDestino + ".");
     
        Stock s = stock.get(posOrigen);
        stock.remove(posOrigen);
        s.cambiarPosicion(posDestino);
        stock.put(posDestino, s);
    }

    public void consultarInventario() {
        ArrayList<Integer> posiciones = new ArrayList<>(stock.keySet());
        posiciones.sort(Integer::compareTo);

        for(Integer posicion : posiciones) {
            Stock s = stock.get(posicion);
            System.out.println("[Posición: "+s.getPosicion()+",Producto: "+s.getProducto()+",Cantidad: "+s.getCantidad()+"]");
        }
    }

    public void actualizarInventario(@NotNull Venta venta) {
        if(!stock.containsKey(venta.getPosicionProducto()))
            throw new IllegalArgumentException("Stock no existente");

    	Stock s = stock.get(venta.getPosicionProducto());
    	s.actualizarCantidad(s.getCantidad() - 1);

    	this.ventas.add(venta);
    }

    public void actualizarInventario(@NotNull Reposicion reposicion) {
        if(reposicion.getPosicionesAsociados().length != reposicion.getCantidades().length)
            throw new IllegalArgumentException("Reposición inválida");

        for(int i = 0; i < reposicion.getCantidades().length; i++) {
            Stock s = stock.get(reposicion.getPosicionesAsociados()[i]);
            s.actualizarCantidad(s.getCantidad() + reposicion.getCantidades()[i]);
        }

        this.reposiciones.add(reposicion);
    }


    public void mostrarHistoricoVentas() {
    	for(Venta venta : ventas) {
            System.out.println(venta);
        }
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

    /**
     * Muestra los stocks bajos que deben ser repuestos.
     * Sólo tiene en cuenta los stocks con cantidad baja:
     * e.g., si hay botellas de agua en las posiciones  1, 3 y 5, y la posición 1 sólo tiene una botella, cuenta la posición 1
     * @return un mapa de los stocks a reponer y el tiempo esperado hasta que se agote cada uno de ellos en días
     */
    public Map<Stock, Float> stocksParaReponer() {
        HashMap<Stock, Float> map = new HashMap<>();

        for(Stock stock: listarStocksInsuficientes()) {
            float diasHastaAgotar = diasHastaAgotar(stock);
            map.put(stock,diasHastaAgotar);
        }

        return map;
    }

    /**
     * Calcula los días estimados hasta que se agote un stock
     * @param stock el stock para calcular
     * @return el número de días estimado hasta que se agote
     */
    public float diasHastaAgotar(Stock stock) {
        int cantidad = stock.getCantidad(); // Cantidad disponible actualmente
        Producto p = stock.getProducto(); // Producto en esta posición. Utilizado para calcular la velocidad de consumo

        // La velocidad se divide entre el número de stocks con el producto porque se asume que se reparte equitativamete entre ellos
        // No debería haber error si la velocidad es 0, ya que devuelve +inf
        return cantidad / (calcularVelocidadConsumo(p) / stocksDeProducto(p));
    }



    /**
     * Número de stocks de la máquina con el producto dado
     * @param producto el producto
     * @return el número de stocks con el producto dado
     */
    private int stocksDeProducto(Producto producto) {
        // Mapea los stocks (stock.values) a sus correspondientes productos, filtra para escoger sólo los que son el producto dado y los cuenta
        return (int) stock.values().stream().map(Stock::getProducto).filter(p -> producto.getId().equals(p.getId())).count();
    }

    /**
     * Devuelve la velocidad estimada de consumo de un producto en unidades por día.
     * La velocidad es estimada por el promedio del consumo en los últimos 30 días
     * @param producto el producto
     * @return la velocidad estimada de consumo en unidades/día
     */
    public float calcularVelocidadConsumo(Producto producto) {
        LocalDate fechaActual = LocalDate.now();
        // Filtra las ventas para mostrar sólo las que se corresponden con el producto dado en los últimos 30 días
        // Luego toma el recuento y lo divide entre 30
        return ventas.stream().filter(venta ->
                this.getPosiciones().contains(venta.getPosicionProducto()) && // Garantiza que no haya errores
                this.getStock(venta.getPosicionProducto()).getProducto().getId().equals(producto.getId()) && // Probablemente sería mejor sobreescribir equals en Producto
                venta.getFecha().until(fechaActual, ChronoUnit.DAYS) <= 30
        ).count() / 30.0f;
    }


    @Override
    public String toString() {
        return this.id;
    }

	
}
