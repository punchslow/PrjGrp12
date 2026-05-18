import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class ConsultarInventarioTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",6);



    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");
    }

    @Test
    public void camino1234_1Iter() {
        maquinaTest.getStock().clear();

        maquinaTest.añadirStock(productoMock,4,6);

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        maquinaTest.consultarInventario();

        try {
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String output = s.hasNext() ? s.next() : "";
            assertEquals(
                    """
                            [Posición: 6,Producto: Producto_test,Cantidad: 4]\r
                            """
                    ,output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void camino1234_2Iter() {
        maquinaTest.getStock().clear();
        maquinaTest.añadirStock(productoMock,4,6);
        maquinaTest.añadirStock(productoMock,8,2);

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        maquinaTest.consultarInventario();

        try {
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String output = s.hasNext() ? s.next() : "";
            assertEquals(
                    """
                            [Posición: 2,Producto: Producto_test,Cantidad: 8]\r
                            [Posición: 6,Producto: Producto_test,Cantidad: 4]\r
                            """
                    ,output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void camino1234_4Iter() {
        maquinaTest.getStock().clear();
        maquinaTest.añadirStock(productoMock,4,6);
        maquinaTest.añadirStock(productoMock,8,2);
        maquinaTest.añadirStock(productoMock,2,3);
        maquinaTest.añadirStock(productoMock,80,1);

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        maquinaTest.consultarInventario();

        try {
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String output = s.hasNext() ? s.next() : "";
            assertEquals(
                    """
                            [Posición: 1,Producto: Producto_test,Cantidad: 80]\r
                            [Posición: 2,Producto: Producto_test,Cantidad: 8]\r
                            [Posición: 3,Producto: Producto_test,Cantidad: 2]\r
                            [Posición: 6,Producto: Producto_test,Cantidad: 4]\r
                            """
                    ,output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void camino1234_5Iter() {
        maquinaTest.getStock().clear();
        maquinaTest.añadirStock(productoMock,4,6);
        maquinaTest.añadirStock(productoMock,8,2);
        maquinaTest.añadirStock(productoMock,2,3);
        maquinaTest.añadirStock(productoMock,5,5);
        maquinaTest.añadirStock(productoMock,80,1);

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        maquinaTest.consultarInventario();

        try {
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String output = s.hasNext() ? s.next() : "";
            assertEquals(
                    """
                            [Posición: 1,Producto: Producto_test,Cantidad: 80]\r
                            [Posición: 2,Producto: Producto_test,Cantidad: 8]\r
                            [Posición: 3,Producto: Producto_test,Cantidad: 2]\r
                            [Posición: 5,Producto: Producto_test,Cantidad: 5]\r
                            [Posición: 6,Producto: Producto_test,Cantidad: 4]\r
                            """
                    ,output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void camino124() {
        maquinaTest.getStock().clear();

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        maquinaTest.consultarInventario();

        try {
            // Comprueba que no quedan bytes por leer
            assertEquals(
                    0
                    ,input.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
