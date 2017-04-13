package core.plugin;

import core.category.CategoryCollection;

import java.util.ArrayList;
import java.util.List;

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
     * The number of inputs the plugin requests from the user must be bounded.
     * @return The number of String inputs the plugin wants from the user.
     */
    int getNumInputs();

    /**
     * Each element in the List will be the description of what the respective input should be.
     * getNumInputs() should equal the List.size().
     * If getNumInputs == 0, getInputDescription can be null.
     * @return The Description of what kind of inputs the Plugin wants from the user.
     */
    ArrayList<String> getInputDescription();

    /**
     * A more detailed explanation of CategoryCollection is in the Class Description.
     * The ArrayList inputs will be determined by getNumInputs(). The ArrayList will be empty if getNumInputs() == 0.
     * @return The fully compiled data structure of nodes & each node's ownership of other types of nodes.
     */
    CategoryCollection getData(List<String> inputs);
}
