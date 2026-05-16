import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ModificarCantidadStockTest {

	private static Maquina maquina = mock(Maquina.class);
	private static Producto producto = mock(Producto.class);
	private static Stock s;
	
	@BeforeAll
	static void iniciacion() {
		when(maquina.diasHastaAgotar(any(Stock.class))).thenReturn(10.0f);
		when(maquina.getRango()).thenReturn(50);
		when(producto.getNombre()).thenReturn("mock");
		
		s = new Stock(maquina,producto,7,10);
	}
	
	@Test
	void testModificarCantidadValido() {
		s.actualizarCantidad(0);
		assertEquals(0,s.getCantidad());
	}

	@Test
	void testModificarCantidadStockNulo() {
		Stock s = null;
		assertThrows(NullPointerException.class, () -> s.actualizarCantidad(5));
	}

	@Test
	void testModificarCantidadNula() {
		assertThrows(IllegalArgumentException.class, () -> s.actualizarCantidad(-1));
	}
	
}
