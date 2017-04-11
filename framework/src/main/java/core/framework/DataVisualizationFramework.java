package core.framework;

import core.category.CategoryCollection;
import core.category.DataCategory;
import core.data.AnalysisData;
import core.comparator.NumRelationComparator;
import core.comparator.RelationStrengthComparator;
import core.data.RelationshipData;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

import javax.swing.*;
import java.util.*;

/**
 * Created by Justin on 4/9/2017.
 */
public class DataVisualizationFramework {
    private FrameworkListener listener;
    private DataPlugin dataPlugin;
    private VisualizationPlugin visPlugin;

    //Implementing caching depends on category collection, node, and link. How to resolve?
    //Also some data might not want caching because of real time things. Have cache_enable field?
    private final Map<CategoryCollection, RelationshipData> relationCache;

    //Also, this cache gets infinitely larger. Somehow make an LRU cache instead?
    private final Map<RelationshipData, AnalysisData> analysisCache;

    public DataVisualizationFramework() {
        relationCache = new HashMap<>();
        analysisCache = new HashMap<>();
        dataPlugin = null;
        visPlugin = null;
    }

    public void setListener(FrameworkListener listener) {
        this.listener = listener;
    }

    /**
     * register the data plugin
     * @param dp the data plugin
     * this method should be called at the start of the framework
     */
    public void registerDataPlugin(DataPlugin dp) {
        this.dataPlugin = dp;
        listener.onNewDataPluginRegistered();
    }

    /**
     * register the visualization plugin
     * @param vp the visualization plugin
     * this method should be called at the start of the framework
     */
    public void registerVisualizationPlugin(VisualizationPlugin vp) {
        this.visPlugin = vp;
        listener.onNewVisualPluginRegistered();
    }

    /**
     * get the current visualization panel
     * @return the visualization panel contained with all the data visualization plots and analysis
     */
    public JPanel getDataVisual(String node, String link) {
        if (dataPlugin.equals(null) || visPlugin.equals(null)) {
            return null;
        }

        RelationshipData relationData = calculateRelationData(node, link);
        AnalysisData analysisData = calculateAnalysisData(relationData);
        return visPlugin.getVisual(relationData, analysisData);
    }

    //Analysis Data Calculation methods below...
    private AnalysisData calculateAnalysisData(RelationshipData relationshipData) {
        if (analysisCache.containsKey(relationshipData)) {
            return analysisCache.get(relationshipData);
        }

        AnalysisData analysisData = new AnalysisData(
                calcNumRelationList(relationshipData),
                calcAverageRelationStrength(relationshipData),
                calcAverageNumRelations(relationshipData),
                calcRelationStrengthList(relationshipData));

        analysisCache.put(relationshipData, analysisData);
        return analysisData;
    }

    private List<DataCategory> calcNumRelationList(RelationshipData relationshipData) {
        Map<DataCategory, Map<DataCategory, Double>> relationMap = relationshipData.getRelationshipMap();
        List<DataCategory> result = new ArrayList<>(relationMap.keySet());
        Collections.sort(result, new NumRelationComparator(relationMap));
        return result;
    }

    private double calcAverageRelationStrength(RelationshipData relationshipData) {
        double sumRelationStrength = 0;
        double numRelations = 0;
        Map<DataCategory, Map<DataCategory, Double>> relationMap = relationshipData.getRelationshipMap();
        for (DataCategory node : relationMap.keySet()) {
            numRelations += relationMap.get(node).size();
            Map<DataCategory, Double> linkedMap = relationMap.get(node);
            for (DataCategory linkedNode : linkedMap.keySet()) {
                sumRelationStrength += linkedMap.get(linkedNode);
            }
        }
        return sumRelationStrength/numRelations;
    }

    private double calcAverageNumRelations(RelationshipData relationshipData) {
        double numNodes = 0;
        double numRelations = 0;
        Map<DataCategory, Map<DataCategory, Double>> relationMap = relationshipData.getRelationshipMap();
        for (DataCategory node : relationMap.keySet()) {
            numNodes++;
            numRelations += relationMap.get(node).size();
        }
        return numRelations/numNodes;
    }

    private double calcRelationPairList(RelationshipData relationshipData) {
        //Todo
        return 0;
    }

    private List<DataCategory> calcRelationStrengthList(RelationshipData relationshipData) {
        Map<DataCategory, Map<DataCategory,Double>> map = relationshipData.getRelationshipMap();
        List<DataCategory> nodeList = new ArrayList<>(map.keySet());
        RelationStrengthComparator comparator = new RelationStrengthComparator(map);
        Collections.sort(nodeList, comparator);
        return nodeList;
    }
    //End of Analysis Data Calculation methods.


    //Relationship Data Calculation methods below...
    private RelationshipData calculateRelationData(String node, String link) {
        Map<DataCategory, Set<DataCategory>> allRelations = dataPlugin.getData().getAllRelations();
        //node and link naming???
        if(node.equals(link)) {
            throw (new IllegalArgumentException("The node and link cannot be of the same type"));
        }
        // if we computed the relationship before, we use the cached data

        // we first add all the keys
        Set<DataCategory> curKeySet = new HashSet<DataCategory>();
        for (DataCategory sc : allRelations.keySet()) {
            if (sc.getType().equals(node)) {
                curKeySet.add(sc);
            }
        }
        RelationshipData curRelationship = new RelationshipData(curKeySet);

        // we then compute and store the strength of the link
        for (DataCategory sc1 : curKeySet) {
            for (DataCategory sc2 : curKeySet) {
                if (!sc1.equals(sc2)) {
                    double sc1All = numTypeElem(allRelations.get(sc1),link);
                    double sc2All = numTypeElem(allRelations.get(sc2),link);
                    double common = numCommonTypeElem(allRelations.get(sc1), allRelations.get(sc2), link);
                    if (sc1All + sc2All == 0.0 || common == 0.0) {
                        //Do nothing -> Nothing is added.
                    }
                    else {
                        double strength = (common * 2.0)/(sc1All+sc2All);
                        curRelationship.addLink(sc1,sc2,strength);
                    }
                }
            }
        }

        // we cache the newly formed relationship

        return curRelationship;
    }

    private double numTypeElem(Set<DataCategory> s, String targetType) {
        double result = 0.0;
        for (DataCategory sc : s) {
            if (sc.getType().equals(targetType)) {
                result = result + 1.0;
            }
        }
        return result;
    }

    private double numCommonTypeElem(Set<DataCategory> s1, Set<DataCategory> s2, String targetType) {
        double result = 0.0;
        for (DataCategory sc1 : s1) {
            if (sc1.getType().equals(targetType) && s2.contains(sc1)) {
                result = result + 1.0;
            }
        }
        return result;
    }
    //End of Relationship Data Calculation methods.












}
