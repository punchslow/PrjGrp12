
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ActualizarReposicionesMaquinaTest {

	private static final int R = 20;

	private static final Producto productoTest = mock(Producto.class);
	private static final Reposicion reposicionTest = mock(Reposicion.class);
	private static final Maquina maquinaTest = new Maquina("TEST",34.2f,-57.5f,"TEST","TEST",R);
	// Para poder añadir stock en posiciones ilegales
	private static final Stock stockTest = mock(Stock.class);

	private static int[] POSICIONES_PRODUCTO;
	private static int[] CANTIDADES_REPOSICION;
	private static int[] CANTIDADES_PRODUCTO;


	static {
		when(productoTest.getId()).thenReturn("TEST-PROD-1");
		when(productoTest.getNombre()).thenReturn("Patatas fritas");

		when(stockTest.getProducto()).thenReturn(productoTest);
	}

	//Válidas
	@Test
	public void actualizarRepoValida1() {
		POSICIONES_PRODUCTO = new int[]{1, 3, R};
		CANTIDADES_REPOSICION = new int[]{3, 2, 40};
		CANTIDADES_PRODUCTO = new int[]{1,2,3};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
		}

		maquinaTest.actualizarInventario(reposicionTest);

		maquinaTest.mostrarHistoricoReposiciones();

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			assertEquals(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).getCantidad(), CANTIDADES_REPOSICION[i] + CANTIDADES_PRODUCTO[i]);
		}
		assertTrue(maquinaTest.getReposiciones().contains(reposicionTest));
	}

	@Test
	public void actualizarRepoValida2() {
		POSICIONES_PRODUCTO = new int[]{1};
		CANTIDADES_REPOSICION = new int[]{1};
		CANTIDADES_PRODUCTO = new int[]{1};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
		}

		// Reduce cantidades a 0
		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			new Venta(POSICIONES_PRODUCTO[i], maquinaTest);
		}

		maquinaTest.actualizarInventario(reposicionTest);

		maquinaTest.mostrarHistoricoReposiciones();

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			assertEquals(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).getCantidad(), CANTIDADES_REPOSICION[i] + CANTIDADES_PRODUCTO[i] - 1); // -1 por la venta
		}
		assertTrue(maquinaTest.getReposiciones().contains(reposicionTest));
	}


	//Inválidas
	@Test
	public void actualizarRepoInvalidaMaquinaNULL() {
		assertThrows(NullPointerException.class, () -> ((Maquina) null).actualizarInventario(reposicionTest));
	}

	@Test
	public void actualizarRepoInvalidaRepoNULL() {
		POSICIONES_PRODUCTO = new int[]{R};
		CANTIDADES_REPOSICION = new int[]{1};
		CANTIDADES_PRODUCTO = new int[]{1};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();


		assertThrows(IllegalArgumentException.class,() -> maquinaTest.actualizarInventario((Reposicion) null));
	}

	@Test
	public void actualizarRepoInvalidaPosBaja() {
		POSICIONES_PRODUCTO = new int[]{0};
		CANTIDADES_REPOSICION = new int[]{4};
		CANTIDADES_PRODUCTO = new int[]{3};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			when(stockTest.getPosicion()).thenReturn(POSICIONES_PRODUCTO[i]);
			when(stockTest.getCantidad()).thenReturn(CANTIDADES_REPOSICION[i]);

			maquinaTest.getStock().put(POSICIONES_PRODUCTO[i],stockTest);
		}

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}

	@Test
	public void actualizarRepoInvalidaPosAlta() {
		POSICIONES_PRODUCTO = new int[]{R+1};
		CANTIDADES_REPOSICION = new int[]{3};
		CANTIDADES_PRODUCTO = new int[]{12};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			when(stockTest.getPosicion()).thenReturn(POSICIONES_PRODUCTO[i]);
			when(stockTest.getCantidad()).thenReturn(CANTIDADES_REPOSICION[i]);

			maquinaTest.getStock().put(POSICIONES_PRODUCTO[i],stockTest);
		}

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}


	@Test
	public void actualizarRepoInvalidaPosInexistente() {
		POSICIONES_PRODUCTO = new int[]{5};
		CANTIDADES_REPOSICION = new int[]{9};
		CANTIDADES_PRODUCTO = new int[]{4};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);


		int OTRA_POS = 6;


		maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[0], OTRA_POS);


		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}


	@Test
	public void actualizarRepoInvalidaLong0() {
		POSICIONES_PRODUCTO = new int[]{};
		CANTIDADES_REPOSICION = new int[]{};
		CANTIDADES_PRODUCTO = new int[]{};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
		}


		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}


	@Test
	public void actualizarRepoInvalidaCantNoPositivas() {
		POSICIONES_PRODUCTO = new int[]{4,6,10};
		CANTIDADES_REPOSICION = new int[]{0,-6,5};
		CANTIDADES_PRODUCTO = new int[]{6,6,6};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
		}

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}


	@Test
	public void actualizarRepoInvalidaArraysDistintoTamano() {
		POSICIONES_PRODUCTO = new int[]{2,7,4};
		CANTIDADES_REPOSICION = new int[]{1};
		CANTIDADES_PRODUCTO = new int[]{2,2,2};

		// Empezar todos los tests desde 0
		maquinaTest.getStock().clear();

		when(reposicionTest.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
		when(reposicionTest.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

		for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
			maquinaTest.añadirStock(productoTest,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
		}

		assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionTest));
	}



}
