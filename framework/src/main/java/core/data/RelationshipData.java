package core.data;

import core.category.DataCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Justin on 4/8/2017.
 */
public class RelationshipData {

    private Map<DataCategory,Map<DataCategory,Double>> relationship;

    public RelationshipData(Set<DataCategory> keySet) {
        relationship = new HashMap<>();
        for (DataCategory sc : keySet) {
            relationship.put(sc, new HashMap<>());
        }
    }

    public void addLink(DataCategory c1, DataCategory c2, Double strength) {
        relationship.get(c1).put(c2,strength);
        relationship.get(c2).put(c1,strength);
    }

    /**
     * @return The copy of the current relationship map
     */
    public Map<DataCategory,Map<DataCategory,Double>> getRelationshipMap() {
        return new HashMap<>(relationship);
    }

    public double getStrength(String type, String name1, String name2) {
        DataCategory sc1 = new DataCategory(type, name1);
        DataCategory sc2 = new DataCategory(type, name2);
        return relationship.get(sc1).get(sc2);
    }


}
