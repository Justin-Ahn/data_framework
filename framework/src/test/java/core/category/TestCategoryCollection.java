package core.category;

import static org.junit.Assert.*;

import core.data.RelationshipData;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by tianyugu on 4/8/17.
 */
public class TestCategoryCollection {


//    @Ignore
//    public void testCategoryCollection1() {
//        CategoryCollection cc = new CategoryCollection();
//        cc.addSpecificCategory("A","A1");
//        cc.addSpecificCategory("B","B1");
//        cc.addRelation("A","A1","B","B1");
//        assertTrue(cc.getAllRelation().containsKey(new DataCategory("A","A1")));
//        assertTrue(cc.getAllRelation().containsKey(new DataCategory("A","A1")));
//        assertTrue(cc.getAllRelation().get(new DataCategory("A","A1")).contains
//                (new DataCategory("B","B1")));
//        assertTrue(cc.getAllRelation().get(new DataCategory("B","B1")).contains
//                (new DataCategory("A","A1")));
//
//
//    }
//
//    @Test
//    @Ignore
//    public void testGetRelationShipData1() {
//        CategoryCollection cc = new CategoryCollection();
//        cc.addSpecificCategory("A","A1");
//        cc.addSpecificCategory("A","A2");
//        cc.addSpecificCategory("B","B1");
//        cc.addRelation("A","A1","B","B1");
//        cc.addRelation("A","A2","B","B1");
//        RelationshipData curRelation = cc.getRelationshipData("A","B");
//        assertEquals(1.0, curRelation.getStrength("A","A1","A2"),0.001);
//        assertEquals(1.0,curRelation.getStrength("A","A2","A1"),0.001);
//    }
//
//    @Test
//    @Ignore
//    public void testGetRelationShipData2() {
//        CategoryCollection cc = new CategoryCollection();
//        cc.addSpecificCategory("A","A1");
//        cc.addSpecificCategory("A","A2");
//        cc.addSpecificCategory("A","A3");
//        cc.addSpecificCategory("A","A4");
//        cc.addSpecificCategory("B","B1");
//        cc.addSpecificCategory("B","B2");
//        cc.addSpecificCategory("B","B3");
//        cc.addRelation("A","A1","B","B1");
//        cc.addRelation("A","A1","B","B2");
//        cc.addRelation("A","A1","B","B3");
//        cc.addRelation("A","A2","B","B1");
//        cc.addRelation("A","A2","B","B2");
//        RelationshipData curRelation = cc.getRelationshipData("A","B");
//
//        assertEquals(0.8,curRelation.getStrength("A","A2","A1"),0.001);
//
//        assertEquals(0.0,curRelation.getStrength("A","A1","A3"),0.001);
//
//        assertEquals(0.0,curRelation.getStrength("A","A3","A4"),0.001);
//
//    }




}
