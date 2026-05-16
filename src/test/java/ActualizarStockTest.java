
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ActualizarStockTest {

    // Casos válidos

    @Test
    void actualizarCantidadValida() {

        Producto p = new Producto();
        p.registrarProducto("P101", "Coca-Cola", 2.5f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);
        Stock s = new Stock(m, p, 10, 1);

        s.actualizarCantidad(8);

        assertEquals(8, s.getCantidad());
    }

    @Test
    void actualizarCantidadEnLimiteCantidadBaja() {

        Producto p = new Producto();
        p.registrarProducto("P201", "Agua", 0.01f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);
        Stock s = new Stock(m, p, 10, 2);

        s.actualizarCantidad(5);

        assertEquals(5, s.getCantidad());
    }

    @Test
    void actualizarCantidadPorDebajoDelLimite() {

        Producto p = new Producto();
        p.registrarProducto("P003", "Nestea", 2.0f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);
        Stock s = new Stock(m, p, 10, 3);

        s.actualizarCantidad(2);

        assertEquals(2, s.getCantidad());
    }

    // Cantidad inválida

    @Test
    void actualizarCantidadACero() {

        Producto p = new Producto();
        p.registrarProducto("P004", "Oreo", 1.5f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);
        Stock s = new Stock(m, p, 10, 4);

        assertThrows(
            IllegalArgumentException.class,
            () -> s.actualizarCantidad(0)
        );
    }

    @Test
    void actualizarCantidadANegativa() {

        Producto p = new Producto();
        p.registrarProducto("P005", "KitKat", 2.2f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);
        Stock s = new Stock(m, p, 10, 5);

        assertThrows(
            IllegalArgumentException.class,
            () -> s.actualizarCantidad(-3)
        );
    }

    // Stock inválido

    @Test
    void crearStockConProductoNull() {

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);

        assertThrows(
            IllegalArgumentException.class,
            () -> new Stock(m, null, 10, 1)
        );
    }

    @Test
    void crearStockConMaquinaNull() {

        Producto p = new Producto();
        p.registrarProducto("P006", "Aquarius", 1.8f);

        assertThrows(
            IllegalArgumentException.class,
            () -> new Stock(null, p, 10, 1)
        );
    }

    @Test
    void crearStockConCantidadNegativa() {

        Producto p = new Producto();
        p.registrarProducto("P007", "Pringles", 3.0f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);

        assertThrows(
            IllegalArgumentException.class,
            () -> new Stock(m, p, -5, 1)
        );
    }

    @Test
    void crearStockConPosicionInvalida() {

        Producto p = new Producto();
        p.registrarProducto("P008", "Twix", 1.9f);

        Maquina m = new Maquina("M001", 42.8805f, -8.5457f, "ModeloX", "FabricanteY", 20);

        assertThrows(
            IllegalArgumentException.class,
            () -> new Stock(m, p, 10, 0)
        );
    }
}
