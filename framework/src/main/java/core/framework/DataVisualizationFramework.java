package core.framework;

import core.category.CategoryCollection;
import core.category.Data;
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
    private DataPlugin dPlugin;
    private VisualizationPlugin vPlugin;
    private String node;
    private String link;

    //Implementing caching depends on category collection, node, and link. How to resolve?
    //Also some data might not want caching because of real time things. Have cache_enable field?
    private final Map<String, RelationshipData> relationCache;

    //Also, this cache gets infinitely larger. Somehow make an LRU cache instead?
    private final Map<RelationshipData, AnalysisData> analysisCache;

    public DataVisualizationFramework() {
        relationCache = new HashMap<>();
        analysisCache = new HashMap<>();
        dPlugin = null;
        vPlugin = null;
        node = null;
        link = null;
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
        dp.onRegister();
        listener.onDataPluginRegistered(dp);
    }

    /**
     * register the visualization plugin
     * @param vp the visualization plugin
     * this method should be called at the start of the framework
     */
    public void registerVisualizationPlugin(VisualizationPlugin vp) {
        vp.onRegister();
        listener.onVisualPluginRegistered(vp);
    }

    public void setDataPlugin(DataPlugin plugin) {
        this.dPlugin = plugin;
    }

    public void setVisualPlugin(VisualizationPlugin plugin) {
        this.vPlugin = plugin;
    }

    public void setNode(String node) {
        if (dPlugin != null && !dPlugin.getData().getManager().contains(node)) {
            throw new IllegalArgumentException("The category isn't known by current plugin's manager!");
        }
        this.node = node;
    }

    public void setLink(String link) {
        if (dPlugin != null && !dPlugin.getData().getManager().contains(node)) {
            throw new IllegalArgumentException("The category isn't known by current plugin's manager!");
        }
        this.link = link;
    }
    /**
     * get the current visualization panel
     * @return the visualization panel contained with all the data visualization plots and analysis
     */
    public JPanel getDataVisual() {
        if (!dPlugin.getData().getManager().contains(node)
                || !dPlugin.getData().getManager().contains(link)) {
            throw new IllegalArgumentException("Node/Link aren't valid Data Categories!");
        }

        RelationshipData relationData = calculateRelationData(dPlugin, node, link);
        AnalysisData analysisData = calculateAnalysisData(dPlugin, relationData);
        return vPlugin.getVisual(relationData, analysisData);
    }

    //Analysis Data Calculation methods below...
    private AnalysisData calculateAnalysisData(DataPlugin plugin, RelationshipData relationshipData) {
        if (plugin.cacheEnabled() && analysisCache.containsKey(relationshipData)) {
            return analysisCache.get(relationshipData);
        }

        AnalysisData analysisData = new AnalysisData(
                calcNumRelationList(relationshipData),
                calcAverageRelationStrength(relationshipData),
                calcAverageNumRelations(relationshipData),
                calcRelationStrengthList(relationshipData));

        if (plugin.cacheEnabled()) {
            analysisCache.put(relationshipData, analysisData);
        }
        return analysisData;
    }

    private List<Data> calcNumRelationList(RelationshipData relationshipData) {
        Map<Data, Map<Data, Double>> relationMap = relationshipData.getRelationshipMap();
        List<Data> result = new ArrayList<>(relationMap.keySet());
        Collections.sort(result, new NumRelationComparator(relationMap));
        return result;
    }

    private double calcAverageRelationStrength(RelationshipData relationshipData) {
        double sumRelationStrength = 0;
        double numRelations = 0;
        Map<Data, Map<Data, Double>> relationMap = relationshipData.getRelationshipMap();
        for (Data node : relationMap.keySet()) {
            numRelations += relationMap.get(node).size();
            Map<Data, Double> linkedMap = relationMap.get(node);
            for (Data linkedNode : linkedMap.keySet()) {
                sumRelationStrength += linkedMap.get(linkedNode);
            }
        }
        return sumRelationStrength/numRelations;
    }

    private double calcAverageNumRelations(RelationshipData relationshipData) {
        double numNodes = 0;
        double numRelations = 0;
        Map<Data, Map<Data, Double>> relationMap = relationshipData.getRelationshipMap();
        for (Data node : relationMap.keySet()) {
            numNodes++;
            numRelations += relationMap.get(node).size();
        }
        return numRelations/numNodes;
    }

    private double calcRelationPairList(RelationshipData relationshipData) {
        //Todo
        return 0;
    }

    private List<Data> calcRelationStrengthList(RelationshipData relationshipData) {
        Map<Data, Map<Data,Double>> map = relationshipData.getRelationshipMap();
        List<Data> nodeList = new ArrayList<>(map.keySet());
        RelationStrengthComparator comparator = new RelationStrengthComparator(map);
        Collections.sort(nodeList, comparator);
        return nodeList;
    }
    //End of Analysis Data Calculation methods.


    //Relationship Data Calculation methods below...
    private RelationshipData calculateRelationData(DataPlugin plugin, String node, String link) {
        String hash = plugin.getData().getManager().getNodeLinkPluginHash(node, link);
        if (plugin.cacheEnabled() && relationCache.containsKey(hash)) {
            return relationCache.get(hash);
        }

        Map<Data, Set<Data>> allRelations = plugin.getData().getAllRelations();
        //node and link naming???
        if(node.equals(link)) {
            throw (new IllegalArgumentException("The node and link cannot be of the same type"));
        }
        // if we computed the relationship before, we use the cached data

        // we first add all the keys
        Set<Data> curKeySet = new HashSet<Data>();
        for (Data sc : allRelations.keySet()) {
            if (sc.getCategory().equals(node)) {
                curKeySet.add(sc);
            }
        }
        RelationshipData curRelationship = new RelationshipData(curKeySet);

        // we then compute and store the strength of the link
        for (Data sc1 : curKeySet) {
            for (Data sc2 : curKeySet) {
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
        if (plugin.cacheEnabled()) {
            relationCache.put(plugin.getData().getManager().getNodeLinkPluginHash(node, link), curRelationship);
        }
        return curRelationship;
    }

    private double numTypeElem(Set<Data> s, String targetType) {
        double result = 0.0;
        for (Data sc : s) {
            if (sc.getCategory().equals(targetType)) {
                result = result + 1.0;
            }
        }
        return result;
    }

    private double numCommonTypeElem(Set<Data> s1, Set<Data> s2, String targetType) {
        double result = 0.0;
        for (Data sc1 : s1) {
            if (sc1.getCategory().equals(targetType) && s2.contains(sc1)) {
                result = result + 1.0;
            }
        }
        return result;
    }
    //End of Relationship Data Calculation methods.













}
