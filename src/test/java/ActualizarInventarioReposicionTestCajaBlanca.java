import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActualizarInventarioReposicionTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",30);
    private static Reposicion reposicionMock = mock(Reposicion.class);

    private int[] POSICIONES_PRODUCTO,CANTIDADES_REPOSICION,CANTIDADES_PRODUCTO;


    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");

        when(reposicionMock.getFecha()).thenReturn(LocalDate.now());

        // Hace que la velocidad de consumo del producto sea elevada
        maquinaTest.añadirStock(productoMock,1000,1);
        for(int i=0; i<300; i++) new Venta(1,maquinaTest);
    }



    @Test
    public void camino12() {
        maquinaTest.getStock().clear();

        POSICIONES_PRODUCTO = new int[]{1, 3, 500};
        CANTIDADES_REPOSICION = new int[]{3};

        when(reposicionMock.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
        when(reposicionMock.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

        assertThrows(IllegalArgumentException.class, () -> maquinaTest.actualizarInventario(reposicionMock));
    }

    @Test
    public void camino3() {
        maquinaTest.getStock().clear();

        POSICIONES_PRODUCTO = new int[]{1, 15, 30};
        CANTIDADES_REPOSICION = new int[]{1,1,1};
        CANTIDADES_PRODUCTO = new int[]{1,1,1};

        when(reposicionMock.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
        when(reposicionMock.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

        for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
            maquinaTest.añadirStock(productoMock,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
        }

        maquinaTest.actualizarInventario(reposicionMock);

        for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
            assertEquals(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).getCantidad(), CANTIDADES_REPOSICION[i] + CANTIDADES_PRODUCTO[i]);
            assertTrue(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).cantidadBaja());
        }
    }


    @Test
    public void camino4() {
        maquinaTest.getStock().clear();

        POSICIONES_PRODUCTO = new int[]{1, 15, 30};
        CANTIDADES_REPOSICION = new int[]{1,1,1};
        CANTIDADES_PRODUCTO = new int[]{500,500,500};

        when(reposicionMock.getPosicionesAsociados()).thenReturn(POSICIONES_PRODUCTO);
        when(reposicionMock.getCantidades()).thenReturn(CANTIDADES_REPOSICION);

        for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
            maquinaTest.añadirStock(productoMock,CANTIDADES_PRODUCTO[i], POSICIONES_PRODUCTO[i]);
        }

        maquinaTest.actualizarInventario(reposicionMock);

        for(int i = 0; i< POSICIONES_PRODUCTO.length; i++) {
            assertEquals(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).getCantidad(), CANTIDADES_REPOSICION[i] + CANTIDADES_PRODUCTO[i]);
            assertFalse(maquinaTest.getStock(POSICIONES_PRODUCTO[i]).cantidadBaja());
        }
    }
}
