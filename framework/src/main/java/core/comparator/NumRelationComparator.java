package core.comparator;

import core.category.Data;

import java.util.Comparator;
import java.util.Map;

/**
 * A Helper Comparator class for the Framework when calculating an aspect of AnalysisData.
 * Nothing important to see here...
 */
public class NumRelationComparator implements Comparator<Data> {
    private final Map<Data,Map<Data,Double>> map;

    public NumRelationComparator(Map<Data,Map<Data,Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(Data o1, Data o2) {
        return Integer.compare(map.get(o1).size(), map.get(o2).size());
    }
}
