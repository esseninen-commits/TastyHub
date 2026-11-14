
package com.myproject.tastyhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT full_name, phoneNo, "
                   + "delivery_area, address "
                   + "FROM customers";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Customer customer = new Customer(
                    resultSet.getString("full_name"),
                    resultSet.getString("phoneNo"),
                    resultSet.getString("delivery_area"),
                    resultSet.getString("address")
                );

                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve customers.");
            e.printStackTrace();
        }

        return customers;
    }
}
