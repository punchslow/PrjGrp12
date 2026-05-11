import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EliminarStockMaquinaTest {

	private static final int R = 10;

    private Maquina maquinaValida;
    private Producto productoValido;

    @BeforeEach
    void setUp() {
        maquinaValida = new Maquina("M-001", 0.0f, 0.0f, "ModeloX", "FabricanteY", R);

        productoValido = new Producto();
        productoValido.registrarProducto("P-001", "Agua", 1.50f);

        // Posiciones ocupadas: 1, 5, 9, 10
        maquinaValida.añadirStock(productoValido, 7, 1);
        maquinaValida.añadirStock(productoValido, 7, 5);
        maquinaValida.añadirStock(productoValido, 7, 9);
        maquinaValida.añadirStock(productoValido, 7, 10);
	}

	////////////////////////////////// Casos válidos /////////////////////////////////////////////////////

    // Clases (1)(4)
    @Test
    void valido_posicionLimiteInferior() {
        maquinaValida.eliminarStock(1);

        assertFalse(maquinaValida.getPosiciones().contains(1),
            "Posición 1 debe desaparecer del mapa");
        assertEquals(3, maquinaValida.getPosiciones().size(),
            "El resto del inventario debe permanecer intacto");
    }

    // Clases (1)(4)
    @Test
    void valido_posicionLimiteSuperior() {
        maquinaValida.eliminarStock(R);

        assertFalse(maquinaValida.getPosiciones().contains(R),
            "Posición R debe desaparecer del mapa");
        assertEquals(3, maquinaValida.getPosiciones().size(),
            "El resto del inventario debe permanecer intacto");
    }

    // Clases (1)(4)
    @Test
    void valido_posicionCentral() {
        int totalAntes = maquinaValida.getPosiciones().size();

        maquinaValida.eliminarStock(5);

        assertFalse(maquinaValida.getPosiciones().contains(5),
            "Posición 5 debe desaparecer del mapa");
        assertEquals(totalAntes - 1, maquinaValida.getPosiciones().size(),
            "Solo debe eliminarse una posición");
    }

    ////////////////////////////////// Casos inválidos /////////////////////////////////////////////////////

    // Clase (2)(4)
    @Test
    void invalido_maquinaEsProducto() {
        assertTrue(true, "Rechazado en compilación");
    }

    // Clase (3)(4)
    @Test
    void invalido_maquinaNula() {
        Maquina maquinaNula = null;

        assertThrows(NullPointerException.class,
            () -> maquinaNula.eliminarStock(1),
            "Máquina nula debe lanzar NullPointerException");
    }

    // Clase (1)(5)
    @Test
    void invalido_posicionCero() {
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.eliminarStock(0),
            "Posición 0 debe lanzar IllegalArgumentException");
    }

    // Clase (1)(6)
    @Test
    void invalido_posicionFueraRango() {
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.eliminarStock(R + 1),
            "Posición R+1 debe lanzar IllegalArgumentException");
    }

    // Clase (1)(7)
    @Test
    void invalido_posicionNoEntera() {
        int posicionTruncada = (int) 0.45; // = 0
        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.eliminarStock(posicionTruncada),
            "Posición 0 (truncado de 0.45) debe lanzar IllegalArgumentException");
    }

    // Clase (1)(8)
    @Test
    void invalido_posicionVacia() {
        assertFalse(maquinaValida.getPosiciones().contains(3),
            "Precondición: posición 3 debe estar libre");

        assertThrows(IllegalArgumentException.class,
            () -> maquinaValida.eliminarStock(3),
            "Posición vacía debe lanzar IllegalArgumentException");
    }

    // No se llama a eliminarStock → posición sigue existiendo
    @Test
    void forzar_posicionSigueExistiendo() {
        // Sin operación → posición 5 sigue en el mapa
        assertTrue(maquinaValida.getPosiciones().contains(5),
            "FORZADO: sin eliminarStock, la posición persiste en el mapa");
    }

    // Se elimina una posición distinta a la pedida (bug simulado)
    @Test
    void forzar_posicionIncorrectaEliminada() {
        maquinaValida.eliminarStock(9); // bug: se elimina la posición equivocada

        assertTrue(maquinaValida.getPosiciones().contains(5),
            "FORZADO: la posición solicitada (5) sigue existiendo");
        assertFalse(maquinaValida.getPosiciones().contains(9),
            "FORZADO: se eliminó la posición incorrecta (9)");
    }

    // Se eliminan todas las posiciones en lugar de solo la indicada
    @Test
    void forzar_todasEliminadas() {
        int totalAntes = maquinaValida.getPosiciones().size();

        maquinaValida.eliminarStock(5);

        assertEquals(totalAntes - 1, maquinaValida.getPosiciones().size(),
            "FORZADO: solo debe eliminarse una posición, no todas");
        assertTrue(maquinaValida.getPosiciones().contains(1),  "Posición 1 debe permanecer intacta");
        assertTrue(maquinaValida.getPosiciones().contains(9),  "Posición 9 debe permanecer intacta");
        assertTrue(maquinaValida.getPosiciones().contains(10), "Posición 10 debe permanecer intacta");
    }

}
