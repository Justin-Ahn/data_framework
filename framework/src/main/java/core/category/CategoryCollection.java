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
    private Map<DataCategory, Set<DataCategory>> allRelations;

    //Cache_enable?

    public CategoryCollection() {
        this.allRelations = new HashMap<>();
    }

    /**
     * add one instance of a DataCategory specified by type and name to allRelations as key
     * the associated value is set to be an empty Set<DataCategory>
     * do nothing if the instance already existed in allRelations
     * @param type the type of the category
     * @param name the name of the instance
     */
    public void addCategory(String type, String name) {
        DataCategory curCategory = new DataCategory(type, name);
        if (this.allRelations.containsKey(curCategory)) {
            /* do nothing */
        } else {
            this.allRelations.put(curCategory, new HashSet<>());
        }
    }

    /**
     * build relationship between the category instance c1 specified by type1, name1
     * and the category instance c2 specified by type2, name2
     *
     * @param type1 type of the first DataCategory instance
     * @param name1 name of the first DataCategory instance
     * @param type2 type of the second DataCategory instance
     * @param name2 name of the second DataCategory instance
     * @throws IllegalArgumentException if type1 is equal to type2
     */
    public void addRelation(String type1, String name1, String type2, String name2) {
        DataCategory c1 = new DataCategory(type1, name1);
        DataCategory c2 = new DataCategory(type2, name2);
        if ((!this.allRelations.containsKey(c1)) || (!this.allRelations.containsKey(c2))) {
            throw (new IllegalArgumentException
                    ("the the category does not exist, please use addSpecificCategory first"));
        }
        this.allRelations.get(c1).add(c2);
        this.allRelations.get(c2).add(c1);
    }

    /**
     *
     * @return A copy of the Map of Categories & each instance's ownership.
     */
    public Map<DataCategory, Set<DataCategory>> getAllRelations() {
        return new HashMap<>(this.allRelations);
    }


    @Override
    public String toString() {
        return allRelations.toString();
    }
}
