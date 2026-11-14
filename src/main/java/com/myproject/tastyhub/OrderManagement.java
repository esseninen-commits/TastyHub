
package com.myproject.tastyhub;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrderManagement extends JFrame {

    private JTable ordersTable;
    private DefaultTableModel tableModel;

    private JLabel totalValue;
    private JLabel pendingValue;
    private JLabel deliveredValue;
    private JLabel footerLabel;

    private JComboBox<String> statusBox;

    // TastyHub dashboard theme
    private final Color BG = new Color(8, 14, 21);
    private final Color HEADER_BG = new Color(10, 17, 25);
    private final Color CARD_BG = new Color(17, 25, 35);
    private final Color PANEL_BG = new Color(12, 20, 29);
    private final Color ORANGE = new Color(255, 105, 0);
    private final Color DARK_ORANGE = new Color(218, 83, 0);
    private final Color WHITE = new Color(245, 247, 250);
    private final Color MUTED = new Color(174, 185, 200);
    private final Color BORDER = new Color(43, 54, 68);
    private final Color GREEN = new Color(14, 195, 128);
    private final Color BLUE = new Color(28, 139, 255);
    private final Color RED = new Color(255, 75, 90);

    private final String[] statuses = {
        "Pending",
        "Preparing",
        "Out for Delivery",
        "Delivered",
        "Cancelled"
    };

    public OrderManagement() {

        setTitle("TastyHub | Order Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        loadOrders();
    }

    // ==================================================
    // HEADER
    // ==================================================

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout(18, 0));
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
            new EmptyBorder(14, 24, 14, 24)
        ));

        JPanel brand = new JPanel(new FlowLayout(
            FlowLayout.LEFT, 13, 0
        ));
        brand.setOpaque(false);

        OrderIcon icon = new OrderIcon(ORANGE);
        brand.add(icon);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(
            textPanel, BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel(
            "<html><font color='#F5F7FA'>Tasty</font>"
            + "<font color='#FF6900'>Hub</font></html>"
        );
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
            "FOOD DELIVERY MANAGEMENT SYSTEM"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 10));
        subtitle.setForeground(MUTED);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(subtitle);

        brand.add(textPanel);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel section = new JLabel("ORDER MANAGEMENT");
        section.setFont(new Font("Arial", Font.BOLD, 12));
        section.setForeground(WHITE);
        section.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel role = new JLabel("Administrator workspace");
        role.setFont(new Font("Arial", Font.PLAIN, 11));
        role.setForeground(MUTED);
        role.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(section);
        right.add(Box.createVerticalStrut(5));
        right.add(role);

        header.add(brand, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    // ==================================================
    // CUSTOM MANAGE ORDERS ICON
    // ==================================================

    private class OrderIcon extends JPanel {

        private final Color accent;

        OrderIcon(Color accent) {
            this.accent = accent;
            setOpaque(false);
            setPreferredSize(new Dimension(48, 48));
            setMinimumSize(new Dimension(48, 48));
            setMaximumSize(new Dimension(48, 48));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(255, 105, 0, 35));
            g2.fillRoundRect(0, 0, 48, 48, 12, 12);

            g2.setColor(accent);
            g2.setStroke(new BasicStroke(
                2.2f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
            ));

            // Order sheet
            g2.drawRoundRect(13, 9, 23, 30, 4, 4);

            // Clip at the top
            g2.drawRoundRect(19, 6, 11, 7, 3, 3);

            // Lines representing order details
            g2.drawLine(18, 20, 30, 20);
            g2.drawLine(18, 25, 30, 25);
            g2.drawLine(18, 30, 26, 30);

            // Small check mark
            g2.setColor(WHITE);
            g2.setStroke(new BasicStroke(
                2.2f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
            ));
            g2.drawLine(27, 34, 30, 37);
            g2.drawLine(30, 37, 35, 31);

            g2.dispose();
        }
    }

    // ==================================================
    // MAIN CONTENT
    // ==================================================

    private JPanel createMainContent() {

        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(18, 22, 12, 22));

        JPanel upper = new JPanel();
        upper.setOpaque(false);
        upper.setLayout(new BoxLayout(
            upper, BoxLayout.Y_AXIS
        ));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(
            headingText, BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel("Manage Orders");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(WHITE);

        JLabel description = new JLabel(
            "Monitor customer orders, verify payments and manage delivery progress."
        );
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setForeground(MUTED);

        headingText.add(title);
        headingText.add(Box.createVerticalStrut(5));
        headingText.add(description);

        JButton refresh = new JButton("↻  Refresh Orders");
        styleSecondaryButton(refresh, 145);
        refresh.addActionListener(e -> loadOrders());

        heading.add(headingText, BorderLayout.WEST);
        heading.add(refresh, BorderLayout.EAST);

        upper.add(heading);
        upper.add(Box.createVerticalStrut(17));
        upper.add(createSummarySection());

        upper.add(Box.createVerticalStrut(18));

        // Orders table
        tableModel = new DefaultTableModel(
            new String[] {
                "Order ID",
                "Customer ID",
                "Order Date",
                "Delivery Area",
                "Delivery Address",
                "Order Status",
                "Total Amount",
                "Payment Method",
                "Payment Status"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ordersTable = new JTable(tableModel);
        styleOrdersTable();

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.setBackground(CARD_BG);
        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.getHorizontalScrollBar().setUnitIncrement(18);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 0));
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setBackground(CARD_BG);
        tableHeading.setBorder(new EmptyBorder(12, 15, 12, 15));

        JLabel tableTitle = new JLabel("Order Records");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 15));
        tableTitle.setForeground(WHITE);

        JLabel tableHint = new JLabel("Select a row to manage an order");
        tableHint.setFont(new Font("Arial", Font.PLAIN, 11));
        tableHint.setForeground(MUTED);

        tableHeading.add(tableTitle, BorderLayout.WEST);
        tableHeading.add(tableHint, BorderLayout.EAST);

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        main.add(upper, BorderLayout.NORTH);
        main.add(tablePanel, BorderLayout.CENTER);
        main.add(createActionPanel(), BorderLayout.SOUTH);

        return main;
    }

    // ==================================================
    // SUMMARY CARDS
    // ==================================================

    private JPanel createSummarySection() {

        JPanel summary = new JPanel(new GridLayout(1, 3, 14, 0));
        summary.setOpaque(false);
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.setPreferredSize(new Dimension(100, 100));
        summary.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, 100
        ));

        JPanel totalCard = createSummaryCard(
            "TOTAL ORDERS",
            "All orders recorded in the system",
            ORANGE
        );
        totalValue = (JLabel) totalCard.getClientProperty("valueLabel");

        JPanel pendingCard = createSummaryCard(
            "PENDING ORDERS",
            "Awaiting further processing",
            BLUE
        );
        pendingValue = (JLabel) pendingCard.getClientProperty("valueLabel");

        JPanel deliveredCard = createSummaryCard(
            "DELIVERED ORDERS",
            "Successfully delivered orders",
            GREEN
        );
        deliveredValue = (JLabel) deliveredCard.getClientProperty("valueLabel");

        summary.add(totalCard);
        summary.add(pendingCard);
        summary.add(deliveredCard);

        return summary;
    }

    private JPanel createSummaryCard(
        String heading,
        String description,
        Color accent
    ) {

        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(11, 15, 10, 15)
        ));

        JPanel top = new JPanel(new BorderLayout(8, 0));
        top.setOpaque(false);

        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Arial", Font.BOLD, 13));
        dot.setForeground(accent);

        JLabel label = new JLabel(heading);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setForeground(MUTED);

        top.add(dot, BorderLayout.WEST);
        top.add(label, BorderLayout.CENTER);

        JLabel value = new JLabel("0");
        value.setFont(new Font("Arial", Font.BOLD, 25));
        value.setForeground(WHITE);

        JLabel detail = new JLabel(description);
        detail.setFont(new Font("Arial", Font.PLAIN, 10));
        detail.setForeground(accent);

        JPanel lower = new JPanel();
        lower.setOpaque(false);
        lower.setLayout(new BoxLayout(lower, BoxLayout.Y_AXIS));

        lower.add(value);
        lower.add(Box.createVerticalStrut(2));
        lower.add(detail);

        card.add(top, BorderLayout.NORTH);
        card.add(lower, BorderLayout.CENTER);

        card.putClientProperty("valueLabel", value);

        return card;
    }

    // ==================================================
    // TABLE STYLING
    // ==================================================

    private void styleOrdersTable() {

        ordersTable.setAutoCreateRowSorter(true);
        ordersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ordersTable.setRowHeight(35);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 12));
        ordersTable.setForeground(WHITE);
        ordersTable.setBackground(CARD_BG);
        ordersTable.setSelectionBackground(new Color(79, 49, 29));
        ordersTable.setSelectionForeground(WHITE);
        ordersTable.setGridColor(BORDER);
        ordersTable.setShowVerticalLines(false);
        ordersTable.setShowHorizontalLines(true);
        ordersTable.setIntercellSpacing(new Dimension(0, 1));
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        JTableHeader header = ordersTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(25, 36, 48));
        header.setForeground(WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // Wide enough for useful information, with horizontal scrolling.
        int[] widths = {
            85, 105, 165, 145, 250, 145, 150, 150, 145
        };

        for (int i = 0; i < widths.length; i++) {
            ordersTable.getColumnModel()
                .getColumn(i)
                .setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer centered =
            new DefaultTableCellRenderer();

        centered.setHorizontalAlignment(SwingConstants.CENTER);
        centered.setForeground(WHITE);
        centered.setBackground(CARD_BG);
        centered.setBorder(new EmptyBorder(0, 5, 0, 5));

        ordersTable.getColumnModel().getColumn(0)
            .setCellRenderer(centered);
        ordersTable.getColumnModel().getColumn(1)
            .setCellRenderer(centered);
        ordersTable.getColumnModel().getColumn(5)
            .setCellRenderer(centered);
        ordersTable.getColumnModel().getColumn(8)
            .setCellRenderer(centered);

        DefaultTableCellRenderer amountRenderer =
            new DefaultTableCellRenderer();

        amountRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        amountRenderer.setForeground(ORANGE);
        amountRenderer.setBackground(CARD_BG);
        amountRenderer.setBorder(new EmptyBorder(0, 5, 0, 12));

        ordersTable.getColumnModel().getColumn(6)
            .setCellRenderer(amountRenderer);
    }

    // ==================================================
    // ACTION PANEL
    // ==================================================

    private JPanel createActionPanel() {

        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(11, 13, 11, 13)
        ));

        JPanel statusControls = new JPanel(new FlowLayout(
            FlowLayout.LEFT, 9, 0
        ));
        statusControls.setOpaque(false);

        JLabel statusLabel = new JLabel("Order status");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(WHITE);

        statusBox = new JComboBox<>(statuses);
        statusBox.setFont(new Font("Arial", Font.PLAIN, 12));
        statusBox.setPreferredSize(new Dimension(155, 36));
        statusBox.setBackground(CARD_BG);
        statusBox.setForeground(WHITE);

        JButton updateButton = new JButton("Update Status");
        stylePrimaryButton(updateButton, 125);
        updateButton.addActionListener(e ->
            updateOrderStatus((String) statusBox.getSelectedItem())
        );

        statusControls.add(statusLabel);
        statusControls.add(statusBox);
        statusControls.add(updateButton);

        JPanel otherActions = new JPanel(new FlowLayout(
            FlowLayout.RIGHT, 8, 0
        ));
        otherActions.setOpaque(false);

        JButton paymentButton = new JButton("Confirm Payment");
        stylePrimaryButton(paymentButton, 145);
        paymentButton.addActionListener(e -> confirmPayment());

        JButton complaintsButton = new JButton("Delivery Complaints");
        styleSecondaryButton(complaintsButton, 155);
        complaintsButton.addActionListener(e -> openComplaints());

        otherActions.add(paymentButton);
        otherActions.add(complaintsButton);

        panel.add(statusControls, BorderLayout.WEST);
        panel.add(otherActions, BorderLayout.EAST);

        return panel;
    }

    // ==================================================
    // FOOTER
    // ==================================================

    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(HEADER_BG);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER),
            new EmptyBorder(9, 22, 9, 22)
        ));

        footerLabel = new JLabel("Loading orders...");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        footerLabel.setForeground(MUTED);

        JLabel brand = new JLabel(
            "<html><font color='#FF6900'><b>TastyHub</b></font>"
            + "<font color='#AEB9C8'>  |  Order Management</font></html>"
        );
        brand.setFont(new Font("Arial", Font.PLAIN, 11));

        footer.add(footerLabel, BorderLayout.WEST);
        footer.add(brand, BorderLayout.EAST);

        return footer;
    }

    // ==================================================
    // BUTTON STYLES
    // ==================================================

    private void stylePrimaryButton(JButton button, int width) {

        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(ORANGE);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(width, 36));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(DARK_ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ORANGE);
            }
        });
    }

    private void styleSecondaryButton(JButton button, int width) {

        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(CARD_BG);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(5, 8, 5, 8)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(width, 36));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 42, 55));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
            }
        });
    }

    // ==================================================
    // LOAD ORDERS
    // ==================================================

    private void loadOrders() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        String sql =
            "SELECT Order_id, Customer_id, Order_date, " +
            "delivery_area, delivery_address, status, " +
            "total_amount, payment_method, payment_status " +
            "FROM `order` ORDER BY Order_id DESC";

        int total = 0;
        int pending = 0;
        int delivered = 0;

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection is unavailable.");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String status = rs.getString("status");
                    double amount = rs.getDouble("total_amount");

                    tableModel.addRow(new Object[] {
                        rs.getInt("Order_id"),
                        rs.getInt("Customer_id"),
                        rs.getTimestamp("Order_date"),
                        rs.getString("delivery_area"),
                        rs.getString("delivery_address"),
                        status,
                        "UGX " + String.format("%,.0f", amount),
                        rs.getString("payment_method"),
                        rs.getString("payment_status")
                    });

                    total++;

                    if (status != null
                            && "Pending".equalsIgnoreCase(status.trim())) {
                        pending++;
                    }

                    if (status != null
                            && "Delivered".equalsIgnoreCase(status.trim())) {
                        delivered++;
                    }
                }
            }

            totalValue.setText(String.valueOf(total));
            pendingValue.setText(String.valueOf(pending));
            deliveredValue.setText(String.valueOf(delivered));

            footerLabel.setText(
                "Showing " + total + " order"
                + (total == 1 ? "" : "s")
            );

        } catch (SQLException ex) {

            footerLabel.setText("Unable to load orders");

            JOptionPane.showMessageDialog(
                this,
                "Could not load orders:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ==================================================
    // CONFIRM PAYMENT
    // ==================================================

    private void confirmPayment() {

        int selectedRow = ordersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an order from the table first.",
                "No Order Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = ordersTable.convertRowIndexToModel(selectedRow);

        int orderId = ((Number)
            tableModel.getValueAt(modelRow, 0)).intValue();

        String paymentStatus = String.valueOf(
            tableModel.getValueAt(modelRow, 8)
        );

        if ("Paid".equalsIgnoreCase(paymentStatus.trim())) {
            JOptionPane.showMessageDialog(
                this,
                "Order #" + orderId + " is already marked as paid.",
                "Payment Already Confirmed",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Have you verified that payment for order #"
                + orderId + " was actually received?\n\n"
                + "Only confirm if you have checked the payment.",
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
            "UPDATE `order` SET payment_status = ? " +
            "WHERE Order_id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection is unavailable.");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "Paid");
                ps.setInt(2, orderId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Payment for order #" + orderId
                            + " has been marked as Paid.",
                        "Payment Confirmed",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Order was not found.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not confirm payment:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    // ==================================================
    // UPDATE ORDER STATUS
    // ==================================================

    private void updateOrderStatus(String newStatus) {

        int selectedRow = ordersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select an order from the table first.",
                "No Order Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (newStatus == null || newStatus.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a valid order status.",
                "Invalid Status",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = ordersTable.convertRowIndexToModel(selectedRow);

        int orderId = ((Number)
            tableModel.getValueAt(modelRow, 0)).intValue();

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                throw new SQLException("Database connection is unavailable.");
            }

            String paymentSql =
                "SELECT payment_status FROM `order` WHERE Order_id = ?";

            try (PreparedStatement check =
                    conn.prepareStatement(paymentSql)) {

                check.setInt(1, orderId);

                try (ResultSet rs = check.executeQuery()) {

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Order was not found.",
                            "Update Failed",
                            JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    String paymentStatus = rs.getString("payment_status");

                    boolean requiresPayment =
                        "Preparing".equals(newStatus)
                        || "Out for Delivery".equals(newStatus)
                        || "Delivered".equals(newStatus);

                    if (requiresPayment
                            && (paymentStatus == null
                            || !"Paid".equalsIgnoreCase(
                                paymentStatus.trim()))) {

                        JOptionPane.showMessageDialog(
                            this,
                            "This order has not been marked as paid.\n"
                                + "Confirm payment before preparing "
                                + "or dispatching it.",
                            "Payment Required",
                            JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                }
            }

            String updateSql =
                "UPDATE `order` SET status = ? WHERE Order_id = ?";

            try (PreparedStatement ps =
                    conn.prepareStatement(updateSql)) {

                ps.setString(1, newStatus);
                ps.setInt(2, orderId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Order #" + orderId
                            + " status changed to " + newStatus + ".",
                        "Status Updated",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Order was not found.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not update status:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    // ==================================================
    // DELIVERY COMPLAINTS
    // ==================================================

    private void openComplaints() {

        try {
            AdminComplaintWindow window = new AdminComplaintWindow();
            window.setLocationRelativeTo(this);
            window.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not open Delivery Complaints:\n"
                    + ex.getMessage(),
                "Window Error",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    // ==================================================
    // MAIN METHOD
    // ==================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ex) {
                System.out.println("Using default look and feel.");
            }

            OrderManagement window = new OrderManagement();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
