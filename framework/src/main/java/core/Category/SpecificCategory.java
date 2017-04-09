package core.Category;

/**
 * Created by tianyugu on 4/8/17.
 */
public class SpecificCategory {
    private String type;
    private String name;

    /**
     *
     * @param type the type of the instance
     * @param name the name of the instance
     */
    public SpecificCategory(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *
     * @return the type of the instance
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @return the name of the instance
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SpecificCategory)) {
            return false;
        } else {
            SpecificCategory otherCategory = (SpecificCategory) other;
            return this.getType().equals(otherCategory.getType()) && this.getName().equals(otherCategory.getName());
        }
    }

    @Override
    public int hashCode() {
        String hashable = this.type + this.name;
        return hashable.hashCode();
    }
}
