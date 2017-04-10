package core.data;

import core.category.SpecificCategory;

import java.util.*;

/**
 * Created by Justin on 4/8/2017.
 */

/**
 * the data structure for storing and computing result of data analysis
 */
public class AnalysisData {
    // the current relationship data
    private RelationshipData curRelation;
    // just a test
    private String testMessage = null;
    // Ordered list of Nodes corresponding to Summation of strength of relationships pertaining to that Node.
    private List<SpecificCategory> nodeStrengthRankList;

    public AnalysisData(RelationshipData rd) {
        this.curRelation = rd;
    }

    /**
     * just a test
     * @return
     */
    public String getTestMessage() {
        if (this.testMessage != null) {
            return testMessage;
        } else {
            this.testMessage = "this is a test";
            return testMessage;
        }
    }

    /**
     *
     * @return Ordered list of Nodes corresponding to Summation of strength of relationships pertaining to that Node.
     */
    public List<SpecificCategory> getNodeStrengthRankList() {
        if (this.nodeStrengthRankList != null) {
            return new ArrayList<>(this.nodeStrengthRankList);
        } else {
            List<SpecificCategory> nodeList =
                    new ArrayList<SpecificCategory>(curRelation.getCurRelationshipMap().keySet());
            NodeStrengthCmp<SpecificCategory> curCmp = new NodeStrengthCmp<>(curRelation.getCurRelationshipMap());
            Collections.sort(nodeList, curCmp);
            this.nodeStrengthRankList = nodeList;
            return new ArrayList<>(this.nodeStrengthRankList);
        }
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


