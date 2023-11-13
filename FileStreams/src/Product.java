public class Product {
    private String name;
    private String description;
    private String ID;
    private double cost;

    // Constructors

    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Utility functions for correctly formatted random product records
    public String getFormattedName() {
        return String.format("%-35s", name);
    }

    public String getFormattedDescription() {
        return String.format("%-75s", description);
    }

    public String getFormattedID() {
        return String.format("%-6s", ID);
    }

    public String getProductNameWithoutFormatting() {
        return name;
    }
    // Other utility functions as needed

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ID='" + ID + '\'' +
                ", cost=" + cost +
                '}';
    }
}
