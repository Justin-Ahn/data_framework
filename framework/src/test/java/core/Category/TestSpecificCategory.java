package core.Category;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import core.Category.SpecificCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tianyugu on 4/8/17.
 */
public class TestSpecificCategory {

    @Test
    public void testSpecificCategory1(){
        SpecificCategory c = new SpecificCategory("t1","n1");
        assertEquals("t1",c.getType());

    }
}

