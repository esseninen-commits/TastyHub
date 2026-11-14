
package com.myproject.tastyhub;

import javax.swing.*;
import java.awt.*;

public class DeliveryComplaintWindow extends JFrame {

    private final JTextField orderIdField;
    private final JTextArea messageArea;
    private final JLabel statusLabel;

    private final DeliveryComplaintDAO complaintDAO;

    public DeliveryComplaintWindow() {

        complaintDAO = new DeliveryComplaintDAO();

        setTitle("TastyHub - Delivery Complaint");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 247, 237));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel heading = new JLabel(
                "Delivery Delay Complaint",
                SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridy = 0;
        panel.add(heading, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Enter your Order ID:"), gbc);

        orderIdField = new JTextField();
        gbc.gridy = 2;
        panel.add(orderIdField, gbc);

        JButton checkButton = new JButton(
                "Check Delivery Status");
        gbc.gridy = 3;
        panel.add(checkButton, gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Describe your complaint:"), gbc);

        messageArea = new JTextArea(4, 25);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(messageArea);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        panel.add(scrollPane, gbc);

        statusLabel = new JLabel(
                "Check your order before submitting.");
        statusLabel.setForeground(Color.DARK_GRAY);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panel.add(statusLabel, gbc);

        JButton submitButton = new JButton(
                "Submit Complaint");
        submitButton.setBackground(new Color(232, 93, 4));
        submitButton.setForeground(Color.WHITE);

        gbc.gridy = 7;
        panel.add(submitButton, gbc);

        checkButton.addActionListener(e -> checkEligibility());
        submitButton.addActionListener(e -> submitComplaint());

        add(panel);
        setVisible(true);
    }

    private int getOrderId() {

        try {
            int orderId = Integer.parseInt(
                    orderIdField.getText().trim());

            if (orderId <= 0) {
                throw new NumberFormatException();
            }

            return orderId;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid positive Order ID.",
                    "Invalid Order ID",
                    JOptionPane.WARNING_MESSAGE);

            return -1;
        }
    }

    private void checkEligibility() {

        int orderId = getOrderId();

        if (orderId == -1) {
            return;
        }

        if (complaintDAO.canComplain(orderId)) {
            statusLabel.setText(
                    "Eligible: You may submit a delay complaint.");
            statusLabel.setForeground(new Color(0, 120, 0));
        } else {
            statusLabel.setText(
                    "Not eligible yet, or order is not found.");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void submitComplaint() {

        int orderId = getOrderId();

        if (orderId == -1) {
            return;
        }

        String message = messageArea.getText().trim();

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please describe your delivery problem.");
            return;
        }

        if (!complaintDAO.canComplain(orderId)) {
            JOptionPane.showMessageDialog(
                    this,
                    "This order is not eligible for a complaint yet. "
                    + "It must be overdue by at least 5 minutes "
                    + "and must not be marked Delivered.");
            return;
        }

        boolean saved = complaintDAO.submitComplaint(
                orderId, message);

        if (saved) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your delivery complaint has been recorded.");

            messageArea.setText("");
            statusLabel.setText("Complaint submitted.");
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "The complaint could not be saved. "
                    + "Please check the database connection.");
        }
    }
}
