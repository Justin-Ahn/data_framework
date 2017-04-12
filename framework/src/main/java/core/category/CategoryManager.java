package core.category;

import java.util.HashSet;
import java.util.Set;

/**
 * A Manager that is linked to each instance of CategoryCollection. The Manager keeps track of
 * all the categories that the Data Plugin wants to create. Thus, the developer of the data plugin
 * must at least think about the data categories that the Data Plugin will produce.
 *
 * Furthermore, this class aids in checking that all instances of a Data's category is correct, as
 * there could have been typos / errors when inputting each category. Using a bare String for keeping track of
 * data category types meant that it was lightweight but more risk-averse. This class tries to minimize that risk.
 */
public class CategoryManager {
    private final Set<String> categories;

    public CategoryManager() {
        categories = new HashSet<>();
    }

    /**
     * Called to register a new Data Category that will be used by the Data Plugin / CategoryCollection.
     * @param category The String name of that category.
     */
    public void registerCategory(String category) {
        if (categories.contains(category)) {
            //Alternative ways to error handle?
            System.out.println("Duplicate Category Registered");
        }
        categories.add(category);
    }

    /**
     * @param category The string name of the Data Category being checked.
     * @return Whether the Manager was already notified of this category.
     */
    public boolean contains(String category) {
        return categories.contains(category);
    }

    /**
     * Clears the Manager's memory.
     */
    public void clear() {
        categories.clear();
    }

    /**
     * A Helper method for the Framework -> Needed for Caching a RelationshipData.
     * Each instance of a RelationshipData is tied to
     * 1) Data Plugin
     * 2) Node
     * 3) Link
     * Thus this method provides a string concat. of those three to make a semi-valid key.
     * @param node
     * @param link
     * @return
     */
    public String getNodeLinkPluginHash(String node, String link) {
        if (this.contains(node) && this.contains(link)) {
            return node + link + this.toString();
        }
        else {
            throw new IllegalArgumentException("Neither Node nor Link is a registered category for this Manager");
        }
    }

    /**
     * @return The set of categories the Manager has received from the Data Plugin.
     */
    public Set<String> getCategorySet() {
        return new HashSet<>(categories);
    }
}
