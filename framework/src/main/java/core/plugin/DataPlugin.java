package core.plugin;

import core.category.CategoryCollection;

/**
 * Created by Justin on 4/8/2017.
 */
public interface DataPlugin {

    String getName();
    boolean cacheEnabled();

    void onRegister();

    /**
     * @return The description of the type of data the plugin will send to the framework.
     */
    String getDescription();

    /**
     * @return The fully compiled data structure of of nodes & each node's ownership of other types of nodes.
     */
    CategoryCollection getData();
}
