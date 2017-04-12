package core.plugin;

import core.data.AnalysisData;
import core.data.RelationshipData;

import javax.swing.*;

/**
 * Created by Justin on 4/8/2017.
 */
public interface VisualizationPlugin {

    String getName();
    void onRegister();

    /**
     * @return The description of the way the plugin will visualize the given data.
     */
    String getDescription();

    /**
     * @param relation The data structure that holds the information of nodes & strength of edges.
     * @param analysis The data structure that holds the result of the analysis from the Analysis Plugin.
     * @return The visualization of the data given.
     */
    JPanel getVisual(RelationshipData relation, AnalysisData analysis);
}
