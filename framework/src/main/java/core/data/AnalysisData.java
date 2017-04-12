package core.data;

import core.category.Data;

import java.util.*;

/**
 * Created by Justin on 4/8/2017.
 */

/**
 * the data structure for storing and computing result of data analysis
 */
public class AnalysisData {
    private final List<Data> numRelationsList;
    private final double averageRelationStrength;
    private final double maxRelationStrength;
    private final double averageNumRelations;
    private final List<Data> relationStrengthList;
    private final Map<Set<Data>, Double> relationPair;

    public AnalysisData(List<Data> numRelationsList,
                        double averageRelationStrength,
                        double maxRelationStrength,
                        double averageNumRelations,
                        List<Data> relationStrengthList,
                        Map<Set<Data>, Double> relationPair) {
        this.numRelationsList = numRelationsList;
        this.averageRelationStrength = averageRelationStrength;
        this.maxRelationStrength = maxRelationStrength;
        this.averageNumRelations = averageNumRelations;
        this.relationStrengthList = relationStrengthList;
        this.relationPair = relationPair;
    }

    public List<Data> getNumRelationsList() {
        return new ArrayList<>(numRelationsList);
    }

    public double getAverageRelationStrength() {
        return averageRelationStrength;
    }

    public double getAverageNumRelations() {
        return averageNumRelations;
    }

    public List<Data> getRelationStrengthList() {
        return new ArrayList<>(relationStrengthList);
    }

}

