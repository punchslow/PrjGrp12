import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToStringReposicionTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",60);
    private static Reposicion reposicionTest;

    private int[] POSICIONES_PRODUCTO,CANTIDADES_REPOSICION;
    private static LocalDate fecha = LocalDate.of(2026,4,5);

    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");

        maquinaTest.añadirStock(productoMock,1,1);
        reposicionTest = Reposicion.registrarReposicion(new int[]{1},new int[]{1},maquinaTest,fecha);
    }

    @BeforeEach
    public void setUp() {
        maquinaTest.getStock().clear();
    }


    @Test
    public void camino1() {
        POSICIONES_PRODUCTO = new int[]{};
        CANTIDADES_REPOSICION = new int[]{};

        Reposicion reposicionSpy = spy(reposicionTest);
        doReturn(POSICIONES_PRODUCTO).when(reposicionSpy).getPosicionesAsociados();

        assertEquals(
                "Reposición "+fecha,
                reposicionSpy.toString()
        );

    }

    @Test
    public void camino2() {
        POSICIONES_PRODUCTO = new int[]{5,8,1};
        CANTIDADES_REPOSICION = new int[]{4,8,100};

        StringBuilder expected = new StringBuilder();
        expected.append("Reposición ").append(fecha);

        for(int i=0; i<POSICIONES_PRODUCTO.length; i++){
            maquinaTest.añadirStock(productoMock,1,POSICIONES_PRODUCTO[i]);
            expected.append("\n\t")
                    .append(productoMock.getNombre())
                    .append(" (posición ").append(POSICIONES_PRODUCTO[i])
                    .append(") x").append(CANTIDADES_REPOSICION[i]);
        }

        Reposicion r = Reposicion.registrarReposicion(POSICIONES_PRODUCTO,CANTIDADES_REPOSICION,maquinaTest,fecha);

        assertEquals(
                expected.toString(),
                r.toString()
        );
    }


    @Test
    public void camino3() {
        POSICIONES_PRODUCTO = new int[]{5,8,1};
        CANTIDADES_REPOSICION = new int[]{4,8,100};

        Reposicion reposicionSpy = spy(reposicionTest);
        doReturn(POSICIONES_PRODUCTO).when(reposicionSpy).getPosicionesAsociados();
        doReturn(CANTIDADES_REPOSICION).when(reposicionSpy).getPosicionesAsociados();

        assertEquals(
                "Reposición "+fecha,
                reposicionSpy.toString()
        );
    }
}
