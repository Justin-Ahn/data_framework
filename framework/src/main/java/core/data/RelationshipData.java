package core.data;

import core.category.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Justin on 4/8/2017.
 */
public class RelationshipData {

    private Map<Data,Map<Data,Double>> relationship;

    public RelationshipData(Set<Data> keySet) {
        relationship = new HashMap<>();
        for (Data sc : keySet) {
            relationship.put(sc, new HashMap<>());
        }
    }

    public void addLink(Data c1, Data c2, Double strength) {
        relationship.get(c1).put(c2,strength);
        relationship.get(c2).put(c1,strength);
    }

    /**
     * @return The copy of the current relationship map
     */
    public Map<Data,Map<Data,Double>> getRelationshipMap() {
        return new HashMap<>(relationship);
    }

    public double getStrength(String type, String name1, String name2) {
        Data sc1 = new Data(type, name1);
        Data sc2 = new Data(type, name2);
        return relationship.get(sc1).get(sc2);
    }


}
