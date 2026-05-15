import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("CP-004: Registro de stock en máquina")
public class RegistroStockTest {

	private Maquina maquinaValida;
    private Producto productoValido;
    private int posicionLibre;

    @BeforeEach
    void setUp() {
        maquinaValida = new Maquina("M001", (float)42.23, (float)-8.71, "Saeco", "Vending Lucus",30);
        productoValido = new Producto();
        productoValido.registrarProducto("P001", "Agua", 1.50f);  
        posicionLibre = 1; // posición que no tiene stock asignado
    }

	// Caso válido -----------------------------------------------------------------------------------------
	@DisplayName("CP-004 Válido: stock registrado correctamente")
    @Test
    void testStockRegistradoCorrectamente() {
        // Arrange
        int cantidad = 10;

        // Act
        maquinaValida.añadirStock(productoValido, cantidad, posicionLibre);
		Stock stock = maquinaValida.getStock(posicionLibre);

        // Assert
        assertNotNull(stock, "El stock no debe ser nulo");
        assertEquals(productoValido, stock.getProducto(), "El producto debe coincidir");
        assertEquals(cantidad, stock.getCantidad(), "La cantidad debe coincidir");
        assertEquals(posicionLibre, stock.getPosicion(), "La posición debe coincidir");
        assertEquals(stock, maquinaValida.getStock(posicionLibre),"El stock debe estar asociado a la posición en la máquina");
    }

	// No válidos -----------------------------------------------------------------------------------------------
	// Máquina --------------------
	@DisplayName("CP-004 Inválido: máquina nula")
    @Test
    void testStockMaquinaNula() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(null, productoValido, 5, posicionLibre),"Debe lanzar excepción con máquina nula");
    }

	//Producto ----------------
	@DisplayName("CP-004 Inválido: producto nulo")
    @Test
    void testStockProductoNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(maquinaValida, null, 5, posicionLibre),"Debe lanzar excepción con producto nulo");
    }

	// Cantidad -----------
	@DisplayName("CP-004 Válido: cantidad cero (borde inferior válido)")
    @Test
    void testStockCantidadCero() {
        // El constructor acepta cantidad >= 0 según su validación actual
        // Act
        Stock stock = new Stock(maquinaValida, productoValido, 0, posicionLibre);

        // Assert
        assertEquals(0, stock.getCantidad());
    }

    @DisplayName("CP-004 Inválido: cantidad negativa")
    @Test
    void testStockCantidadNegativa() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Stock(maquinaValida, productoValido, -1, posicionLibre), "Debe lanzar excepción con cantidad negativa");
    }

	// Posición ---------------
	@DisplayName("CP-004 Inválido: posición nula")
    @Test
    void testStockPosicionNula() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(maquinaValida, productoValido, 5, null),
                "Debe lanzar excepción con posición nula");
    }

    @DisplayName("CP-004 Inválido: posición cero")
    @Test
    void testStockPosicionCero() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(maquinaValida, productoValido, 5, 0),
                "Debe lanzar excepción con posición 0");
    }

    @DisplayName("CP-004 Inválido: posición negativa")
    @Test
    void testStockPosicionNegativa() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Stock(maquinaValida, productoValido, 5, -3),
                "Debe lanzar excepción con posición negativa");
    }
	
	@DisplayName("CP-004 Inválido: posición ya ocupada en la máquina")
	@Test
	void testStockPosicionOcupada() {
		// Arrange 
		maquinaValida.añadirStock(productoValido, 5, posicionLibre);

		// Act & Assert
		assertThrows(IllegalArgumentException.class,
				() -> maquinaValida.añadirStock(productoValido, 3, posicionLibre),"Debe lanzar excepción al intentar ocupar una posición ya usada");}
}
