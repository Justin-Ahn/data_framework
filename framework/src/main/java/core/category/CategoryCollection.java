package core.category;

import java.util.HashMap;
import java.util.Map;
import java.lang.IllegalArgumentException;
import java.util.Set;
import java.util.HashSet;


/**
 * Created by tianyugu on 4/8/17.
 */
public class CategoryCollection {
    private Map<Data, Set<Data>> allRelations;
    private final CategoryManager manager;

    //Cache_enable?

    public CategoryCollection(CategoryManager manager) {
        this.manager = manager;
        this.allRelations = new HashMap<>();
    }

    /**
     * add one instance of a Data specified by type and name to allRelations as key
     * the associated value is set to be an empty Set<Data>
     * do nothing if the instance already existed in allRelations
     * @param category the type of the category
     * @param name the name of the instance
     */
    public Data addData(String category, String name) {
        if (!manager.contains(category)) {
            throw new IllegalArgumentException("The Manager doesn't know of this category!");
        }

        Data data = new Data(category, name);
        if (this.allRelations.containsKey(data)) {
            /* do nothing */
        } else {
            this.allRelations.put(data, new HashSet<>());
        }
        return data;
    }

    /**
     * build relationship between the category instance c1 specified by type1, name1
     * and the category instance c2 specified by type2, name2
     *
     * @param category1 type of the first Data instance
     * @param name1 name of the first Data instance
     * @param category2 type of the second Data instance
     * @param name2 name of the second Data instance
     * @throws IllegalArgumentException if type1 is equal to type2
     */
    public void addRelation(String category1, String name1, String category2, String name2) {
        Data d1 = null;
        Data d2 = null;
        for (Data d : allRelations.keySet()) {
            if (d.getCategory().equals(category1) && d.getName().equals(name1)) {
                d1 = d;
            }
            else if (d.getCategory().equals(category2) && d.getName().equals(name2)) {
                d2 = d;
            }
        }
        addRelation(d1, d2);
    }

    public boolean isConnected(Data d1, Data d2) {
        return allRelations.get(d1).contains(d2);
    }


    public void addRelation(Data d1, Data d2) {
        if (!manager.contains(d1.getCategory()) | !manager.contains(d2.getCategory())) {
            throw new IllegalArgumentException("Your specified Categories are not registered to the manager!");
        }
        if (!allRelations.containsKey(d1) || !allRelations.containsKey(d2)) {
            throw new IllegalArgumentException ("Specified data not found.");
        }
        this.allRelations.get(d1).add(d2);
        this.allRelations.get(d2).add(d1);
    }

    /**
     *
     * @return A copy of the Map of Categories & each instance's ownership.
     */
    public Map<Data, Set<Data>> getAllRelations() {
        return new HashMap<>(this.allRelations);
    }

    public void clear() {
        allRelations.clear();
        manager.clear();
    }

    @Override
    public String toString() {
        return allRelations.toString();
    }
}
