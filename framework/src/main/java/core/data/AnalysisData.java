package core.data;

import core.category.SpecificCategory;

import java.util.ArrayList;

/**
 * Created by Justin on 4/8/2017.
 */
public class AnalysisData {
    private final ArrayList<SpecificCategory> nodeData;
    private final ArrayList<Double> numData;

    public AnalysisData() {
        nodeData = new ArrayList<>();
        numData = new ArrayList<>();
    }

    public void addNum(double d) {
        numData.add(d);
    }

    public boolean removeNum(double d) {
        return numData.remove(d);
    }

    public boolean removeNode(SpecificCategory node) {
        return nodeData.remove(node);
    }

    public void addNode(SpecificCategory node) {
        nodeData.add(node);
    }

    public ArrayList<Double> getNumData() {
        return new ArrayList<>(numData);
    }

    public ArrayList<SpecificCategory> getNodeData() {
        return new ArrayList<>(nodeData);
    }

}
