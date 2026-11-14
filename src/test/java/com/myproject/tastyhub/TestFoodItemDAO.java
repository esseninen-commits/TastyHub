package com.myproject.tastyhub;

import java.util.List;

public class TestFoodItemDAO {

    public static void main(String[] args) {

        FoodItemDAO dao = new FoodItemDAO();

        List<FoodItem> foodItems = dao.getAllFoodItems();

        for (FoodItem food : foodItems) {
            System.out.println(
                food.getName() + " - UGX " + food.getPrice()
            );
        }

        System.out.println("Total foods retrieved: " + foodItems.size());
    }
}