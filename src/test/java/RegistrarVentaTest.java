import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class RegistrarVentaTest {
	
	private static Producto mockedproducto;
	private static Maquina maquina;
	
	
	@BeforeAll
	static void iniciacion() {
		
		mockedproducto = mock(Producto.class);
        
        when(mockedproducto.getId()).thenReturn("producto-prueba");
	    when(mockedproducto.getNombre()).thenReturn("Producto");
	    
	    maquina = new Maquina("prueba", 1, -1, "modeloprueba", "fabricanteprueba", 20);

	}
	

	
	//rango de la máquina R = 20
	static Stream<Arguments> argumentos_validos(){
		return Stream.of(
				Arguments.of(1,7,"2025-12-23"),
				Arguments.of(20,7,"2025-12-23"),
				Arguments.of(10,1,"2025-12-23")
		);
	}
	@ParameterizedTest
	@MethodSource("argumentos_validos")
	void testRegistrarVentaValida(int posicion, int cantidad, String fecha) {
		
		///////////////////////////////////////////////////////////////////
        
	    //partimos de:
	    //	-	maquina válida
	    //	-	posicion no vacía
	    //	-	fecha correcta
	    
        //le añadimos previamente stock a la posición desde la que queremos realizar una venta
        maquina.añadirStock(mockedproducto, cantidad, posicion);
        
        ///////////////////////////////////////////////////////////////////
        
		Venta venta = new Venta(posicion, maquina, LocalDate.parse(fecha));

		///////////////////////////////////////////////////////////////////
		
		assertAll(
		() -> {assertTrue(maquina.getVentas().contains(venta));}, //la venta está en el historial de la máquina
		() -> {assertEquals(cantidad-1, maquina.getStock(posicion).getCantidad());} //se ha deducido un articulo
		);
		
	}
	
	
	
	@Test
	void testRegistrarVentaMaquinaInvalida() {
		
		int posicion = Math.ceilDiv(5*20, 6);
		String fecha = "2026-01-01";
		
		assertAll(
		() -> {assertThrows(IllegalArgumentException.class, () -> new Venta(posicion, null, LocalDate.parse(fecha)));},
		() -> {assertThrows(RuntimeException.class, () -> new Venta(posicion, (Maquina)new Object(), LocalDate.parse(fecha)));}
		);
		
	}
	
	
	
	static Stream<Arguments> argumentos_posicion_invalida(){
		return Stream.of(
				Arguments.of(0,30,"2022-12-23"),
				Arguments.of(21,15,"2020-04-08")
		);
	}
	@ParameterizedTest
	@MethodSource("argumentos_posicion_invalida")
	void testRegistrarVentaPosicionInvalida(int posicion, int cantidad, String fecha) {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Venta(posicion, maquina, LocalDate.parse(fecha)));
		assertEquals("Posición inválida.", exception.getMessage());
		
	}
	

	
	@Test
	void testRegistrarVentaCantidadInvalida() {
		
		int cantidad = 0;
		int posicion = Math.ceilDiv(20, 3);
		String fecha = "2023-03-19";
		
		maquina.añadirStock(mockedproducto, cantidad, posicion);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Venta(posicion, maquina, LocalDate.parse(fecha)));
		assertEquals("La cantidad no puede ser negativa.", exception.getMessage());
		
	}
	
	
	
	
	@Test
	void testRegistrarVentaFechaInvalida() {
		
		int posicion = Math.ceilDiv(20, 4);
		String fecha = "20/09/20";
		
		assertThrows(DateTimeParseException.class, () -> new Venta(posicion, maquina, LocalDate.parse(fecha)));
	}
	
}
