package core.data;

import core.category.SpecificCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Justin on 4/8/2017.
 */
public class RelationshipData {

    private Map<SpecificCategory,Map<SpecificCategory,Double>> curRelationship;

    public RelationshipData(Set<SpecificCategory> keySet) {
        curRelationship = new HashMap<SpecificCategory,Map<SpecificCategory,Double>>();
        for (SpecificCategory sc : keySet) {
            curRelationship.put(sc, new HashMap<SpecificCategory, Double>());
        }
    }

    public void addLink(SpecificCategory c1, SpecificCategory c2, Double strength) {
        curRelationship.get(c1).put(c2,strength);
        curRelationship.get(c2).put(c1,strength);
    }

    /**
     * for test purpose
     * @return
     */
    public Map<SpecificCategory,Map<SpecificCategory,Double>> getCurRelationship() {
        return curRelationship;
    }

    public double getStrength(String type, String name1, String name2) {
        SpecificCategory sc1 = new SpecificCategory(type, name1);
        SpecificCategory sc2 = new SpecificCategory(type, name2);
        return curRelationship.get(sc1).get(sc2);
    }


}
