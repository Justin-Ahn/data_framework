package core.category;

import core.data.RelationshipData;
import core.framework.DataVisualizationFramework;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * A Tester class for CategoryCollection
 */
public class TestCategoryCollection {
    @Test
    public void testCategoryCollection1() {
        CategoryManager manager = new CategoryManager();
        CategoryCollection cc = new CategoryCollection(manager);
        manager.registerCategory("A");
        manager.registerCategory("B");
        cc.addData("A","A1");
        cc.addData("B","B1");
        cc.addRelation("A","A1","B","B1");
        assertTrue(cc.getAllRelations().containsKey(new Data("A","A1")));
        assertTrue(cc.getAllRelations().containsKey(new Data("A","A1")));
        assertTrue(cc.getAllRelations().get(new Data("A","A1")).contains
                (new Data("B","B1")));
        assertTrue(cc.getAllRelations().get(new Data("B","B1")).contains
                (new Data("A","A1")));


    }

    @Test
    public void testGetRelationShipData1() {
        DataVisualizationFramework framework = new DataVisualizationFramework();
        CategoryManager manager = new CategoryManager();
        CategoryCollection cc = new CategoryCollection(manager);
        manager.registerCategory("A");
        manager.registerCategory("B");
        cc.addData("A","A1");
        cc.addData("A","A2");
        cc.addData("B","B1");
        cc.addRelation("A","A1","B","B1");
        cc.addRelation("A","A2","B","B1");
        //Can we add this without errors? (Manager Testing)
    }

    @Test
    public void testGetRelationShipData2() {
        CategoryManager manager = new CategoryManager();
        CategoryCollection cc = new CategoryCollection(manager);
        cc.addData("A","A1");
        cc.addData("A","A2");
        cc.addData("A","A3");
        cc.addData("A","A4");
        cc.addData("B","B1");
        cc.addData("B","B2");
        cc.addData("B","B3");
        cc.addRelation("A","A1","B","B1");
        cc.addRelation("A","A1","B","B2");
        cc.addRelation("A","A1","B","B3");
        cc.addRelation("A","A2","B","B1");
        cc.addRelation("A","A2","B","B2");
        //Can we add this without errors? (Manager Testing)
    }
}
