
package com.myproject.tastyhub;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class PaymentManagement extends JFrame {

    // Midnight Navy + Warm Gold theme
    private static final Color BACKGROUND =
            new Color(16, 24, 32);

    private static final Color SIDEBAR_BG =
            new Color(23, 35, 47);

    private static final Color CARD_BG =
            new Color(32, 45, 57);

    private static final Color GOLD =
            new Color(244, 184, 96);

    private static final Color IVORY =
            new Color(245, 243, 238);

    private static final Color MUTED =
            new Color(170, 183, 196);

    private static final Color BORDER =
            new Color(52, 68, 83);

    private static final Color GREEN =
            new Color(89, 190, 139);

    private static final Color RED =
            new Color(231, 104, 104);

    private static final Color BLUE =
            new Color(105, 174, 235);

    private JTable paymentTable;
    private DefaultTableModel model;

    public PaymentManagement() {
        setTitle("TastyHub | Payment Management");
        setSize(1050, 650);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        initializeComponents();
        loadPayments();
    }

    // ---------------- MAIN INTERFACE ----------------

    private void initializeComponents() {

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);

        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, BORDER),
                new EmptyBorder(22, 26, 22, 26)
        ));

        JPanel headingText = new JPanel(
                new java.awt.GridLayout(2, 1, 0, 6));
        headingText.setOpaque(false);

        JLabel heading = new JLabel("Payment Management");
        heading.setFont(
                new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(IVORY);

        JLabel subtitle = new JLabel(
                "Verify received payments and track order payment status");
        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        headingText.add(heading);
        headingText.add(subtitle);

        JLabel badge = new JLabel("PAYMENT CONTROL");
        badge.setFont(
                new Font("SansSerif", Font.BOLD, 10));
        badge.setForeground(GOLD);
        badge.setOpaque(true);
        badge.setBackground(CARD_BG);
        badge.setBorder(
                new EmptyBorder(9, 12, 9, 12));

        header.add(headingText, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // CONTENT
        JPanel content = new JPanel(
                new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(
                new EmptyBorder(22, 26, 22, 26));

        // TABLE SECTION HEADING
        JPanel tableHeading = new JPanel(
                new BorderLayout());
        tableHeading.setBackground(BACKGROUND);

        JPanel headingLabels = new JPanel(
                new java.awt.GridLayout(2, 1, 0, 5));
        headingLabels.setOpaque(false);

        JLabel tableTitle = new JLabel("Payment Records");
        tableTitle.setFont(
                new Font("SansSerif", Font.BOLD, 19));
        tableTitle.setForeground(IVORY);

        JLabel tableSubtitle = new JLabel(
                "Select an order to confirm a verified payment");
        tableSubtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        tableSubtitle.setForeground(MUTED);

        headingLabels.add(tableTitle);
        headingLabels.add(tableSubtitle);

        tableHeading.add(
                headingLabels, BorderLayout.WEST);

        // PAYMENT TABLE
        model = new DefaultTableModel(
                new String[]{
                    "Order ID",
                    "Customer ID",
                    "Amount (UGX)",
                    "Payment Method",
                    "Payment Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(
                    int row, int column) {
                return false;
            }
        };

        paymentTable = new JTable(model);
        styleTable();

        JScrollPane scrollPane =
                new JScrollPane(paymentTable);

        scrollPane.setBackground(CARD_BG);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER));

        JPanel tablePanel = new JPanel(
                new BorderLayout(0, 13));
        tablePanel.setBackground(BACKGROUND);

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        content.add(tablePanel, BorderLayout.CENTER);

        // ACTION BUTTONS
        JButton confirmButton = createButton(
                "Confirm Payment", GOLD);

        JButton refreshButton = createButton(
                "Refresh", BLUE);

        JButton closeButton = createButton(
                "Close", CARD_BG);

        confirmButton.addActionListener(
                e -> confirmPayment());

        refreshButton.addActionListener(
                e -> loadPayments());

        closeButton.addActionListener(
                e -> dispose());

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(BACKGROUND);

        buttons.add(confirmButton);
        buttons.add(refreshButton);
        buttons.add(closeButton);

        content.add(buttons, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    // ---------------- TABLE DESIGN ----------------

    private void styleTable() {
        paymentTable.setRowHeight(42);
        paymentTable.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        paymentTable.setForeground(IVORY);
        paymentTable.setBackground(CARD_BG);
        paymentTable.setGridColor(BORDER);
        paymentTable.setShowVerticalLines(false);
        paymentTable.setShowHorizontalLines(true);

        paymentTable.setSelectionBackground(
                new Color(69, 61, 48));
        paymentTable.setSelectionForeground(IVORY);

        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        paymentTable.setAutoCreateRowSorter(true);
        paymentTable.setFillsViewportHeight(true);
        paymentTable.setIntercellSpacing(
                new Dimension(0, 1));

        JTableHeader tableHeader =
                paymentTable.getTableHeader();

        tableHeader.setFont(
                new Font("SansSerif", Font.BOLD, 12));
        tableHeader.setBackground(SIDEBAR_BG);
        tableHeader.setForeground(GOLD);
        tableHeader.setOpaque(true);
        tableHeader.setPreferredSize(
                new Dimension(100, 42));
        tableHeader.setReorderingAllowed(false);

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                super.getTableCellRendererComponent(
                        table, value, isSelected,
                        hasFocus, row, column);

                setBorder(BorderFactory.createEmptyBorder(
                        0, 10, 0, 10));

                if (isSelected) {
                    setBackground(
                            table.getSelectionBackground());
                    setForeground(
                            table.getSelectionForeground());
                } else {
                    setBackground(CARD_BG);

                    // Highlight payment status.
                    if (column == 4) {
                        String status = value == null
                                ? ""
                                : value.toString().trim();

                        if ("Paid".equalsIgnoreCase(status)) {
                            setForeground(GREEN);
                            setFont(new Font(
                                    "SansSerif",
                                    Font.BOLD, 13));
                        } else {
                            setForeground(GOLD);
                            setFont(new Font(
                                    "SansSerif",
                                    Font.BOLD, 13));
                        }
                    } else {
                        setForeground(IVORY);
                        setFont(new Font(
                                "SansSerif",
                                Font.PLAIN, 13));
                    }
                }

                return this;
            }
        };

        paymentTable.setDefaultRenderer(
                Object.class, renderer);

        paymentTable.getColumnModel()
                .getColumn(0).setPreferredWidth(90);

        paymentTable.getColumnModel()
                .getColumn(1).setPreferredWidth(110);

        paymentTable.getColumnModel()
                .getColumn(2).setPreferredWidth(160);

        paymentTable.getColumnModel()
                .getColumn(3).setPreferredWidth(180);

        paymentTable.getColumnModel()
                .getColumn(4).setPreferredWidth(160);
    }

    // ---------------- BUTTON DESIGN ----------------

    private JButton createButton(
            String text, Color baseColor) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 12));

        button.setForeground(
                baseColor.equals(GOLD)
                        ? BACKGROUND : IVORY);

        button.setBackground(baseColor);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setRolloverEnabled(false);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        baseColor.equals(CARD_BG)
                                ? BORDER : baseColor),
                BorderFactory.createEmptyBorder(
                        11, 16, 11, 16)
        ));

        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        Color hoverColor = baseColor.equals(GOLD)
                ? new Color(255, 202, 125)
                : baseColor.equals(BLUE)
                    ? new Color(133, 192, 245)
                    : SIDEBAR_BG;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    // ---------------- LOAD PAYMENT RECORDS ----------------

    private void loadPayments() {
        model.setRowCount(0);

        String sql =
                "SELECT Order_id, Customer_id, total_amount, "
                + "payment_method, payment_status "
                + "FROM `order` ORDER BY Order_id DESC";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                double amount =
                        rs.getDouble("total_amount");

                String paymentMethod =
                        rs.getString("payment_method");

                String paymentStatus =
                        rs.getString("payment_status");

                model.addRow(new Object[]{
                    rs.getInt("Order_id"),
                    rs.getInt("Customer_id"),
                    String.format(Locale.US, "%,.0f", amount),
                    paymentMethod == null
                            ? "Not specified" : paymentMethod,
                    paymentStatus == null
                            || paymentStatus.trim().isEmpty()
                            ? "Pending" : paymentStatus
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load payment records:\n"
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ---------------- CONFIRM PAYMENT ----------------

    private void confirmPayment() {
        int selectedRow = paymentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an order from the table first.",
                    "No Order Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row =
                paymentTable.convertRowIndexToModel(selectedRow);

        int orderId = ((Number)
                model.getValueAt(row, 0)).intValue();

        String status =
                String.valueOf(model.getValueAt(row, 4));

        if ("Paid".equalsIgnoreCase(status.trim())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Order #" + orderId
                    + " is already marked as Paid.",
                    "Payment Already Recorded",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Have you verified that payment for Order #"
                + orderId + " was actually received?\n\n"
                + "Only confirm if the payment has been verified.",
                "Confirm Payment",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "UPDATE `order` "
                + "SET payment_status = ? "
                + "WHERE Order_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {
            ps.setString(1, "Paid");
            ps.setInt(2, orderId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Payment for Order #" + orderId
                        + " has been recorded as Paid.",
                        "Payment Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadPayments();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No matching order was found.",
                        "Order Not Found",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not update payment:\n"
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Use the default look and feel.
            }

            new PaymentManagement().setVisible(true);
        });
    }
}
