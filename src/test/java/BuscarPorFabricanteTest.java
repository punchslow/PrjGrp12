import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuscarPorFabricanteTest {


    // Válidas
    @Test
    public void testBuscarFabricanteValido1() {
        maquinaDAO dao = new maquinaDAO();

        Maquina m1 = dao.crear("1",0,0,"_","abc",50);
        Maquina m2 = dao.crear("2",0,0,"_","abc",50);
        dao.crear("3",0,0,"_","qwerty",50);
        dao.crear("4",0,0,"_","SNATCH",50);
        dao.crear("5",0,0,"_","EMPRESA",50);
        dao.crear("6",0,0,"_","___",50);

        List<Maquina> maquinas = dao.buscarPorFabricante("abc");

        assertAll(
                () -> assertEquals(maquinas.size(),2),
                () -> assertTrue(maquinas.contains(m1)),
                () -> assertTrue(maquinas.contains(m2))
        );

    }

    @Test
    public void testBuscarFabricanteValido2() {
        maquinaDAO dao = new maquinaDAO();

        dao.crear("1",0,0,"_","abc",50);
        dao.crear("2",0,0,"_","abc",50);
        dao.crear("3",0,0,"_","qwerty",50);
        dao.crear("4",0,0,"_","SNATCH-PERO-NO",50);
        dao.crear("5",0,0,"_","EMPRESA",50);
        dao.crear("6",0,0,"_","___",50);

        List<Maquina> maquinas = dao.buscarPorFabricante("SNATCH");

        assertTrue(maquinas.isEmpty());
    }


    // Inválidas
    @Test
    public void testBuscarFabricanteDAONull() {
        maquinaDAO dao = null;

        assertThrows(NullPointerException.class, () -> dao.buscarPorFabricante("EMPRESA"));
    }

    @Test
    public void testBuscarFabricanteVacio() {
        maquinaDAO dao = new maquinaDAO();

        dao.crear("1",0,0,"_","abc",50);
        dao.crear("2",0,0,"_","abc",50);
        dao.crear("3",0,0,"_","qwerty",50);
        dao.crear("4",0,0,"_","SNATCH",50);
        dao.crear("5",0,0,"_","EMPRESA",50);
        dao.crear("6",0,0,"_","___",50);

        assertThrows(IllegalArgumentException.class, () -> dao.buscarPorFabricante(null));
    }

    @Test
    public void testBuscarFabricanteNull() {
        maquinaDAO dao = new maquinaDAO();

        dao.crear("1",0,0,"_","abc",50);
        dao.crear("2",0,0,"_","abc",50);
        dao.crear("3",0,0,"_","qwerty",50);
        dao.crear("4",0,0,"_","SNATCH",50);
        dao.crear("5",0,0,"_","EMPRESA",50);
        dao.crear("6",0,0,"_","___",50);

        assertThrows(IllegalArgumentException.class, () -> dao.buscarPorFabricante(null));
    }


    

}
