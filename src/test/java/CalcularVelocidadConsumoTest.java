import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalcularVelocidadConsumoTest {

	private Maquina maquina;
	private int rango = 30;
	private Producto prod = mock(Producto.class);
	
	
	@BeforeEach
	void iniciacion() {
		maquina = new Maquina("m001", 12.9f, 23.0f, "m", "f", rango);
		
		maquina.añadirStock(prod, 50, 1);
		maquina.añadirStock(prod, 50, 2);
		maquina.añadirStock(prod, 50, 3);
	}
	
	@ParameterizedTest
	@CsvSource({"0,6","5,0","23,8"})
	void testCalcularVelocidadValido(int numVentasRecientes, int numVentasPasadas) {
		
		when(prod.getId()).thenReturn("mock");
		when(prod.getNombre()).thenReturn("mock");
		
		for (int i = 0; i < numVentasRecientes; i++) {
			new Venta(i%3 + 1,maquina);
		}
		
		for (int i = 0; i < numVentasPasadas; i++) {
			new Venta(i%3 + 1,maquina,LocalDate.parse("2024-05-06"));
		}
		
		float velocidad = maquina.calcularVelocidadConsumo(prod);
		
		assertTrue(velocidad == ((float)numVentasRecientes)/30.0f);
	}
	
	@Test
	void testCalcularVelocidadNulos() {
		Maquina m = null;
		Producto p = null;
		
		assertAll(
			() -> {assertThrows(NullPointerException.class, () -> m.calcularVelocidadConsumo(prod));},
			() -> {assertEquals(0, maquina.calcularVelocidadConsumo(p));}
		);
	}
	
	

}
