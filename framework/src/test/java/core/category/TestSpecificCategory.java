package core.category;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by tianyugu on 4/8/17.
 */
public class TestSpecificCategory {

    @Test
    public void testSpecificCategory1(){
        DataCategory c = new DataCategory("t1","n1");
        assertEquals("t1",c.getType());

        assertEquals("n1",c.getName());

        DataCategory d = new DataCategory("t1","n1");
        assertTrue(c.equals(d));

    }
}

