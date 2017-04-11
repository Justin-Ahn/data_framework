package core.data;

import core.category.DataCategory;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * Created by Justin on 4/8/2017.
 */

/**
 * the data structure for storing and computing result of data analysis
 */
public class AnalysisData {
    private final List<DataCategory> numRelationsList;
    private final double averageRelationStrength;
    private final double averageNumRelations;
    private final List<DataCategory> relationStrengthList;

    public AnalysisData(List<DataCategory> numRelationsList,
                        double averageRelationStrength,
                        double averageNumRelations,
                        List<DataCategory> relationStrengthList) {
        this.numRelationsList = numRelationsList;
        this.averageRelationStrength = averageRelationStrength;
        this.averageNumRelations = averageNumRelations;
        this.relationStrengthList = relationStrengthList;
    }

    public List<DataCategory> getNumRelationsList() {
        return new ArrayList<>(numRelationsList);
    }

    public double getAverageRelationStrength() {
        return averageRelationStrength;
    }

    public double getAverageNumRelations() {
        return averageNumRelations;
    }

    public List<DataCategory> getRelationStrengthList() {
        return new ArrayList<>(relationStrengthList);
    }

}

