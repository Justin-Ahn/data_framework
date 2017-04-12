package core.framework;

import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;

/**
 * Created by Justin on 4/11/2017.
 */
public interface FrameworkListener {
    void onDataPluginRegistered(DataPlugin dataPlugin);
    void onVisualPluginRegistered(VisualizationPlugin visualPlugin);
}
