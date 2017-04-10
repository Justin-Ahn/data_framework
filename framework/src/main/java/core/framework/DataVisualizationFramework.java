package core.framework;

import core.category.CategoryCollection;
import core.data.AnalysisData;
import core.data.RelationshipData;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

import javax.swing.*;

/**
 * Created by Justin on 4/9/2017.
 */
public class DataVisualizationFramework {
    private DataPlugin dataPlugin;
    private VisualizationPlugin visPlugin;
    private String node;
    private String link;
    private CategoryCollection curCollection;
    private RelationshipData curRelation;
    private AnalysisData curAnalysis;
    private JPanel curVisPanel;

    public DataVisualizationFramework() {
        dataPlugin = null;
        visPlugin = null;
        node = null;
        link = null;
        curCollection = null;
        curRelation = null;
        curAnalysis = null;
        curVisPanel = null;
    }

    /**
     * initialize the whole framework
     */
    public void initializeFramework() {
        dataPlugin = null;
        visPlugin = null;
        node = null;
        link = null;
        curCollection = null;
        curRelation = null;
        curAnalysis = null;
        curVisPanel = null;
    }

    /**
     * register the data plugin
     * @param dp the data plugin
     * this method should be called at the start of the framework
     */
    public void registerDataPlugin(DataPlugin dp) {
        this.dataPlugin = dp;
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
    public void setCurCollection() {
        this.curCollection = this.dataPlugin.getData();
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
    public void setCurRelation() {
        if((this.node==null)||(this.link==null)) {
            return;
        } else {
//            try {
                this.curRelation = this.curCollection.getRelationshipData(this.node,this.link);
//            } catch (IllegalArgumentException ie) {
//                this.node = null;
//                this.link = null;
//            }

        }
    }

    /**
     * store the AnalysisData so its method can be called by the visualization plugin later
     *
     * this method will be called after the curRelation is set
     */
    public void setCurAnalysis() {
        this.curAnalysis = new AnalysisData(this.curRelation);
    }


    /**
     * set the current visualization panel
     * this method will be called after everything else is done
     */
    public void setCurVisPanel() {
        this.curVisPanel =  this.visPlugin.getVisual(this.curRelation,this.curAnalysis);
    }

    /**
     * get the current visualization panel
     * @return the visualization panel contained with all the data visualization plots and analysis
     */
    public JPanel getCurVisPanel() {
        return this.curVisPanel;
    }












}
