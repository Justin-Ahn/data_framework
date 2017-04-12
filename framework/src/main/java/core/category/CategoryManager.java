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

    public Set<String> getCategorySet() {
        return new HashSet<>(categories);
    }
    //Register Category?
}
