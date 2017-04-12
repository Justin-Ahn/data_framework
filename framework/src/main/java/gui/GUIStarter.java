package gui;

import core.framework.DataVisualizationFramework;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

import java.util.List;

/**
 * A Class that holds the data/visual Plugins and runs the FrameworkGUI. Gives an instance of itself
 * to the GUI such that the GUI can create a completely new FrameworkGUI by itself.
 */
public class GUIStarter {
    private List<DataPlugin> dataPlugins;
    private List<VisualizationPlugin> visualPlugins;

    public GUIStarter(List<DataPlugin> dataPlugins, List<VisualizationPlugin> visualPlugins) {
        this.dataPlugins = dataPlugins;
        this.visualPlugins = visualPlugins;
    }

    public void startFramework() {
        DataVisualizationFramework core = new DataVisualizationFramework();
        FrameworkGUI gui = new FrameworkGUI(core, this);
        dataPlugins.forEach(core::registerDataPlugin);
        visualPlugins.forEach(core::registerVisualizationPlugin);
    }
}
