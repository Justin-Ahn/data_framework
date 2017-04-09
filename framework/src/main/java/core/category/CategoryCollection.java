package core.category;

import core.data.RelationshipData;

import java.util.HashMap;
import java.util.Map;
import java.lang.IllegalArgumentException;
import java.util.Set;
import java.util.HashSet;


/**
 * Created by tianyugu on 4/8/17.
 */
public class CategoryCollection {
    private Map<SpecificCategory, Set<SpecificCategory>> allRelation;
    private Map<String,RelationshipData> cacheRelationShipData;

    public CategoryCollection() {
        this.allRelation = new HashMap<SpecificCategory, Set<SpecificCategory>>();
        this.cacheRelationShipData = new HashMap<String, RelationshipData>();
    }

    /**
     * add one instance of a SpecificCategory specified by type and name to allRelation as key
     * the associated value is set to be an empty Set<SpecificCategory>
     * do nothing if the instance already existed in allRelation
     * @param type the type of the category
     * @param name the name of the instance
     */
    public void addSpecificCategory(String type, String name) {
        SpecificCategory curCategory = new SpecificCategory(type, name);
        if (this.allRelation.containsKey(curCategory)) {
            /* do nothing */
        } else {
            this.allRelation.put(curCategory, new HashSet<SpecificCategory>());
        }
    }

    /**
     * build relationship between the category instance c1 specified by type1, name1
     * and the category instance c2 specified by type2, name2
     *
     * @param type1 type of the first SpecificCategory instance
     * @param name1 name of the first SpecificCategory instance
     * @param type2 type of the second SpecificCategory instance
     * @param name2 name of the second SpecificCategory instance
     * @throws IllegalArgumentException if type1 is equal to type2
     */
    public void addRelation(String type1, String name1, String type2, String name2) {
        SpecificCategory c1 = new SpecificCategory(type1, name1);
        SpecificCategory c2 = new SpecificCategory(type2, name2);
        if ((!this.allRelation.containsKey(c1)) || (!this.allRelation.containsKey(c2))) {
            throw (new IllegalArgumentException
                    ("the the category does not exist, please use addSpecificCategory first"));
        }
        this.allRelation.get(c1).add(c2);
        this.allRelation.get(c2).add(c1);
    }

    /**
     * no modifier for testing purpose (package level access)
     * @return
     */
    Map<SpecificCategory, Set<SpecificCategory>> getAllRelation() {
        return this.allRelation;
    }

    public RelationshipData getRelationshipData(String node, String link) {
        if(node.equals(link)) {
            throw (new IllegalArgumentException("node and link should be different types"));
        }
        // if we computed the relationship before, we use the cached data
        if (this.cacheRelationShipData.get(node+link) != null) {
            return this.cacheRelationShipData.get(node+link);
        }

        // we first add all the keys
        Set<SpecificCategory> curKeySet = new HashSet<SpecificCategory>();
        for (SpecificCategory sc : this.allRelation.keySet()) {
            if (sc.getType().equals(node)) {
                curKeySet.add(sc);
            }
        }
        RelationshipData curRelationship = new RelationshipData(curKeySet);

        // we then compute and store the strength of the link
        for (SpecificCategory sc1 : curKeySet) {
            for (SpecificCategory sc2 : curKeySet) {
                if (!sc1.equals(sc2)) {
                    try {
                        double sc1All = numTypeElem(this.allRelation.get(sc1),link);
                        double sc2All = numTypeElem(this.allRelation.get(sc2),link);
                        double common = numCommonTypeElem(this.allRelation.get(sc1), this.allRelation.get(sc2), link);
                        if (sc1All + sc2All == 0.0) {
                            throw new ArithmeticException();
                        }
                        double strength = (common * 2.0)/(sc1All+sc2All);
                        curRelationship.addLink(sc1,sc2,strength);
                    } catch(ArithmeticException ae) {
                        curRelationship.addLink(sc1,sc2,0.0);
                    }

                }
            }
        }

        // we cache the newly formed relationship
        this.cacheRelationShipData.put(node+link, curRelationship);
        
        return curRelationship;
    }

    private double numTypeElem(Set<SpecificCategory> s, String targetType) {
        double result = 0.0;
        for (SpecificCategory sc : s) {
            if (sc.getType().equals(targetType)) {
                result = result + 1.0;
            }
        }
        return result;
    }

    private double numCommonTypeElem(Set<SpecificCategory> s1, Set<SpecificCategory> s2, String targetType) {
        double result = 0.0;
        for (SpecificCategory sc1 : s1) {
            if (sc1.getType().equals(targetType) && s2.contains(sc1)) {
                result = result + 1.0;
            }
        }
        return result;
    }

}
