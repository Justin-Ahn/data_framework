package core.category;

/**
 * Created by tianyugu on 4/8/17.
 */
public class Data {
    private final String category;
    private final String name;

    /**
     *
     * @param category the type of the instance
     * @param name the name of the instance
     */
    public Data(String category, String name) {
        this.category = category;
        this.name = name;
    }

    /**
     *
     * @return The type of the instance
     */
    public String getCategory() {
        return this.category;
    }

    /**
     *
     * @return The name of the instance
     */
    public String getName() {
        return this.name;
    }

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
