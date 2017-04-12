import core.framework.DataVisualizationFramework;
import core.plugin.DataPlugin;
import gui.FrameworkGUI;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by Justin on 4/8/2017.
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


    public static void main (String [] args) throws MalformedURLException {
        URL[] jars = findPluginJars();
        //create a class loader that searches in these extra jar files
        ClassLoader cl = new URLClassLoader(jars, Thread.currentThread().getContextClassLoader());
        //use service loader with extra classloader
        Iterator<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class, cl).iterator();
        while (plugins.hasNext()) {
            System.out.println(plugins.next());
        }


        //SwingUtilities.invokeLater(Main::startFramework);
    }

    private static void startFramework() {
        DataVisualizationFramework core = new DataVisualizationFramework();
        FrameworkGUI gui = new FrameworkGUI(core);
    }
}
