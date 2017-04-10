package core.framework;

import core.category.CategoryCollection;
import core.category.DataCategory;
import core.data.AnalysisData;
import core.data.RelationshipData;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

import javax.swing.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Justin on 4/9/2017.
 */
public class DataVisualizationFramework {
    private DataPlugin dataPlugin;
    private VisualizationPlugin visPlugin;
    private String node;
    private String link;
    private CategoryCollection collection;
    private RelationshipData relationData;
    private AnalysisData analysisData;
    private JPanel dataVisual;

    public DataVisualizationFramework() {
        dataPlugin = null;
        visPlugin = null;
        node = null;
        link = null;
        collection = null;
        relationData = null;
        analysisData = null;
        dataVisual = null;
    }

    /**
     * initialize the whole framework
     */
    public void initializeFramework() {
        dataPlugin = null;
        visPlugin = null;
        node = null;
        link = null;
        collection = null;
        relationData = null;
        analysisData = null;
        dataVisual = null;
    }

    /**
     * register the data plugin
     * @param dp the data plugin
     * this method should be called at the start of the framework
     */
    public void registerDataPlugin(DataPlugin dp) {
        this.dataPlugin = dp;
        setCollection();
    }

    /**
     * register the visualization plugin
     * @param vp the visualization plugin
     * this method should be called at the start of the framework
     */
    public void registerVisualizationPlugin(VisualizationPlugin vp) {
        this.visPlugin = vp;
    }

    /**
     * call the data plugin's method to get data in CategoryCollection form
     * and store the data
     * this method will be called after the data plugin is set
     */
    public void setCollection() {
        this.collection = this.dataPlugin.getData();
    }

    /**
     * select the category user would like to visualize and analyze as nodes
     * @param node the category type that user would like to visualize and analyze as nodes
     */
    public void setNode(String node) {
        this.node = node;
    }

    /**
     * select the category user would like to use for calculating strength of relation between nodes
     * @param link the category that will be used for calculating strength of relation
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * store the current relationship the user would like to visualize and analyze
     * if the node and link have not been selected, this method will do nothing
     * if the node and link are the same type, node and link will be set to null for reselecting
     *
     * this method will be called after node and link is set
     */
    public void setRelationData() {
        if((this.node==null)||(this.link==null)) {
            return;
        }
        else {
            this.relationData = calculateRelationData(node, link);
        }
    }

    /**
     * store the AnalysisData so its method can be called by the visualization plugin later
     *
     * this method will be called after the relationData is set
     */
    public void setAnalysisData() {
        this.analysisData = new AnalysisData(this.relationData);
    }


    /**
     * set the current visualization panel
     * this method will be called after everything else is done
     */
    public void setVisPanel() {
        this.dataVisual =  this.visPlugin.getVisual(this.relationData,this.analysisData);
    }

    /**
     * get the current visualization panel
     * @return the visualization panel contained with all the data visualization plots and analysis
     */
    public JPanel getdataVisual() {
        return this.dataVisual;
    }

    private RelationshipData calculateRelationData(String node, String link) {
        Map<DataCategory, Set<DataCategory>> allRelations = collection.getAllRelations();
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
                    if (sc1All + sc2All == 0.0) {
                        curRelationship.addLink(sc1,sc2,0.0);
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












}
