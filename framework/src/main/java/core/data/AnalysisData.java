package core.data;

import core.category.DataCategory;

import java.util.*;

/**
 * Created by Justin on 4/8/2017.
 */

/**
 * the data structure for storing and computing result of data analysis
 */
public class AnalysisData {
    // the current relationship data
    private RelationshipData relationData;
    // Ordered list of Nodes corresponding to Summation of strength of relationships pertaining to that Node.
    private List<DataCategory> nodeStrengthList;

    public AnalysisData(RelationshipData rd) {
        this.relationData = rd;
    }

    /**
     *
     * @return Ordered list of Nodes corresponding to Summation of strength of relationships pertaining to that Node.
     */
    public List<DataCategory> getNodeStrengthList() {
        if (this.nodeStrengthList != null) {
            return new ArrayList<>(this.nodeStrengthList);
        } else {
            List<DataCategory> nodeList =
                    new ArrayList<DataCategory>(relationData.getCurRelationshipMap().keySet());
            NodeStrengthCmp<DataCategory> curCmp = new NodeStrengthCmp<>(relationData.getCurRelationshipMap());
            Collections.sort(nodeList, curCmp);
            this.nodeStrengthList = nodeList;
            return new ArrayList<>(this.nodeStrengthList);
        }
    }

    public List<DataCategory> getNodeConnectionList() {

        return null;
    }

}

/**
 * the comparator class for comparing nodes by comparing summation of strength of relationships pertaining to that node
 * @param <SpecificCategory>
 */
class NodeStrengthCmp<SpecificCategory> implements Comparator<SpecificCategory> {
    private Map<SpecificCategory,Map<SpecificCategory,Double>> map;

    NodeStrengthCmp(Map<SpecificCategory,Map<SpecificCategory,Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(SpecificCategory o1, SpecificCategory o2) {
        return Double.compare(getMapStrength(this.map.get(o1)), getMapStrength(this.map.get(o2)));
    }

    private Double getMapStrength(Map<SpecificCategory,Double> mp) {
        Double result = 0.0;
        for (Double val : mp.values()) {
            result += val;
        }
        return result;
    }
}


