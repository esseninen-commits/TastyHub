
package com.myproject.tastyhub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminComplaintWindow extends JFrame {

    private JTable complaintTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusComboBox;

    public AdminComplaintWindow() {

        setTitle("TastyHub - Manage Delivery Complaints");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(255, 247, 237));

        JLabel heading = new JLabel(
                "Customer Delivery Complaints",
                SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{
                    "Complaint ID",
                    "Order ID",
                    "Type",
                    "Message",
                    "Date Submitted",
                    "Status"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        complaintTable = new JTable(tableModel);
        complaintTable.setRowHeight(28);
        complaintTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        complaintTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF);

        complaintTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        complaintTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        complaintTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        complaintTable.getColumnModel().getColumn(3).setPreferredWidth(350);
        complaintTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        complaintTable.getColumnModel().getColumn(5).setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(complaintTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        bottomPanel.setBackground(new Color(255, 247, 237));

        bottomPanel.add(new JLabel("Update selected complaint to:"));

        statusComboBox = new JComboBox<>(
                new String[]{"Open", "Investigating", "Resolved"});
        bottomPanel.add(statusComboBox);

        JButton updateButton = new JButton("Update Status");
        updateButton.setBackground(new Color(232, 93, 4));
        updateButton.setForeground(Color.WHITE);

        JButton refreshButton = new JButton("Refresh Complaints");
        bottomPanel.add(updateButton);
        bottomPanel.add(refreshButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> updateComplaintStatus());
        refreshButton.addActionListener(e -> loadComplaints());

        add(mainPanel);

        loadComplaints();
        setVisible(true);
    }

    private void loadComplaints() {

        tableModel.setRowCount(0);

        String sql =
                "SELECT complaint_id, order_id, complaint_type, "
                + "complaint_message, complaint_date, complaint_status "
                + "FROM delivery_complaints "
                + "ORDER BY complaint_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("complaint_id"),
                    rs.getInt("order_id"),
                    rs.getString("complaint_type"),
                    rs.getString("complaint_message"),
                    rs.getTimestamp("complaint_date"),
                    rs.getString("complaint_status")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load complaints.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateComplaintStatus() {

        int selectedRow = complaintTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a complaint from the table first.");
            return;
        }

        int complaintId = (int) tableModel.getValueAt(
                selectedRow, 0);

        String newStatus = (String) statusComboBox.getSelectedItem();

        String sql =
                "UPDATE delivery_complaints "
                + "SET complaint_status = ? "
                + "WHERE complaint_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, complaintId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Complaint status updated successfully.");

                loadComplaints();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No complaint was updated.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not update complaint status.\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
