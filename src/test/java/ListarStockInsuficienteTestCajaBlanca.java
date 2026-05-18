import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ListarStockInsuficienteTestCajaBlanca {
    private static Producto productoMock = mock(Producto.class);
    private static Maquina maquinaTest = new Maquina("test",0,0,"modelo","fabricante",60);

    static {
        when(productoMock.getId()).thenReturn("Producto_test");
        when(productoMock.getNombre()).thenReturn("Producto_test");
    }

    @Test
    public void camino1() {
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

        List<Stock> insuficientes = maquinaTest.listarStocksInsuficientes();

        try {
            // Comprueba que no quedan bytes por leer
            assertEquals(
                    0
                    ,input.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(insuficientes.isEmpty());
    }

    @Test
    public void camino3() {
        maquinaTest.getStock().clear();

        HashMap<Integer,Stock> stocks = maquinaTest.getStock();
        for(int i=0; i<10; i++) {
            Stock mockStock = mock(Stock.class);
            when(mockStock.cantidadBaja()).thenReturn(false);
            stocks.put(i, mockStock);
        }

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Stock> insuficientes = maquinaTest.listarStocksInsuficientes();

        try {
            // Comprueba que no quedan bytes por leer
            assertEquals(
                    0
                    ,input.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(insuficientes.isEmpty());

    }



    @Test
    public void camino4() {
        maquinaTest.getStock().clear();

        HashMap<Integer,Stock> stocks = maquinaTest.getStock();
        Set<Stock> stockSet = new HashSet<>();
        StringBuilder expectedOutput = new StringBuilder();
        for(int i=0; i<10; i++) {
            Stock mockStock = mock(Stock.class);
            when(mockStock.cantidadBaja()).thenReturn(true);
            stocks.put(i, mockStock);
            stockSet.add(mockStock);
            expectedOutput.append(mockStock).append("\r\n");
        }

        // Captura el standard output en un archivo para poder recuperarlo después
        FileInputStream input;
        try {
            File outputFile = new File("output-redir");
            System.setOut(new PrintStream(outputFile));
            input = new FileInputStream("output-redir");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Stock> insuficientes = maquinaTest.listarStocksInsuficientes();


        Scanner s = new Scanner(input).useDelimiter("\\A");
        String output = s.hasNext() ? s.next() : "";
        assertEquals(
                expectedOutput.toString()
                ,output);


        assertTrue(insuficientes.containsAll(stockSet));

    }
}
