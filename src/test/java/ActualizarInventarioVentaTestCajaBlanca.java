import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActualizarInventarioVentaTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",30);
    private static Venta ventaMock = mock(Venta.class);

    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");

        when(ventaMock.getPosicionProducto()).thenReturn(5);
        when(ventaMock.getFecha()).thenReturn(LocalDate.now());
    }

    @Test
    public void camino12() {
        maquinaTest.getStock().clear();
        assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(ventaMock));
    }

    @Test
    public void camino1345() {
        maquinaTest.getStock().clear();
        maquinaTest.añadirStock(productoMock,4,5);

        maquinaTest.actualizarInventario(ventaMock);

        assertFalse(maquinaTest.getStock(5).cantidadBaja());
    }

    @Test
    public void camino1346() {
        maquinaTest.getStock().clear();
        maquinaTest.añadirStock(productoMock,1,5);

        maquinaTest.actualizarInventario(ventaMock);

        assertTrue(maquinaTest.getStock(5).cantidadBaja());
    }
}
