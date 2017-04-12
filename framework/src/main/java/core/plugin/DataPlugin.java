package core.plugin;

import core.category.CategoryCollection;

/**
 * DataPlugin's Interface.
 */
public interface DataPlugin {

    /**
     * @return The name of the framework. Must be concise.
     */
    String getName();

    /**
     * Enables Cache if true. Caching is only recommended if the data is static and will not change during the
     * duration of the program's execution.
     * @return If the DataPlugin wants its data/analyses to be cached by the framework.
     */
    boolean cacheEnabled();

    /**
     * Called when the framework is registered to the framework to do any initial setups.
     */
    void onRegister();

    /**
     * @return A String description of the type of data the plugin will send to the framework.
     */
    String getDescription();

    /**
     * A more detailed explanation of CategoryCollection is in the Class Description.
     * @return The fully compiled data structure of of nodes & each node's ownership of other types of nodes.
     */
    CategoryCollection getData();
}
