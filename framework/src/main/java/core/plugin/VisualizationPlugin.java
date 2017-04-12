package core.plugin;

import core.data.AnalysisData;
import core.data.RelationshipData;

import javax.swing.*;

/**
 * VisualizationPlugin's Interface.
 */
public interface VisualizationPlugin {

    /**
     * @return The name of the framework. Must be concise.
     */
    String getName();

    /**
     * Called when the framework is registered to the framework to do any initial setups.
     */
    void onRegister();

    /**
     * @return The description of the way the plugin will visualize the given data.
     */
    String getDescription();

    /**
     * A JPanel that provides the visualization of the RelationshipData & AnalysisData given by
     * DataPlugin -> Framework -> VisualizationPlugin.
     * The Visualization could be of the RelationshipData, AnalysisData, both, or neither. Entirely dependent
     * on the developer's desires. As long as its a JPanel, its acceptable.
     * @param relation The data structure that holds the information of nodes & strength of edges.
     * @param analysis The data structure that holds the result of the analysis from the Analysis Plugin.
     * @return The visualization of the data given.
     */
    JPanel getVisual(RelationshipData relation, AnalysisData analysis);
}
