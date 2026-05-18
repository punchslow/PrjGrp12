import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class RegistrarVentaTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",6);
    private static LocalDate fecha = LocalDate.of(2026,4,5);

    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");
    }

    @BeforeEach
    public void setUp() {
        maquinaTest.getStock().clear();
    }

    @Test
    public void camino1() {
        assertThrows(IllegalArgumentException.class, () -> new Venta(3,null,fecha));
    }

    @Test
    public void camino2() {
        assertThrows(IllegalArgumentException.class, () -> new Venta(-2,maquinaTest,fecha));
    }

    @Test
    public void camino3() {
        assertThrows(IllegalArgumentException.class, () -> new Venta(3,maquinaTest,fecha));
    }

    @Test
    public void camino4() {
        assertThrows(IllegalArgumentException.class, () -> new Venta(3,maquinaTest,null));
    }

    @Test
    public void camino5() {
        maquinaTest.añadirStock(productoMock,1,3);

        Stock stockSpy = spy(maquinaTest.getStock(3));
        doReturn(0).when(stockSpy).getCantidad();

        maquinaTest.getStock().put(3,stockSpy);

        assertThrows(IllegalArgumentException.class, () -> new Venta(3,maquinaTest,fecha));
    }

    @Test
    public void camino6() {
        maquinaTest.añadirStock(productoMock,1,3);
        new Venta(3,maquinaTest,fecha);
        assertTrue(maquinaTest.getStock(3).cantidadBaja());
    }

    @Test
    public void camino7() {
        maquinaTest.añadirStock(productoMock,10,3);
        new Venta(3,maquinaTest,fecha);
        assertFalse(maquinaTest.getStock(3).cantidadBaja());
    }
}
