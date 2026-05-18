

/* CP-009 */

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

public class ActualizarVentasMaquinaTest {
	private static final int R = 20;

	private static final Producto productoTest = mock(Producto.class);
	private static final Venta ventaTest = mock(Venta.class);
	private static final Maquina maquinaTest = new Maquina("TEST",34.2f,-57.5f,"TEST","TEST",R);
	// Para poder añadir stock en posiciones ilegales
	private static final Stock stockTest = mock(Stock.class);

	private static int POS_PRODUCTO;
	private static int CANTIDAD_PRODUCTO;


	static {
		when(productoTest.getId()).thenReturn("TEST-PROD-1");
		when(productoTest.getNombre()).thenReturn("Patatas fritas");

		when(ventaTest.getFecha()).thenReturn(LocalDate.of(2020,1,1));

		when(stockTest.getProducto()).thenReturn(productoTest);
	}


	// Casos válidos

	@Test
	public void actualizarVentaValida1() {
		POS_PRODUCTO = 1;
		CANTIDAD_PRODUCTO = 1;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		maquinaTest.añadirStock(productoTest,CANTIDAD_PRODUCTO,POS_PRODUCTO);
		maquinaTest.actualizarInventario(ventaTest);

		maquinaTest.mostrarHistoricoVentas();

		assertEquals(maquinaTest.getStock(POS_PRODUCTO).getCantidad(),CANTIDAD_PRODUCTO - 1);
		// La cantidad es 0, así que siempre debe saltar el aviso de cantidad baja
		assertTrue(maquinaTest.getStock(POS_PRODUCTO).cantidadBaja(),"La cantidad debe estar por debajo del límite de cantidad baja");
		assertTrue(maquinaTest.getVentas().contains(ventaTest));
	}

	@Test
	public void actualizarVentaValidaR() {
		POS_PRODUCTO = R;
		CANTIDAD_PRODUCTO = 12;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		maquinaTest.añadirStock(productoTest,CANTIDAD_PRODUCTO,POS_PRODUCTO);
		maquinaTest.actualizarInventario(ventaTest);

		assertEquals(maquinaTest.getStock(POS_PRODUCTO).getCantidad(),CANTIDAD_PRODUCTO - 1);
		// La cantidad es demasiado alta para que se alcance el límite
		assertFalse(maquinaTest.getStock(POS_PRODUCTO).cantidadBaja(),"La cantidad debe estar por encima del límite de cantidad baja");
		assertTrue(maquinaTest.getVentas().contains(ventaTest));
	}

	@Test
	public void actualizarVentaValidaR2() {
		POS_PRODUCTO = Math.ceilDiv(R,2);
		CANTIDAD_PRODUCTO = 12;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		maquinaTest.añadirStock(productoTest,CANTIDAD_PRODUCTO,POS_PRODUCTO);
		maquinaTest.actualizarInventario(ventaTest);

		assertEquals(maquinaTest.getStock(POS_PRODUCTO).getCantidad(),CANTIDAD_PRODUCTO - 1);
		// La cantidad es demasiado alta para que se alcance el límite
		assertFalse(maquinaTest.getStock(POS_PRODUCTO).cantidadBaja(),"La cantidad debe estar por encima del límite de cantidad baja");
		assertTrue(maquinaTest.getVentas().contains(ventaTest));
	}


	/**
	 * Prueba que el aviso de cantidad baja es correcto
	 * Crea un nuevo producto de prueba para evitar errores
	 * Va creando ventas del producto hasta que el producto deba estar en cantidad baja
	 * Crea 2 stocks con el mismo producto para probar el funcionamiento correcto
	 */
	@Test
	public void cantidadBajaTest() {
		Producto producto_test_2 = mock(Producto.class);
		when(producto_test_2.getId()).thenReturn("TEST-PROD-2");
		when(producto_test_2.getNombre()).thenReturn("Patatas fritas 2");

		int POS_PRODUCTO_STOCK_1 = R/3;
		int CANTIDAD_PRODUCTO_STOCK_1 = 123;

		int POS_PRODUCTO_STOCK_2 = 4*R/7;
		int CANTIDAD_PRODUCTO_STOCK_2 = 2;

		maquinaTest.getStock().clear();

		maquinaTest.añadirStock(producto_test_2,CANTIDAD_PRODUCTO_STOCK_1,POS_PRODUCTO_STOCK_1);
		maquinaTest.añadirStock(producto_test_2,CANTIDAD_PRODUCTO_STOCK_2,POS_PRODUCTO_STOCK_2);

		// Tras el bucle, el stock 1 tendrá 3 uds. y el stock 2, 2.
		// Al haber 2 stocks del producto, esto es una velocidad de consumo estimada de 2 uds/día
		// Por tanto, el stock 2 estará en límite de cantidad baja, pero el 1 no.
		for(int i=0; i<120; i++) {
			Venta venta_mock = mock(Venta.class);
			// Venta del stock 1 del producto
			when(venta_mock.getPosicionProducto()).thenReturn(POS_PRODUCTO_STOCK_1);
			// Reparte las ventas a lo largo de los últimos 29 días
			when(venta_mock.getFecha()).thenReturn(LocalDate.now().minusDays(i * 29 / 120));

			maquinaTest.actualizarInventario(venta_mock);

			// Asegura que tiene el valor correcto y que la venta se registra
			assertEquals(maquinaTest.getStock(POS_PRODUCTO_STOCK_1).getCantidad(),CANTIDAD_PRODUCTO_STOCK_1 - i - 1);
			// La cantidad es demasiado alta para que se alcance el límite
			assertFalse(maquinaTest.getStock(POS_PRODUCTO_STOCK_1).cantidadBaja(),"La cantidad debe estar por encima del límite de cantidad baja");
			assertTrue(maquinaTest.getVentas().contains(venta_mock));
		}


		assertTrue(maquinaTest.getStock(POS_PRODUCTO_STOCK_2).cantidadBaja(),"La cantidad debe estar por debajo del límite de cantidad baja");

	}




	// Casos inválidos
	// Máquina o venta inválidas
	@Test
	public void actualizarVentaMaquinaNull() {
		POS_PRODUCTO = R;
		CANTIDAD_PRODUCTO = 3;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		Maquina otraMaquinaTest = null;

		assertThrows(NullPointerException.class, () -> otraMaquinaTest.actualizarInventario(ventaTest));
	}

	@Test
	public void actualizarVentaVentaNull() {
		POS_PRODUCTO = Math.ceilDiv(5*R,6);
		CANTIDAD_PRODUCTO = 1;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		Venta otraVentaTest = null;

		maquinaTest.añadirStock(productoTest,CANTIDAD_PRODUCTO,POS_PRODUCTO);

		// El acceso a null en la condición del if resulta en una IllegalArgumentException. En cualquier caso, no es una venta válida y lanza una excepción.
		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(otraVentaTest));

	}



	// Posiciones fuera de rango
	@Test
	public void actualizarVentaPos0() {
		POS_PRODUCTO = 0;
		CANTIDAD_PRODUCTO = 1;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);
		when(stockTest.getCantidad()).thenReturn(CANTIDAD_PRODUCTO);
		when(stockTest.getPosicion()).thenReturn(POS_PRODUCTO);

		maquinaTest.getStock().put(POS_PRODUCTO,stockTest);

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(ventaTest));
	}

	@Test
	public void actualizarVentaPosRMas1() {
		POS_PRODUCTO = R+1;
		CANTIDAD_PRODUCTO = 1;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);
		when(stockTest.getCantidad()).thenReturn(CANTIDAD_PRODUCTO);
		when(stockTest.getPosicion()).thenReturn(POS_PRODUCTO);

		maquinaTest.getStock().put(POS_PRODUCTO,stockTest);

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(ventaTest));
	}




	// Posiciones vacías
	@Test
	public void actualizarVentaPosNoExistente() {
		POS_PRODUCTO = R/2;
		CANTIDAD_PRODUCTO = 1;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		int OTRA_POS_PRODUCTO = 2*R/3;

		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);

		maquinaTest.añadirStock(productoTest,CANTIDAD_PRODUCTO,OTRA_POS_PRODUCTO);

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(ventaTest));
	}

	@Test
	public void actualizarVentaPosVacia() {
		POS_PRODUCTO = R/2;
		CANTIDAD_PRODUCTO = 0;

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();


		when(ventaTest.getPosicionProducto()).thenReturn(POS_PRODUCTO);
		when(stockTest.getCantidad()).thenReturn(CANTIDAD_PRODUCTO);
		when(stockTest.getPosicion()).thenReturn(POS_PRODUCTO);

		maquinaTest.getStock().put(POS_PRODUCTO,stockTest);

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(ventaTest));
	}








}
