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
    private final double averageNumRelations;
    private final List<Data> relationStrengthList;

    public AnalysisData(List<Data> numRelationsList,
                        double averageRelationStrength,
                        double averageNumRelations,
                        List<Data> relationStrengthList) {
        this.numRelationsList = numRelationsList;
        this.averageRelationStrength = averageRelationStrength;
        this.averageNumRelations = averageNumRelations;
        this.relationStrengthList = relationStrengthList;
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

