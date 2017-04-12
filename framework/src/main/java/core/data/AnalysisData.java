package core.data;

import core.category.Data;

import java.util.*;

/**
 * The Data structure that holds all the results of the analysis of an instance of RelationshipData.
 * Contains the following:
 *
 * numRelationList => A List sorted by the number of relations each Data has with others. Index @ 0 is the largest number.
 * averageRelationStrength => The average Strength of Relationships of all the Data in the RelationshipData
 * maxRelationStrength => The maximum RelationshipStrength of all the Data in RelationshipData.
 * averageNumRelations => The average number of relationships per Data in RelationshipData
 * relationStrengthList => A List sorted by the summation of each Data's relationship strengths. Index @ 0 is the largest.
 * relationPair => A Pairing of all the relationships and their respective relationshipStrength. Just another way to
 * organize the relationshipData.
 *
 * Further Analyses can be made by a Visualization plugin if needed, since the RelationshipData contains
 * the entire mapping data structure.
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

    public double getMaxRelationStrength() {
        return maxRelationStrength;
    }

    public Map<Set<Data>, Double> getRelationPair() {
        return relationPair;
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

