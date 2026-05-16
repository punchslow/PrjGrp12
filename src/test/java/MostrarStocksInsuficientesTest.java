import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MostrarStocksInsuficientesTest {

	private int rango = 30;
	@Test
	void test() {
		Maquina maquina = new Maquina("m001", 12.9f, 23.0f, "m", "f", rango);
		//espiamos la máquina para poder controlar el funcionamiento de determinadas funciones, aún teniendo el objeto real
		Maquina maquinaSpy = spy(maquina);
		
		//con espías, hay que usar doReturn al principio, en vez de thenReturn al final
		//queremos que la función de velocidad consumo siempre devuelva 2.5, para evitar usar objetos venta
		doReturn(2.5f).when(maquinaSpy).calcularVelocidadConsumo(any(Producto.class));
		
		//la máquina va a tener sus primeras 6 posiciones con Stocks de 3 productos distintos
		Stock[] stocks = {
				mock(Stock.class),
				mock(Stock.class),
				mock(Stock.class),
				mock(Stock.class),
				mock(Stock.class),
				mock(Stock.class)
		};
		
		Producto[] prods = {
				mock(Producto.class),
				mock(Producto.class),
				mock(Producto.class)
		};
		
		//para cada producto mock, devolverá "i" como identificador 
		for (int i = 0; i < 3; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(i);
			when(prods[i].getId()).thenReturn(sb.toString());
		}
		
		//mapa que contendrá los stocks mockeados con sus posiciones en la máquina
		Map<Integer, Stock> mapa = new HashMap<>();
		
		for (int i = 0; i < 6; i++) {
			when(stocks[i].getProducto()).thenReturn(prods[i%3]);
			when(stocks[i].getCantidad()).thenReturn(i*2 + 1);
			
			mapa.put(i+1, stocks[i]);
			doReturn(stocks[i]).when(maquinaSpy).getStock(i+1);
		}
		
		doReturn(mapa).when(maquinaSpy).getStock();
		
		//hasta ahora, tenemos en la máquina:
		
		//stocks[0] (cantidad = 1); stocks[3] (cantidad = 7) -> prods[0]
		//stocks[1] (cantidad = 3); stocks[4] (cantidad = 9) -> prods[1]
		//stocks[2] (cantidad = 5); stocks[5] (cantidad = 11) -> prods[2]
		
		//prods[0] -> 8 elementos, posiciones 1 y 4
		//prods[1] -> 12 elementos, posiciones 2 y 5
		//prods[2] -> 16 elementos, posiciones 3 y 6
		
		when(stocks[0].cantidadBaja()).thenReturn(true);
		when(stocks[1].cantidadBaja()).thenReturn(true);
		//los stocks en las dos primeras posiciones tendrán cantidad baja
		
		Map<Stock, Float> reponer = maquinaSpy.stocksParaReponer();
		
		//en número de días que le queda a un determinado stock se calcula de la siguiente forma:
		//dias_restantes = cantidad / (velocidad_consumo / num_stocks), donde
		//cantidad: cantidad del stock actual
		//velocidad_consumo: velocidad del consumo del producto asociado al stock
		//num_stocks: número de stocks en la máquina que contienen al mismo producto. En nuestro caso, cada producto está presente en 2 stocks distintos: num_stocks = 2, siempre
		
		assertAll(
		() -> {assertTrue(reponer.containsKey(maquinaSpy.getStock(1)));},
		() -> {assertTrue(reponer.containsKey(maquinaSpy.getStock(2)));},
		() -> {assertEquals(0.8f,reponer.get(maquinaSpy.getStock(1)));}, //0.8 = 1/(2.5/2) = cantidad_stocks / (velocidad_consumo / num_stocks)
		() -> {assertEquals(2.4f,reponer.get(maquinaSpy.getStock(2)));} //2.4 = 3/(2.5/2)
		);
	}

}
