package core.category;

/**
 * A Data Structure that keeps track of only what data category the Data is in and its unique name.
 * Each data category name is unique, and each Data instance's name is unique in each data category.
 */
public class Data {
    private final String category;
    private final String name;

    /**
     * @param category the category of the instance
     * @param name the name of the instance
     */
    public Data(String category, String name) {
        this.category = category;
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }


    /**
     * 
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Data)) {
            return false;
        } else {
            Data otherCategory = (Data)other;
            return this.getCategory().equals(otherCategory.getCategory()) && this.getName().equals(otherCategory.getName());
        }
    }

    @Override
    public int hashCode() {
        String hashable = this.category + this.name;
        return hashable.hashCode();
    }

    @Override
    public String toString() {
        return "(Category: " + category + ", | Name: " + name + ")";
    }
}
