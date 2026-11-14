
package com.myproject.tastyhub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SalesReport extends JFrame {

    // Midnight Navy + Warm Gold theme
    private static final Color BACKGROUND =
            new Color(16, 24, 32);

    private static final Color HEADER_BG =
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

    private static final Color BLUE =
            new Color(105, 174, 235);

    private static final Color PURPLE =
            new Color(181, 147, 235);

    private JLabel totalSalesValue;
    private JLabel todaySalesValue;
    private JLabel pendingOrdersValue;
    private JLabel deliveredOrdersValue;

    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public SalesReport() {
        setTitle("TastyHub | Sales and Reports");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 580));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        loadReport();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        // ---------------- HEADER ----------------

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(
                        20, 26, 20, 26)
        ));

        JPanel headingText = new JPanel(
                new GridLayout(2, 1, 0, 6));
        headingText.setOpaque(false);

        JLabel title = new JLabel("Sales and Reports");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(IVORY);

        JLabel subtitle = new JLabel(
                "Monitor revenue, payments and delivery performance");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        headingText.add(title);
        headingText.add(subtitle);

        JPanel datePanel = new JPanel(
                new GridLayout(2, 1, 0, 5));
        datePanel.setOpaque(false);

        JLabel dateHeading = new JLabel(
                "REPORT DATE", SwingConstants.RIGHT);
        dateHeading.setFont(
                new Font("SansSerif", Font.BOLD, 10));
        dateHeading.setForeground(GOLD);

        JLabel dateValue = new JLabel(
                new SimpleDateFormat("dd MMM yyyy")
                        .format(new Date()),
                SwingConstants.RIGHT);
        dateValue.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        dateValue.setForeground(MUTED);

        datePanel.add(dateHeading);
        datePanel.add(dateValue);

        header.add(headingText, BorderLayout.WEST);
        header.add(datePanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ---------------- MAIN CONTENT ----------------

        JPanel content = new JPanel(new BorderLayout(0, 22));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(
                24, 26, 22, 26));

        // Summary cards

        JPanel summaryPanel = new JPanel(
                new GridLayout(1, 4, 15, 0));
        summaryPanel.setBackground(BACKGROUND);
        summaryPanel.setPreferredSize(
                new Dimension(900, 130));

        totalSalesValue = createValueLabel();
        todaySalesValue = createValueLabel();
        pendingOrdersValue = createValueLabel();
        deliveredOrdersValue = createValueLabel();

        summaryPanel.add(createSummaryCard(
                "TOTAL PAID SALES",
                totalSalesValue,
                GOLD,
                "Revenue from paid orders"));

        summaryPanel.add(createSummaryCard(
                "TODAY'S PAID SALES",
                todaySalesValue,
                BLUE,
                "Paid orders placed today"));

        summaryPanel.add(createSummaryCard(
                "PENDING ORDERS",
                pendingOrdersValue,
                PURPLE,
                "Orders awaiting completion"));

        summaryPanel.add(createSummaryCard(
                "DELIVERED ORDERS",
                deliveredOrdersValue,
                GREEN,
                "Orders marked delivered"));

        content.add(summaryPanel, BorderLayout.NORTH);

        // ---------------- RECENT ORDERS TABLE ----------------

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setBackground(BACKGROUND);

        JPanel tableHeading = new JPanel(
                new BorderLayout());
        tableHeading.setBackground(BACKGROUND);

        JLabel tableTitle = new JLabel("Recent Orders");
        tableTitle.setFont(
                new Font("SansSerif", Font.BOLD, 19));
        tableTitle.setForeground(IVORY);

        JLabel tableSubtitle = new JLabel(
                "The 20 most recent orders recorded in TastyHub");
        tableSubtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        tableSubtitle.setForeground(MUTED);

        JPanel tableHeadingText = new JPanel(
                new GridLayout(2, 1, 0, 5));
        tableHeadingText.setOpaque(false);
        tableHeadingText.add(tableTitle);
        tableHeadingText.add(tableSubtitle);

        tableHeading.add(
                tableHeadingText, BorderLayout.WEST);

        String[] columns = {
            "Order ID",
            "Order Date",
            "Total Amount (UGX)",
            "Delivery Status",
            "Payment Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(
                    int row, int column) {
                return false;
            }
        };

        ordersTable = new JTable(tableModel);
        styleTable();

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBackground(CARD_BG);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                BORDER));

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        content.add(tablePanel, BorderLayout.CENTER);

        // ---------------- BOTTOM BUTTONS ----------------

        JPanel buttonPanel = new JPanel(
                new BorderLayout());
        buttonPanel.setBackground(BACKGROUND);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel footer = new JLabel(
                "TastyHub Food Delivery System");
        footer.setFont(
                new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(MUTED);

        JPanel buttons = new JPanel(
                new java.awt.FlowLayout(
                        java.awt.FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(BACKGROUND);

        JButton refreshButton = createButton(
                "Refresh Report", true);

        JButton closeButton = createButton(
                "Close", false);

        refreshButton.addActionListener(e -> loadReport());
        closeButton.addActionListener(e -> dispose());

        buttons.add(refreshButton);
        buttons.add(closeButton);

        buttonPanel.add(footer, BorderLayout.WEST);
        buttonPanel.add(buttons, BorderLayout.EAST);

        content.add(buttonPanel, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    // ---------------- SUMMARY CARDS ----------------

    private JLabel createValueLabel() {
        JLabel label = new JLabel(
                "...", SwingConstants.LEFT);
        label.setFont(
                new Font("SansSerif", Font.BOLD, 21));
        label.setForeground(IVORY);
        return label;
    }

    private JPanel createSummaryCard(
            String heading,
            JLabel valueLabel,
            Color accent,
            String description) {

        JPanel card = new JPanel(
                new BorderLayout(0, 10));

        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(
                        16, 15, 14, 15)
        ));

        JPanel top = new JPanel(
                new BorderLayout(8, 0));
        top.setBackground(CARD_BG);

        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(
                new Font("SansSerif", Font.BOLD, 10));
        headingLabel.setForeground(MUTED);

        JPanel accentDot = new JPanel();
        accentDot.setBackground(accent);
        accentDot.setPreferredSize(
                new Dimension(7, 7));

        top.add(headingLabel, BorderLayout.CENTER);
        top.add(accentDot, BorderLayout.EAST);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 10));
        descriptionLabel.setForeground(MUTED);

        JPanel lower = new JPanel(
                new BorderLayout(0, 6));
        lower.setBackground(CARD_BG);
        lower.add(valueLabel, BorderLayout.NORTH);
        lower.add(descriptionLabel, BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(lower, BorderLayout.CENTER);

        return card;
    }

    // ---------------- TABLE STYLING ----------------

    private void styleTable() {
        ordersTable.setRowHeight(36);
        ordersTable.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        ordersTable.setForeground(IVORY);
        ordersTable.setBackground(CARD_BG);
        ordersTable.setGridColor(BORDER);
        ordersTable.setSelectionBackground(
                new Color(69, 61, 48));
        ordersTable.setSelectionForeground(IVORY);
        ordersTable.setShowVerticalLines(false);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setAutoCreateRowSorter(true);
        ordersTable.setIntercellSpacing(
                new Dimension(0, 1));

        JTableHeader tableHeader =
                ordersTable.getTableHeader();

        tableHeader.setBackground(SIDEBAR_BG);
        tableHeader.setForeground(GOLD);
        tableHeader.setFont(
                new Font("SansSerif", Font.BOLD, 11));
        tableHeader.setPreferredSize(
                new Dimension(100, 40));
        tableHeader.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER);
        centerRenderer.setBackground(CARD_BG);
        centerRenderer.setForeground(IVORY);
        centerRenderer.setBorder(
                BorderFactory.createEmptyBorder(0, 6, 0, 6));

        for (int i = 0; i < ordersTable.getColumnCount(); i++) {
            ordersTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        ordersTable.getColumnModel()
                .getColumn(0).setPreferredWidth(80);
        ordersTable.getColumnModel()
                .getColumn(1).setPreferredWidth(155);
        ordersTable.getColumnModel()
                .getColumn(2).setPreferredWidth(180);
        ordersTable.getColumnModel()
                .getColumn(3).setPreferredWidth(130);
        ordersTable.getColumnModel()
                .getColumn(4).setPreferredWidth(130);
    }

    // ---------------- BUTTON STYLING ----------------

    private JButton createButton(
            String text, boolean primary) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 12));

        button.setBackground(
                primary ? GOLD : CARD_BG);

        button.setForeground(
                primary ? BACKGROUND : IVORY);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        11, 19, 11, 19));

        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent e) {
                button.setBackground(
                        primary
                                ? new Color(255, 202, 125)
                                : SIDEBAR_BG);
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent e) {
                button.setBackground(
                        primary ? GOLD : CARD_BG);
            }
        });

        return button;
    }

    // ---------------- CURRENCY FORMAT ----------------

    private String formatUGX(double amount) {
        NumberFormat formatter =
                NumberFormat.getNumberInstance(Locale.US);

        formatter.setMaximumFractionDigits(0);
        formatter.setMinimumFractionDigits(0);

        return "UGX " + formatter.format(amount);
    }

    // ---------------- LOAD REPORT DATA ----------------

    private void loadReport() {
        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection == null) {
                throw new SQLException(
                        "Database connection is unavailable.");
            }

            // 1. Total sales from paid orders

            String totalSalesSQL =
                    "SELECT COALESCE(SUM(total_amount), 0) " +
                    "FROM `order` " +
                    "WHERE LOWER(TRIM(payment_status)) = 'paid'";

            try (Statement statement =
                         connection.createStatement();
                 ResultSet result =
                         statement.executeQuery(totalSalesSQL)) {

                if (result.next()) {
                    totalSalesValue.setText(
                            formatUGX(result.getDouble(1)));
                }
            }

            // 2. Today's paid sales

            String todaySalesSQL =
                    "SELECT COALESCE(SUM(total_amount), 0) " +
                    "FROM `order` " +
                    "WHERE LOWER(TRIM(payment_status)) = 'paid' " +
                    "AND DATE(Order_date) = CURDATE()";

            try (Statement statement =
                         connection.createStatement();
                 ResultSet result =
                         statement.executeQuery(todaySalesSQL)) {

                if (result.next()) {
                    todaySalesValue.setText(
                            formatUGX(result.getDouble(1)));
                }
            }

            // 3. Pending orders

            String pendingSQL =
                    "SELECT COUNT(*) FROM `order` " +
                    "WHERE LOWER(TRIM(status)) = 'pending'";

            try (Statement statement =
                         connection.createStatement();
                 ResultSet result =
                         statement.executeQuery(pendingSQL)) {

                if (result.next()) {
                    pendingOrdersValue.setText(
                            String.valueOf(result.getInt(1)));
                }
            }

            // 4. Delivered orders

            String deliveredSQL =
                    "SELECT COUNT(*) FROM `order` " +
                    "WHERE LOWER(TRIM(status)) = 'delivered'";

            try (Statement statement =
                         connection.createStatement();
                 ResultSet result =
                         statement.executeQuery(deliveredSQL)) {

                if (result.next()) {
                    deliveredOrdersValue.setText(
                            String.valueOf(result.getInt(1)));
                }
            }

            // 5. Most recent 20 orders

            tableModel.setRowCount(0);

            String recentOrdersSQL =
                    "SELECT Order_id, Order_date, total_amount, " +
                    "status, payment_status " +
                    "FROM `order` " +
                    "ORDER BY Order_date DESC LIMIT 20";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 recentOrdersSQL);
                 ResultSet result =
                         statement.executeQuery()) {

                while (result.next()) {
                    Object orderId =
                            result.getObject("Order_id");

                    Object orderDate =
                            result.getObject("Order_date");

                    double amount =
                            result.getDouble("total_amount");

                    String status =
                            result.getString("status");

                    String paymentStatus =
                            result.getString("payment_status");

                    tableModel.addRow(new Object[]{
                        orderId,
                        orderDate,
                        formatUGX(amount),
                        status,
                        paymentStatus
                    });
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load the sales report.\n\n"
                    + "Check your database connection and "
                    + "the order table columns.\n\n"
                    + "Details: " + ex.getMessage(),
                    "Sales Report Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SalesReport().setVisible(true);
        });
    }
}
