package core.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.IllegalArgumentException;
import java.util.Set;
import java.util.HashSet;


/**
 * Created by tianyugu on 4/8/17.
 */
public class CategoryCollection {
    private Map<SpecificCategory, Set<SpecificCategory>> allRelation;

    public CategoryCollection() {
        this.allRelation = new HashMap<SpecificCategory, Set<SpecificCategory>>();
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








}
