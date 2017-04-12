package core.data;

import core.category.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Data Structure that holds all the information on the Relationships.
 * The entirety of the relationship & strength of relationships are in
 * the Map<Data,Map<Data,Double>> relationship.
 */
public class RelationshipData {

    private Map<Data,Map<Data,Double>> relationship;

    public RelationshipData(Set<Data> keySet) {
        relationship = new HashMap<>();
        for (Data sc : keySet) {
            relationship.put(sc, new HashMap<>());
        }
    }

    /**
     * Relationship between c1 and c2 are asserted. The Strength of this relationship
     * is contained within the "Double strength' parameter.
     * @param c1 Data 1
     * @param c2 Data 2
     * @param strength Strength of relationship between Data1 & Data 2.
     */
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

    /**
     * Returns the Strength of the relationship (0 <= Strength <= 1).
     * @param type The data category.
     * @param name1
     * @param name2
     * @return The relationship strength of the two Data.
     */
    public double getStrength(String type, String name1, String name2) {
        Data sc1 = new Data(type, name1);
        Data sc2 = new Data(type, name2);
        return relationship.get(sc1).get(sc2);
    }


}
