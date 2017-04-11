package core.comparator;

import core.category.DataCategory;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Justin on 4/11/2017.
 */
public class NumRelationComparator implements Comparator<DataCategory> {
    private final Map<DataCategory,Map<DataCategory,Double>> map;

    public NumRelationComparator(Map<DataCategory,Map<DataCategory,Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(DataCategory o1, DataCategory o2) {
        return Integer.compare(map.get(o1).size(), map.get(o2).size());
    }
}
