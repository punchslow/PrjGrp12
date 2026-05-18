import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrarReposicionTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",6);
    private static LocalDate fecha = LocalDate.of(2026,4,5);
    private int[] POSICIONES_PRODUCTO,CANTIDADES_REPOSICION;

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
        POSICIONES_PRODUCTO = null;
        CANTIDADES_REPOSICION = new int[]{1};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        fecha
                )
        );
        assertEquals(
                "Debe haber al menos un producto.",
                e.getMessage()
        );
    }

    @Test
    public void camino2() {
        POSICIONES_PRODUCTO = new int[]{};
        CANTIDADES_REPOSICION = new int[]{};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        fecha
                )
        );
        assertEquals(
                "Debe haber al menos un producto.",
                e.getMessage()
        );
    }


    @Test
    public void camino3() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = null;
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        fecha
                )
        );
        assertEquals(
                "Debe haber al menos una cantidad.",
                e.getMessage()
        );
    }

    @Test
    public void camino4() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = new int[]{};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        fecha
                )
        );
        assertEquals(
                "Debe haber al menos una cantidad.",
                e.getMessage()
        );
    }


    @Test
    public void camino5() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = new int[]{2,2};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        fecha
                )
        );
        assertEquals(
                "Productos y cantidades deben tener el mismo tamaño.",
                e.getMessage()
        );
    }


    @Test
    public void camino6() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = new int[]{2};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        null,
                        fecha
                )
        );
        assertEquals(
                "La máquina no debe ser nula.",
                e.getMessage()
        );
    }

    @Test
    public void camino7() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = new int[]{2};
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> Reposicion.registrarReposicion(
                        POSICIONES_PRODUCTO,
                        CANTIDADES_REPOSICION,
                        maquinaTest,
                        null
                )
        );
        assertEquals(
                "La fecha no debe ser nula.",
                e.getMessage()
        );
    }


    @Test
    public void camino9() {
        POSICIONES_PRODUCTO = new int[]{1};
        CANTIDADES_REPOSICION = new int[]{2};

        maquinaTest.añadirStock(productoMock,2,1);

        Reposicion.registrarReposicion(
            POSICIONES_PRODUCTO,
            CANTIDADES_REPOSICION,
            maquinaTest,
            fecha
        );

        assertEquals(
                4,
                maquinaTest.getStock(1).getCantidad()
        );

    }





}
