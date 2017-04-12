package core.category;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Justin on 4/11/2017.
 */
public class CategoryManager {
    private final Set<String> categories;

    public CategoryManager() {
        categories = new HashSet<>();
    }

    public void registerCategory(String category) {
        if (categories.contains(category)) {
            //Alternative ways to error handle?
            System.out.println("Duplicate Category Registered");
        }
        categories.add(category);
    }

    public boolean contains(String category) {
        return categories.contains(category);
    }

    public void clear() {
        categories.clear();
    }

    public String getNodeLinkPluginHash(String node, String link) {
        if (this.contains(node) && this.contains(link)) {
            return node + link + this.toString();
        }
        else {
            throw new IllegalArgumentException("Neither Node nor Link is a registered category for this Manager");
        }
    }

    public Set<String> getCategorySet() {
        return new HashSet<>(categories);
    }
}
