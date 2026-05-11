import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegistroProductoTest {

    // Casos válidos

    @Test
    void registrarProductoValido() {

        Producto p = new Producto();

        p.registrarProducto("P101", "Coca-Cola", 2.5f);

        assertEquals("P101", p.getId());
        assertEquals("Coca-Cola", p.getNombre());
        assertEquals(2.5f, p.getPrecio());
    }

    @Test
    void cambiarPrecioValido1() {

        Producto p = new Producto();

        p.registrarProducto("P201", "Agua", 0.01f);
        p.establecerPrecio(1.0f);

        assertEquals(1.0f, p.getPrecio());
    }

	@Test
    void cambiarPrecioValido2() {

        Producto p = new Producto();

        p.registrarProducto("P305", "Café", 1.3f);
        p.establecerPrecio(1.3f);

        assertEquals(1.3f, p.getPrecio());
    }


    // ID inválido

    @Test
    void registrarProductoConIdVacio() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto("", "Agua", 1.0f)
        );
    }

    @Test
    void registrarProductoConIdNull() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto(null, "KitKat", 2.5f)
        );
    }

    // Nombre inválido
    
    @Test
    void registrarProductoConNombreVacio() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto("P003", "", 0.9f)
        );
    }

    @Test
    void registrarProductoConNombreNull() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto("P004", null, 2.5f)
        );
    }

    // Precio inválido

    @Test
    void registrarProductoConPrecioCero() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto("P005", "Oreo", 0f)
        );
    }

    @Test
    void registrarProductoConPrecioNegativo() {

        Producto p = new Producto();

        assertThrows(
            IllegalArgumentException.class,
            () -> p.registrarProducto("P006", "Nestea", -2.5f)
        );
    }
    
    // Nuevo precio inválido

    @Test
    void cambiarPrecioACero() {

        Producto p = new Producto();

        p.registrarProducto("P008", "Manzana", 2.5f);

        assertThrows(
            IllegalArgumentException.class,
            () -> p.establecerPrecio(0f)
        );
    }

    @Test
    void cambiarPrecioANegativo() {

        Producto p = new Producto();

        p.registrarProducto("P009", "Plátano", 2.2f);

        assertThrows(
            IllegalArgumentException.class,
            () -> p.establecerPrecio(-2.5f)
        );
    }
}
