package core.comparator;

import core.category.Data;

import java.util.Comparator;
import java.util.Map;

/**
 * A Helper Comparator class for the Framework when calculating an aspect of AnalysisData.
 * Nothing important to see here...
 */
public class RelationStrengthComparator implements Comparator<Data> {
    private Map<Data,Map<Data,Double>> map;

    public RelationStrengthComparator(Map<Data,Map<Data,Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(Data o1, Data o2) {
        return Double.compare(getMapStrength(this.map.get(o1)), getMapStrength(this.map.get(o2)));
    }

    private Double getMapStrength(Map<Data,Double> mp) {
        Double result = 0.0;
        for (Double val : mp.values()) {
            result += val;
        }
        return result;
    }
}
