
package com.myproject.tastyhub;

public class OrderItem {
    private FoodItem food;
    private int quantity;

    public OrderItem(FoodItem food, int quantity) {
        if (food == null) {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        this.food = food;
        this.quantity = quantity;
    }

    public FoodItem getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than zero."
            );
        }
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return food.getPrice() * quantity;
    }
}
