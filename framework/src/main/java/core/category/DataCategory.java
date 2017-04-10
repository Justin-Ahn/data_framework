package core.category;

/**
 * Created by tianyugu on 4/8/17.
 */
public class DataCategory {
    private final String type;
    private final String name;

    /**
     *
     * @param type the type of the instance
     * @param name the name of the instance
     */
    public DataCategory(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *
     * @return The type of the instance
     */
    public String getType() {
        return this.type;
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
        if (!(other instanceof DataCategory)) {
            return false;
        } else {
            DataCategory otherCategory = (DataCategory) other;
            return this.getType().equals(otherCategory.getType()) && this.getName().equals(otherCategory.getName());
        }
    }

    @Override
    public int hashCode() {
        String hashable = this.type + this.name;
        return hashable.hashCode();
    }

    @Override
    public String toString() {
        return "(Type: " + type + ", | Name: " + name+")";
    }
}
