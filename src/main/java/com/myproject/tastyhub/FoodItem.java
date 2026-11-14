
package com.myproject.tastyhub;

public class FoodItem {
    private int id;
    private String name;
    private double price;
    private String category;
    private boolean available;

    // New fields for the premium food menu
    private String description;
    private String imagePath;
    private String flavourTags;

    // Keep the original constructor so existing code still works
    public FoodItem(int id, String name, double price,
                    String category, boolean available) {
        this(id, name, price, category, available,
             "Deliciously prepared and ready to enjoy.",
             "",
             "Customer Favourite");
    }

    // New constructor with food presentation details
    public FoodItem(int id, String name, double price,
                    String category, boolean available,
                    String description, String imagePath,
                    String flavourTags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.description = description;
        this.imagePath = imagePath;
        this.flavourTags = flavourTags;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getFlavourTags() {
        return flavourTags;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFlavourTags(String flavourTags) {
        this.flavourTags = flavourTags;
    }
}
