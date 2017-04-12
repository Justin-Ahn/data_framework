package core.category;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by tianyugu on 4/8/17.
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

