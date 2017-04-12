package core.category;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A TesterClass for Data.
 */
public class TestDataCategory {

    @Test
    public void testSpecificCategory1(){
        Data c = new Data("t1","n1");
        assertEquals("t1",c.getCategory());
        assertEquals("n1",c.getName());
        Data d = new Data("t1","n1");
        assertTrue(c.equals(d));
    }
}

