import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RegistrarMaquinaTest {

	Producto prod1 = mock(Producto.class);;
	Producto prod2 = mock(Producto.class);;
	Producto prod3 = mock(Producto.class);;
	
	Maquina maquina;
	
	@BeforeEach
	void iniciacion() {
		maquina = new Maquina("m001", 38.7784552f, -9.1212464f, "fry-box", "Nvending", 20);
	}
	
	@Test
	public void testResgistroMaquinaValida() {
		
		String salidaesperada = "";
		when(prod1.getId()).thenReturn("prod_1");
		when(prod2.getId()).thenReturn("prod_2");
		when(prod3.getId()).thenReturn("prod_3");
	
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		
		maquina.consultarInventario();
		//confirmamos que, justo tras crear la máquina, esta está vacía
		assertEquals(salidaesperada,outputStream.toString());
		
		
		maquina.añadirStock(prod1, 1, 10);
		maquina.añadirStock(prod2, 5, 1);
		maquina.añadirStock(prod3, 5, 20);
		
		assertAll(
				() -> {assertEquals(prod1,maquina.getStock(10).getProducto());},
				() -> {assertEquals(prod2,maquina.getStock(1).getProducto());},
				() -> {assertEquals(prod3,maquina.getStock(20).getProducto());},
				
				() -> {assertEquals(1,maquina.getStock(10).getCantidad());},
				() -> {assertEquals(5,maquina.getStock(1).getCantidad());},
				() -> {assertEquals(5,maquina.getStock(20).getCantidad());}

		);
		
	}
	
	@Test
	public void testResgistroMaquinaIdInvalido() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Maquina(null, 38.7784552f, -9.1212464f, "fry-box", "Nvending", 10));
		assertEquals("El id no puede ser nulo o vacío.", exception.getMessage());
		
	}
	
	@Test
	public void testRegistroMaquinaProductoNulo() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> maquina.añadirStock(null, 5, 2));
		assertEquals("El producto no puede ser nulo.", exception.getMessage());
	}

	
	@Nested
	class TestsConStock {
		
		@BeforeEach
		void iniciacion() {
			maquina.añadirStock(prod1, 5, 5);
		}
		
		@ParameterizedTest
		@CsvSource({"0,4", "5,0", "5,21", "5,5"})
		public void testRegistroMaquinaStockInvalido(int cantidad, int posicion) {
			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> maquina.añadirStock(prod1, cantidad, posicion));
			System.out.println(exception);
		}
	
	}
	

}
