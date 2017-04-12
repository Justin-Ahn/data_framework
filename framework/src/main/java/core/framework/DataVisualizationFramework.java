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
import java.util.List;

/**
 * The Main Framework Class. Deals with cross-communication between the two plugins via different data structures.
 * DataPlugin --> CategoryCollection/CategoryManager/Data | Framework
 * Framework ---> RelationshipData/AnalysisData           | VisualizationPlugin
 */
public class DataVisualizationFramework {
    private FrameworkListener listener;
    private DataPlugin dPlugin;
    private VisualizationPlugin vPlugin;
    private CategoryCollection collection;

    private final Map<String, RelationshipData> relationCache;
    private final Map<RelationshipData, AnalysisData> analysisCache;

    public DataVisualizationFramework() {
        relationCache = new HashMap<>();
        analysisCache = new HashMap<>();
        dPlugin = null;
        vPlugin = null;
        collection = null;
    }

    public void setListener(FrameworkListener listener) {
        this.listener = listener;
    }

    /**
     * Registers the data plugin. Called only once per type of DataPlugin.
     * This method should be called at the start of the framework lifecycle.
     * @param dp The data plugin
     */
    public void registerDataPlugin(DataPlugin dp) {
        dp.onRegister();
        listener.onDataPluginRegistered(dp);
    }

    /**
     * Registers the visualization plugin. Called only once per type of VisualizationPlugin.
     * This method should be called at the start of the framework lifecycle.
     * @param vp the visualization plugin
     */
    public void registerVisualizationPlugin(VisualizationPlugin vp) {
        vp.onRegister();
        listener.onVisualPluginRegistered(vp);
    }

    /**
     * Sets the framework's dataPlugin. The framework can only handle one plugin of each type at a time.
     * The plugin's .getData() method will only be called once by the Framework.
     * @param plugin The data plugin
     */
    public void setDataPlugin(DataPlugin plugin) {
        this.dPlugin = plugin;
        this.collection = plugin.getData();
    }

    /**
     * Sets the framework's visualPlugin. The framework can only handle one plugin of each type at a time.
     * @param plugin The visualization plugin
     */
    public void setVisualPlugin(VisualizationPlugin plugin) {
        this.vPlugin = plugin;
    }

    public DataPlugin getDataPlugin() {
        return this.dPlugin;
    }

    public VisualizationPlugin getVisualPlugin() {
        return this.vPlugin;
    }

    /**
     * Calculates the RelationData and the AnalysisData based on the dataPlugin.
     * Calls getVisual on the visualizationPlugin, giving it the aforementioned calculated RelationshipData and
     * AnalysisData.
     * @return The VisualizationPlugin's panel given respective inputs.
     */
    public JPanel getDataVisual(String node, String link) {
        if (!collection.getManager().contains(node)
                || !collection.getManager().contains(link)) {
            throw new IllegalArgumentException("Node/Link aren't valid Data Categories!");
        }

        RelationshipData relationData = calculateRelationData(dPlugin, node, link);
        AnalysisData analysisData = calculateAnalysisData(dPlugin, relationData);
        return vPlugin.getVisual(relationData, analysisData);
    }

    //________________________________START OF ANALYSIS METHODS________________________________

    /**
     * Calcaultes the AnalysisData based on the RelationshipData. A Description of each type of
     * method/data calculation is available on the class description of AnalysisData.
     * @param plugin The DataPlugin (for cache enabling purposes)
     * @param relationshipData The RelationshipData.
     * @return The calculated, finished AnalysisData.
     */
    private AnalysisData calculateAnalysisData(DataPlugin plugin, RelationshipData relationshipData) {
        if (plugin.cacheEnabled() && analysisCache.containsKey(relationshipData)) {
            return analysisCache.get(relationshipData);
        }

        AnalysisData analysisData = new AnalysisData(
                calcNumRelationList(relationshipData),
                calcAverageRelationStrength(relationshipData),
                calcMaxRelationStrength(relationshipData),
                calcAverageNumRelations(relationshipData),
                calcRelationStrengthList(relationshipData),
                calcRelationPairMap(relationshipData));

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

    private double calcMaxRelationStrength(RelationshipData relationshipData) {
        double maxRelationStrength = 0;
        Map<Data, Map<Data, Double>> relationMap = relationshipData.getRelationshipMap();
        for (Data node : relationMap.keySet()) {
            Map<Data, Double> linkedMap = relationMap.get(node);
            for (Data linkedNode : linkedMap.keySet()) {
                if (maxRelationStrength < relationMap.get(node).get(linkedNode)) {
                    maxRelationStrength = relationMap.get(node).get(linkedNode);
                }
            }
        }
        return maxRelationStrength;
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

    private Map<Set<Data>, Double> calcRelationPairMap(RelationshipData relationshipData) {
        Map<Data,Map<Data,Double>> map = relationshipData.getRelationshipMap();
        Map<Set<Data> ,Double> result = new HashMap<>();
        for (Data node : map.keySet()) {
            for (Data linkedNode : map.get(node).keySet()) {
                Set<Data> set = new HashSet<>();
                set.add(node);
                set.add(linkedNode);
                result.put(set, map.get(node).get(linkedNode));
            }
        }
        return result;
    }

    private List<Data> calcRelationStrengthList(RelationshipData relationshipData) {
        Map<Data, Map<Data,Double>> map = relationshipData.getRelationshipMap();
        List<Data> nodeList = new ArrayList<>(map.keySet());
        RelationStrengthComparator comparator = new RelationStrengthComparator(map);
        Collections.sort(nodeList, comparator);
        return nodeList;
    }
    //________________________________END OF ANALYSIS METHODS________________________________


    //________________________________START OF ANALYSIS METHODS________________________________

    /**
     * Calculates the RelationshipData based on an instance of Data owning different instances
     * of Data that are in a different category.
     * @param plugin The DataPlugin (for cache enabling purposes)
     * @param node The category to set to "node" status. (Will be checked if it is a valid category by the Manager)
     * @param link The category to set to "link" status. (Will be also checked.)
     * @return The RelationshipData
     */
    private RelationshipData calculateRelationData(DataPlugin plugin, String node, String link) {
        String hash = collection.getManager().getNodeLinkPluginHash(node, link);
        if (plugin.cacheEnabled() && relationCache.containsKey(hash)) {
            return relationCache.get(hash);
        }

        Map<Data, Set<Data>> allRelations = collection.getAllRelations();
        //node and link naming???
        if (node.equals(link)) {
            throw (new IllegalArgumentException("The Node and Link cannot be of the same type!"));
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
            relationCache.put(collection.getManager().getNodeLinkPluginHash(node, link), curRelationship);
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
    //________________________________END OF RELATIONSHIP METHODS________________________________













}
