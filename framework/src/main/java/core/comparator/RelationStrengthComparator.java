package core.comparator;

import core.category.DataCategory;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Justin on 4/11/2017.
 */
public class RelationStrengthComparator implements Comparator<DataCategory> {
    private Map<DataCategory,Map<DataCategory,Double>> map;

    public RelationStrengthComparator(Map<DataCategory,Map<DataCategory,Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(DataCategory o1, DataCategory o2) {
        return Double.compare(getMapStrength(this.map.get(o1)), getMapStrength(this.map.get(o2)));
    }

    private Double getMapStrength(Map<DataCategory,Double> mp) {
        Double result = 0.0;
        for (Double val : mp.values()) {
            result += val;
        }
        return result;
    }
}
