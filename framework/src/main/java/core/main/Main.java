package core.main;

import core.framework.DataVisualizationFramework;
import core.plugin.DataPlugin;
import core.plugin.VisualizationPlugin;
import gui.FrameworkGUI;
import gui.GUIStarter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * The core.main.Main class that loads the plugins, creates the framework, and runs the GUI.
 */
public class Main {
    private static URL[] findPluginJars() throws MalformedURLException {
        //where to search
        File pluginsDir = new File("plugins");
        if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            System.err.println("plugins/ directory not found");
            return new URL[0];
        }
        //find all jars and convert to URL array
        return Arrays.stream(new File("plugins").listFiles()).
                filter(file -> file.getName().toLowerCase().endsWith(".jar")).
                map(file -> {
                    try {
                        return file.toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }).
                toArray(URL[]::new);
    }

    private static List<DataPlugin> loadDataPlugins() {
        ArrayList<DataPlugin> dataPlugins = new ArrayList<>();
        //use service loader with extra classloader
        Iterator<DataPlugin> dataPluginIterator = ServiceLoader.load(DataPlugin.class).iterator();
        while (dataPluginIterator.hasNext()) {
            dataPlugins.add(dataPluginIterator.next());
        }
        return dataPlugins;
    }

    private static List<VisualizationPlugin> loadVisualPlugins() {
        ArrayList<VisualizationPlugin> visualPlugins = new ArrayList<>();
        Iterator<VisualizationPlugin> visPluginIterator = ServiceLoader.load(VisualizationPlugin.class).iterator();
        while (visPluginIterator.hasNext()) {
            visualPlugins.add(visPluginIterator.next());
        }
        return visualPlugins;
    }

    public static void main (String [] args) throws MalformedURLException {
        List<DataPlugin> dataPlugins = loadDataPlugins();
        List<VisualizationPlugin> visualPlugins = loadVisualPlugins();
        GUIStarter starter = new GUIStarter(dataPlugins, visualPlugins);
        starter.startFramework();
    }

}
