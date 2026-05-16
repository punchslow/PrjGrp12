import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ObtenerStockTest {

	private static Maquina maquina;
	private Producto producto1 = mock(Producto.class);
	private Producto producto2 = mock(Producto.class);
	private static int rango = 20;
	
	@BeforeAll
	static void iniciacion() {
		maquina = new Maquina("m001", 14.05f, -1.09f, "model", "Vending", rango);
	}
	
	@Test
	void testObtenerStockVaĺido() {
		maquina.añadirStock(producto1, 10, 1);
		maquina.añadirStock(producto2, 10, 20);
		
		assertAll(
			() -> {assertEquals(producto1, maquina.getStock(1).getProducto());},
			() -> {assertEquals(producto2, maquina.getStock(20).getProducto());}
		);
	}
	
	@Test
	void testObtenerStockNuloVaĺido() {
		assertNull(maquina.getStock(2));
	}
	
	@Test
	void testObtenerStockMaquinaNula() {
		Maquina m = null;
		assertThrows(NullPointerException.class, () -> m.getStock(10));
	}
	
	@ParameterizedTest
	@CsvSource({"0", "21"})
	void testObtenerStockPosicionInvalida(int posicion) {
		assertNull(maquina.getStock(posicion));
	}

}
