
package com.myproject.tastyhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {

    public int submitOrder(Order order, String paymentMethod) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "An order must contain at least one food item."
            );
        }

        String customerSql =
                "INSERT INTO customers "
                + "(full_name, phoneNo, delivery_area, address) "
                + "VALUES (?, ?, ?, ?)";

        String orderSql =
        "INSERT INTO `order` "
        + "(Customer_id, delivery_area, delivery_address, "
        + "status, total_amount, payment_method, payment_status) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Corrected: the database column is unit_price, not price.
        String itemSql =
                "INSERT INTO order_items "
                + "(order_id, food_id, quantity, unit_price) "
                + "VALUES (?, ?, ?, ?)";
        int savedOrderId = -1;

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            if (conn == null) {
                throw new SQLException("Database connection is null.");
            }

            conn.setAutoCommit(false);

            // 1. Save customer
            int customerId;

            try (PreparedStatement ps = conn.prepareStatement(
                    customerSql, Statement.RETURN_GENERATED_KEYS)) {

                Customer customer = order.getCustomer();

                ps.setString(1, customer.getName());
                ps.setString(2, customer.getPhoneNo());
                ps.setString(3, customer.getDeliveryArea());
                ps.setString(4, customer.getAddress());

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException(
                                "Could not generate customer ID."
                        );
                    }

                    customerId = keys.getInt(1);
                }
            }

            // 2. Save order
            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(
                    orderSql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, customerId);
                ps.setString(2, order.getCustomer().getDeliveryArea());
                ps.setString(3, order.getDeliveryAddress());
                ps.setString(4, order.getStatus());
                ps.setDouble(5, order.getTotal() + 3000);
ps.setString(6, paymentMethod);
ps.setString(7, "Pending");
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException(
                                "Could not generate order ID."
                        );
                    }

                    orderId = keys.getInt(1);
                }
                savedOrderId = orderId;
            }
            

            // 3. Save order items
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {

                for (OrderItem item : order.getItems()) {

                    if (item == null || item.getFood() == null) {
                        throw new SQLException(
                                "Order contains an invalid food item."
                        );
                    }

                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getFood().getId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getFood().getPrice());

                    ps.addBatch();
                }

                ps.executeBatch();
            }

            // 4. Commit all changes together
            conn.commit();

            System.out.println(
                    "Order submitted successfully. Order ID: " + orderId
            );

            System.out.println(
                    "Total amount: UGX " + order.getTotal()
            );

           return orderId;

        } catch (SQLException e) {

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            System.err.println("Order submission failed:");
            e.printStackTrace();

            return -1;

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Error closing database connection:"
                    );
                    e.printStackTrace();
                }
            }
        }
    }
}
