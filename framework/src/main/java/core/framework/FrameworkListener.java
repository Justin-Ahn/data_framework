package core.framework;

import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

/**
 * Listens for Plugins being registered onto the Framework.
 */
public interface FrameworkListener {
    /**
     * Called when a DataPlugin is registered.
     * @param dataPlugin The plugin that is being registered.
     */
    void onDataPluginRegistered(DataPlugin dataPlugin);

    /**
     * Called when a VisualizationPlugin is registered.
     * @param visualPlugin The plugin that is being registered.
     */
    void onVisualPluginRegistered(VisualizationPlugin visualPlugin);
}
