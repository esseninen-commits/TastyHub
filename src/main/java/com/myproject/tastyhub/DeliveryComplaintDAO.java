
package com.myproject.tastyhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryComplaintDAO {

    // Set to true for project demonstrations.
    // Set to false to enforce the normal delay rule.
    private static final boolean DEMO_MODE = true;

    // =========================================
    // SAVE A DELIVERY COMPLAINT
    // =========================================

    public boolean submitComplaint(
            int orderId, String message) {

        String sql =
                "INSERT INTO delivery_complaints "
                + "(order_id, complaint_type, "
                + "complaint_message, complaint_status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setString(2, "Delivery Delay");
            stmt.setString(3, message);
            stmt.setString(4, "Open");

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error saving complaint: "
                    + e.getMessage());

            return false;
        }
    }

    // =========================================
    // CHECK COMPLAINT ELIGIBILITY
    // =========================================

    public boolean canComplain(int orderId) {

        String sql =
                "SELECT status, delivery_area, "
                + "TIMESTAMPDIFF(MINUTE, Order_date, NOW()) "
                + "AS elapsed_minutes "
                + "FROM `order` "
                + "WHERE Order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {

                // The order must exist.
                if (!rs.next()) {
                    return false;
                }

                String status = rs.getString("status");

                // Delivered orders cannot receive delay complaints.
                if ("Delivered".equalsIgnoreCase(status)) {
                    return false;
                }

                // DEMO MODE: Skip the waiting period.
                // The order must still exist and not be Delivered.
                if (DEMO_MODE) {
                    return true;
                }

                // NORMAL MODE: Enforce the estimated delay rule.
                String area = rs.getString("delivery_area");

                int elapsedMinutes =
                        rs.getInt("elapsed_minutes");

                int estimatedMinutes =
                        DeliveryETA.estimateMinutes(area);

                return elapsedMinutes >= estimatedMinutes + 5;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error checking complaint eligibility: "
                    + e.getMessage());

            return false;
        }
    }
}
