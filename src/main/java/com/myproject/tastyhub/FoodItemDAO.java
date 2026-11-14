
package com.myproject.tastyhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemDAO {

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();

        String sql = "SELECT * FROM food_items";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Food_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String category = resultSet.getString("category");
                boolean available =
                        resultSet.getBoolean("available");

                FoodItem food = new FoodItem(
                        id, name, price, category, available
                );

                foodItems.add(food);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve food items.");
            e.printStackTrace();
        }

        return foodItems;
    }
}
