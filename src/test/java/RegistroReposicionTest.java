import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CP-003: Registro de reposición")
class RegistroReposicionTest {
	private Maquina maquinaValida;
	private int R; 
	private LocalDate fechaValida;
	
	@BeforeEach
    void setUp() {
        maquinaValida = new Maquina("M001", (float)42.23, (float)-8.71, "Saeco", "Vending Lucus",30);
        R = maquinaValida.getRango();
        fechaValida = LocalDate.of(2025, 5, 8);

        Producto p = new Producto();
        p.registrarProducto("P001", "Agua", 1.50f);  
        for (int i = 1; i <= R; i++) {
            maquinaValida.añadirStock(p, 5, i);
        }
    }
	
	// VÁLIDOS ------------------------------------------------------------------------
	
	@DisplayName("CP-003 Válido: posición mínima (1)")
    @Test
    void testReposicionPosicionMinima() {
        // Arrange
        int[] posiciones = {1};
        int[] cantidades = {5};
        
        // Act
        Reposicion r = Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida);
        
        // Assert
        assertNotNull(r, "La reposición no debe ser nula");
        assertTrue(maquinaValida.getReposiciones().contains(r));
        assertEquals(10, maquinaValida.getStock(1).getCantidad(),"El stock de posición 1 debe haberse incrementado en 5 (5+5)");
    }
	
	@DisplayName("CP-003 Válido: posición máxima (R)")
    @Test
    void testReposicionPosicionMaxima() {
        // Arrange
        int[] posiciones = {R};
        int[] cantidades = {5};

        // Act
        Reposicion r = Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida);

        // Assert
        assertNotNull(r,"La reposición no debe ser nula");
        assertTrue(maquinaValida.getReposiciones().contains(r));
        assertEquals(10, maquinaValida.getStock(R).getCantidad(),"El stock de posición 1 debe haberse incrementado en 5 (5+5)");
    }
	
	@DisplayName("CP-003 Válido: cantidad mínima válida (1)")
    @Test
    void testReposicionCantidadMinima() {
        // Arrange
        int pos = (int) Math.ceil(R / 2.0);
        int[] posiciones = {pos};
        int[] cantidades = {1};

        // Act
        Reposicion r = Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida);

        // Assert
        assertNotNull(r);
        assertEquals(6, maquinaValida.getStock(pos).getCantidad(), "El stock debe haberse incrementado en 1 (5+1)");
    }
	
	// NO VÁLIDOS --------------------------------------------------------------
	
	// ARRAYS VACÍOS --------------------------
	@DisplayName("CP-003 Inválido: array de posiciones vacío")
    @Test
    void testReposicionPosicionesVacias() {
        // Arrange
        int[] posiciones = {};
        int[] cantidades = {};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción con arrays vacíos");
    }

	// POSICIONES --------------------------
    @DisplayName("CP-003 Inválido: posición 0")
    @Test
    void testReposicionPosicionCero() {
        // Arrange
        int[] posiciones = {0};
        int[] cantidades = {5};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción con posición 0 (fuera de rango)");
    }

    @DisplayName("CP-003 Inválido: posición negativa")
    @Test
    void testReposicionPosicionNegativa() {
        // Arrange
        int[] posiciones = {-1, 2};
        int[] cantidades = {3, 5};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción con posición negativa");
    }

    @DisplayName("CP-003 Inválido: posición mayor que R")
    @Test
    void testReposicionPosicionFueraDeRango() {
        // Arrange
        int[] posiciones = {R + 1};
        int[] cantidades = {5};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción con posición > R");
    }

    @DisplayName("CP-003 Inválido: posición muy fuera de rango")
    @Test
    void testReposicionPosicionMuyFueraDeRango() {
        // Arrange
        int[] posiciones = {R + 3};
        int[] cantidades = {2};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción");
    }
    
    // CANTIDADES ------------------------
    @DisplayName("CP-003 Inválido: cantidad cero")
    @Test
    void testReposicionCantidadCero() {
        // Arrange
        int[] posiciones = {1, 2};
        int[] cantidades = {0, 5};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción con cantidad 0");
    }
	
    // LONGITUD DE ARRAYS ------------------
    @DisplayName("CP-003 Inválido: longitudes distintas")
    @Test
    void testReposicionLongitudesDistintas() {
        // Arrange
        int[] posiciones = {1, 2};
        int[] cantidades = {3};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, fechaValida),"Debe lanzar excepción si posiciones y cantidades tienen distinta longitud");
    }
    
    //FECHA --------------------
    @DisplayName("CP-003 Inválido: fecha nula")
    @Test
    void testReposicionFechaNula() {
        // Arrange
        int[] posiciones = {1};
        int[] cantidades = {3};

        // Act & Assert
        assertThrows(IllegalArgumentException.class,() -> Reposicion.registrarReposicion(posiciones, cantidades, maquinaValida, null),"Debe lanzar excepción con fecha nula");
    }
    
    
    
}
