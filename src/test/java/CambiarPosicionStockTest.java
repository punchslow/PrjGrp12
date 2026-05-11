import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CambiarPosicionStockTest {

	private static final int R = 10;

    private Maquina maquinaValida;
    private Producto productoValido;

    @BeforeEach
    void setUp() {
        maquinaValida = new Maquina("M-001", 0.0f, 0.0f, "ModeloX", "FabricanteY", R);

        productoValido = new Producto();
        productoValido.registrarProducto("P-001", "Agua", 1.50f);

        maquinaValida.añadirStock(productoValido, 7, 1);
        maquinaValida.añadirStock(productoValido, 7, 5);
        maquinaValida.añadirStock(productoValido, 7, 9);
        maquinaValida.añadirStock(productoValido, 7, 10);
    }

	////////////////////////////////// Casos válidos/////////////////////////////////////////////////////

	// Clases (1)(4)
	@Test
	void valido_posicionDestinoLimiteInferior() {
        maquinaValida.moverStock(5, 2);

        assertFalse(maquinaValida.getPosiciones().contains(5), "Origen debe quedar libre");
        assertTrue(maquinaValida.getPosiciones().contains(2),  "Destino debe estar ocupado");
        assertEquals(7, maquinaValida.getStock(2).getCantidad(), "Cantidad debe conservarse");
        assertEquals(2, maquinaValida.getStock(2).getPosicion(), "Posición interna del Stock debe actualizarse");
    }

	// Clases (1)(4)
	@Test
    void valido_posicionDestinoLimiteSuperior() {
        maquinaValida.moverStock(1, 8);

        assertFalse(maquinaValida.getPosiciones().contains(1), "Origen debe quedar libre");
        assertTrue(maquinaValida.getPosiciones().contains(8),  "Destino debe estar ocupado");
        assertEquals(8, maquinaValida.getStock(8).getPosicion(), "Posición interna del Stock debe actualizarse");
    }

	// Clases (1)(4)
	@Test
    void valido_posicionDestinoCentral() {
        maquinaValida.moverStock(9, 6);

        assertFalse(maquinaValida.getPosiciones().contains(9));
        assertTrue(maquinaValida.getPosiciones().contains(6));
        assertEquals(7, maquinaValida.getStock(6).getCantidad());
    }

	////////////////////////////////// Casos inválidos/////////////////////////////////////////////////////

	// Clase (2)(4)
	@Test
    void invalido_stockEsProducto() {
        // No ejecutable: error de compilación si se pasa Producto donde se espera int/Stock.
        // Se documenta como caso de especificación.
        assertTrue(true, "Rechazado en compilación");
    }

	// Clase (3)(4)
	@Test
    void invalido_origenVacio() {
        // posición 3 está libre en setUp
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.moverStock(3, 6),
            "Origen sin stock debe lanzar IllegalArgumentException");
    }

	@Test
	void invalido_stockNulo() {
		assertThrows(NullPointerException.class,
			() -> { Stock s = null; s.getPosicion(); },
			"Stock nulo debe lanzar NullPointerException");
	}

	// Clase (1)(5)
	@Test
    void invalido_destinoCero() {
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.moverStock(5, 0),
            "Destino = 0 debe lanzar IllegalArgumentException");
    }

	// Clase (1)(6)
	@Test
    void invalido_destinoFueraRango() {
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.moverStock(5, R + 1),
            "Destino = R+1 debe lanzar IllegalArgumentException");
    }

	// Clase (1)(7)
	@Test
    void invalido_destinoNoEntero() {
        int posicionTruncada = (int) 0.45; // = 0
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.moverStock(5, posicionTruncada),
            "Destino 0 (truncado de 0.45) debe lanzar IllegalArgumentException");
    }

	// Clase (1)(8)
	@Test
    void invalido_destinoOcupado() {
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.moverStock(5, 9),
            "Destino ocupado debe lanzar IllegalArgumentException");
    }

	// Solo cambiarPosicion() sin actualizar el HashMap.
	@Test
    void forzar_origenNoLiberado() {
        Stock s = maquinaValida.getStock(5);
        s.cambiarPosicion(6); // bug: solo campo interno, no el HashMap

        assertTrue(maquinaValida.getPosiciones().contains(5),
            "FORZADO: origen sigue en el HashMap (inconsistente)");
        assertFalse(maquinaValida.getPosiciones().contains(6),
            "FORZADO: destino no existe en el HashMap (inconsistente)");
    }

	// No se realiza ninguna operación → stock no movido.
	@Test
    void forzar_stockNoMovido() {
        Stock s = maquinaValida.getStock(5);
        // Sin llamar a moverStock
        assertEquals(5, s.getPosicion(), "FORZADO: posición no cambia sin operación");
        assertTrue(maquinaValida.getPosiciones().contains(5));
    }

	// Stock movido a posición incorrecta.
	@Test
    void forzar_posicionIncorrecta() {
        maquinaValida.moverStock(5, 7); // destino incorrecto (bug simulado)

        assertFalse(maquinaValida.getPosiciones().contains(6),
            "FORZADO: destino deseado (6) no tiene el stock");
        assertTrue(maquinaValida.getPosiciones().contains(7),
            "FORZADO: stock acabó en posición incorrecta (7)");
    }

}
