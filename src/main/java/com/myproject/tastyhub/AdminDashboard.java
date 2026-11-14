
package com.myproject.tastyhub;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminDashboard extends JFrame {

    private static final Color BG = new Color(8, 14, 21);
    private static final Color HEADER_BG = new Color(10, 17, 25);
    private static final Color SIDEBAR_BG = new Color(12, 20, 29);
    private static final Color CARD_BG = new Color(17, 25, 35);
    private static final Color ORANGE = new Color(255, 105, 0);
    private static final Color WHITE = new Color(245, 247, 250);
    private static final Color MUTED = new Color(174, 185, 200);
    private static final Color BORDER = new Color(43, 54, 68);
    private static final Color BLUE = new Color(28, 139, 255);
    private static final Color GREEN = new Color(14, 195, 128);
    private static final Color PURPLE = new Color(165, 69, 255);
    private static final Color RED = new Color(255, 48, 83);

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JLabel ordersValue;
    private JLabel foodValue;
    private JLabel customersValue;
    private JLabel revenueValue;
    private JLabel dateLabel;

    public AdminDashboard() {
        setTitle("TastyHub | Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);
        mainPanel.setBorder(new EmptyBorder(25, 28, 20, 28));

        contentPanel = new JPanel(new BorderLayout(0, 22));
        contentPanel.setBackground(BG);

        contentPanel.add(createWelcomeSection(), BorderLayout.NORTH);
        contentPanel.add(createDashboardContent(), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        refreshDashboardStats();
    }

    // ---------------- HEADER ----------------

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 76));
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, BORDER));

        JPanel brand = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 14, 15));
        brand.setBackground(HEADER_BG);
        brand.setBorder(new EmptyBorder(0, 20, 0, 0));

        IconView logo = new IconView("utensils", ORANGE, 27);
        brand.add(logo);

        JLabel brandName = new JLabel("TastyHub");
        brandName.setFont(new Font("SansSerif", Font.BOLD, 25));
        brandName.setForeground(WHITE);
        brand.add(brandName);

        JLabel adminBadge = new JLabel("ADMIN");
        adminBadge.setFont(new Font("SansSerif", Font.BOLD, 10));
        adminBadge.setForeground(ORANGE);
        adminBadge.setOpaque(true);
        adminBadge.setBackground(new Color(55, 31, 18));
        adminBadge.setBorder(new EmptyBorder(5, 9, 5, 9));
        brand.add(adminBadge);

        JPanel right = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 12, 23));
        right.setBackground(HEADER_BG);
        right.setBorder(new EmptyBorder(0, 0, 0, 20));

        IconView calendarIcon = new IconView("calendar", MUTED, 17);
        right.add(calendarIcon);

        dateLabel = new JLabel(getCurrentDate());
        dateLabel.setForeground(MUTED);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        right.add(dateLabel);

        header.add(brand, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("EEE, dd MMM yyyy")
                .format(new Date());
    }

    // ---------------- SIDEBAR ----------------

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1, BORDER));

        JPanel menu = new JPanel();
        menu.setBackground(SIDEBAR_BG);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(25, 13, 15, 13));

        JLabel sectionTitle = new JLabel("MAIN MENU");
        sectionTitle.setForeground(new Color(110, 125, 143));
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        sectionTitle.setBorder(new EmptyBorder(0, 13, 16, 0));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(sectionTitle);

        menu.add(createNavButton(
                "Dashboard", "layout-dashboard", true,
                this::showDashboard));

        menu.add(Box.createVerticalStrut(7));

        menu.add(createNavButton(
                "Manage Orders", "clipboard-list", false,
                () -> openManagement(new OrderManagement())));

        menu.add(Box.createVerticalStrut(7));

        menu.add(createNavButton(
                "Food Management", "utensils", false,
                () -> openManagement(new FoodManagement())));

        menu.add(Box.createVerticalStrut(7));

        menu.add(createNavButton(
                "Payments", "wallet", false,
                () -> openManagement(new PaymentManagement())));

        menu.add(Box.createVerticalStrut(7));

        menu.add(createNavButton(
                "Sales Reports", "chart", false,
                () -> openManagement(new SalesReport())));

        menu.add(Box.createVerticalStrut(7));

        menu.add(createNavButton(
                "Customers", "users", false,
                this::showCustomers));

        sidebar.add(menu, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setBackground(SIDEBAR_BG);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(12, 13, 20, 13));

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        bottom.add(separator);
        bottom.add(Box.createVerticalStrut(15));

        JButton logout = createNavButton(
                "Exit Dashboard", "log-out", false, this::logout);
        logout.setForeground(new Color(255, 112, 125));
        bottom.add(logout);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createNavButton(
            String text, String iconName,
            boolean selected, Runnable action) {

        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(selected ? ORANGE : MUTED);
        button.setBackground(selected
                ? new Color(47, 30, 20) : SIDEBAR_BG);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIcon(new IconView(
                iconName, selected ? ORANGE : MUTED, 19));
        button.setIconTextGap(14);
        button.setBorder(new EmptyBorder(13, 13, 13, 10));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        button.addActionListener(e -> action.run());

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    button.setBackground(new Color(23, 33, 45));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    button.setBackground(SIDEBAR_BG);
                }
            }
        });

        return button;
    }

    // ---------------- WELCOME SECTION ----------------

    private JPanel createWelcomeSection() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG);

        JLabel title = new JLabel("Admin Dashboard");
        title.setForeground(WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Manage your food delivery operations from one place.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textPanel = new JPanel();
        textPanel.setBackground(BG);
        textPanel.setLayout(new BoxLayout(
                textPanel, BoxLayout.Y_AXIS));

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(7));
        textPanel.add(subtitle);

        panel.add(textPanel, BorderLayout.WEST);

        return panel;
    }

    // ---------------- DASHBOARD CONTENT ----------------

    private JPanel createDashboardContent() {
        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel stats = new JPanel(new GridLayout(1, 4, 15, 0));
        stats.setBackground(BG);
        stats.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 135));
        stats.setPreferredSize(new Dimension(800, 135));

        ordersValue = new JLabel("...");
        foodValue = new JLabel("...");
        customersValue = new JLabel("...");
        revenueValue = new JLabel("...");

        stats.add(createStatCard(
                "Total Orders", ordersValue,
                "clipboard-list", ORANGE));

        stats.add(createStatCard(
                "Food Items", foodValue,
                "utensils", BLUE));

        stats.add(createStatCard(
                "Customers", customersValue,
                "users", PURPLE));

        stats.add(createStatCard(
                "Paid Revenue", revenueValue,
                "wallet", GREEN));

        panel.add(stats);
        panel.add(Box.createVerticalStrut(27));

        JLabel quickTitle = new JLabel("Quick Management");
        quickTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        quickTitle.setForeground(WHITE);
        quickTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(quickTitle);
        panel.add(Box.createVerticalStrut(14));

        JPanel cards = new JPanel(new GridLayout(2, 2, 15, 15));
        cards.setBackground(BG);
        cards.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 250));

        cards.add(createManagementCard(
                "Manage Orders",
                "View customer orders and update order status.",
                "clipboard-list", ORANGE,
                () -> openManagement(new OrderManagement())));

        cards.add(createManagementCard(
                "Food Management",
                "Add, edit and manage available food items.",
                "utensils", BLUE,
                () -> openManagement(new FoodManagement())));

        cards.add(createManagementCard(
                "Payments",
                "Record payments and confirm received funds.",
                "wallet", GREEN,
                () -> openManagement(new PaymentManagement())));

        cards.add(createManagementCard(
                "Sales Reports",
                "Review revenue and sales information.",
                "chart", PURPLE,
                () -> openManagement(new SalesReport())));

        panel.add(cards);
        panel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "TastyHub Food Delivery System  |  Administration");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(new Color(105, 119, 136));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalStrut(18));
        panel.add(footer);

        return panel;
    }

    private JPanel createStatCard(
            String title, JLabel value,
            String iconName, Color accent) {

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(15, 16, 15, 16)));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(MUTED);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel iconBox = new JPanel(new GridBagLayout());
        iconBox.setBackground(new Color(
                accent.getRed(), accent.getGreen(),
                accent.getBlue(), 30));
        iconBox.setPreferredSize(new Dimension(37, 37));
        iconBox.add(new IconView(iconName, accent, 20));

        top.add(titleLabel, BorderLayout.WEST);
        top.add(iconBox, BorderLayout.EAST);

        value.setFont(new Font("SansSerif", Font.BOLD, 25));
        value.setForeground(WHITE);

        card.add(top, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private JPanel createManagementCard(
            String title, String description,
            String iconName, Color accent, Runnable action) {

        JPanel card = new JPanel(new BorderLayout(14, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(17, 17, 17, 17)));

        JPanel iconBox = new JPanel(new GridBagLayout());
        iconBox.setBackground(new Color(
                accent.getRed(), accent.getGreen(),
                accent.getBlue(), 30));
        iconBox.setPreferredSize(new Dimension(52, 52));
        iconBox.add(new IconView(iconName, accent, 25));

        JPanel details = new JPanel();
        details.setBackground(CARD_BG);
        details.setLayout(new BoxLayout(
                details, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
                "<html><div style='width:210px'>"
                        + description + "</div></html>");
        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 11));
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel openLabel = new JLabel("Open section  →");
        openLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        openLabel.setForeground(accent);
        openLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(titleLabel);
        details.add(Box.createVerticalStrut(7));
        details.add(descriptionLabel);
        details.add(Box.createVerticalStrut(10));
        details.add(openLabel);

        card.add(iconBox, BorderLayout.WEST);
        card.add(details, BorderLayout.CENTER);

        card.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(23, 34, 47));
                details.setBackground(new Color(23, 34, 47));
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
                details.setBackground(CARD_BG);
                card.repaint();
            }
        };

        card.addMouseListener(clickListener);
        iconBox.addMouseListener(clickListener);
        details.addMouseListener(clickListener);
        titleLabel.addMouseListener(clickListener);
        descriptionLabel.addMouseListener(clickListener);
        openLabel.addMouseListener(clickListener);

        return card;
    }

    // ---------------- DATABASE STATISTICS ----------------

    private void refreshDashboardStats() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                setStatsUnavailable();
                return;
            }

            ordersValue.setText(String.valueOf(
                    getCount(con, "SELECT COUNT(*) FROM `order`")));

            foodValue.setText(String.valueOf(
                    getCount(con, "SELECT COUNT(*) FROM food_items")));

            customersValue.setText(String.valueOf(
                    getCount(con, "SELECT COUNT(*) FROM customers")));

            revenueValue.setText(
                    "UGX " + String.format("%,.0f",
                            getPaidRevenue(con)));

        } catch (SQLException ex) {
            setStatsUnavailable();
            System.err.println(
                    "Could not load dashboard statistics: "
                            + ex.getMessage());
        }
    }

    private int getCount(Connection con, String sql)
            throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private double getPaidRevenue(Connection con)
            throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(total_amount), 0) " +
                "FROM `order` " +
                "WHERE LOWER(TRIM(payment_status)) = 'paid'";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            // Some database versions may not yet have these columns.
            System.err.println(
                    "Paid revenue could not be loaded. Check that "
                    + "the `order` table has total_amount and "
                    + "payment_status columns.");
        }

        return 0;
    }

    private void setStatsUnavailable() {
        if (ordersValue != null) ordersValue.setText("—");
        if (foodValue != null) foodValue.setText("—");
        if (customersValue != null) customersValue.setText("—");
        if (revenueValue != null) revenueValue.setText("—");
    }

    // ---------------- NAVIGATION ----------------

    private void openManagement(JFrame window) {
        if (window == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "This management screen could not be opened.",
                    "TastyHub",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        window.setLocationRelativeTo(this);
        window.setVisible(true);
    }

    private void showDashboard() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout(0, 22));
        contentPanel.add(createWelcomeSection(), BorderLayout.NORTH);
        contentPanel.add(createDashboardContent(), BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();

        refreshDashboardStats();
    }

    private void showCustomers() {
        List<Customer> customers;

        try {
            CustomerDAO dao = new CustomerDAO();
            customers = dao.getAllCustomers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load customers.\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columns = {
            "Customer Name", "Phone", "Delivery Area", "Address"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {
                    @Override
                    public boolean isCellEditable(
                            int row, int column) {
                        return false;
                    }
                };

        for (Customer customer : customers) {
            model.addRow(new Object[]{
                customer.getName(),
                customer.getPhoneNo(),
                customer.getDeliveryArea(),
                customer.getAddress()
            });
        }

        JTable table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel customerPanel = new JPanel(new BorderLayout(0, 15));
        customerPanel.setBackground(BG);

        JLabel title = new JLabel("Customer Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "Registered customers and their delivery information");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        JPanel headings = new JPanel();
        headings.setBackground(BG);
        headings.setLayout(new BoxLayout(
                headings, BoxLayout.Y_AXIS));
        headings.add(title);
        headings.add(Box.createVerticalStrut(6));
        headings.add(subtitle);

        JButton backButton = new JButton("← Dashboard");
        backButton.setForeground(WHITE);
        backButton.setBackground(CARD_BG);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> showDashboard());

        customerPanel.add(headings, BorderLayout.NORTH);
        customerPanel.add(scrollPane, BorderLayout.CENTER);
        customerPanel.add(backButton, BorderLayout.SOUTH);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(customerPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void styleTable(JTable table) {
        table.setBackground(CARD_BG);
        table.setForeground(WHITE);
        table.setGridColor(BORDER);
        table.setRowHeight(32);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(66, 47, 34));
        table.setSelectionForeground(WHITE);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(HEADER_BG);
        header.setForeground(ORANGE);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 36));
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the admin dashboard?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    // ---------------- CUSTOM DRAWN ICONS ----------------

    private static class IconView extends JComponent
            implements Icon {

        private final String type;
        private final Color color;
        private final int iconSize;

        IconView(String type, Color color, int iconSize) {
            this.type = type;
            this.color = color;
            this.iconSize = iconSize;

            setPreferredSize(new Dimension(iconSize, iconSize));
            setMinimumSize(new Dimension(iconSize, iconSize));
            setOpaque(false);
        }

        @Override
        public int getIconWidth() {
            return iconSize;
        }

        @Override
        public int getIconHeight() {
            return iconSize;
        }

        @Override
        public void paintIcon(
                Component component, Graphics graphics,
                int x, int y) {

            Graphics2D g = (Graphics2D) graphics.create();

            g.translate(x, y);
            g.setColor(color);
            g.setStroke(new BasicStroke(
                    1.8f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            int s = iconSize;
            int p = Math.max(2, s / 8);
            int w = s - 2 * p;
            int h = s - 2 * p;

            switch (type) {
                case "layout-dashboard":
                    g.drawRoundRect(p, p, w, h, 3, 3);
                    g.drawLine(p, p + h / 2, p + w, p + h / 2);
                    g.drawLine(p + w / 2, p, p + w / 2, p + h);
                    break;

                case "clipboard-list":
                    g.drawRoundRect(p + 2, p + 2, w - 4, h - 2, 3, 3);
                    g.drawRoundRect(
                            p + w / 3, p, w / 3, Math.max(4, s / 5), 3, 3);
                    g.drawLine(p + 5, p + h / 2, p + w - 5, p + h / 2);
                    g.drawLine(p + 5, p + h * 2 / 3,
                            p + w - 5, p + h * 2 / 3);
                    break;

                case "utensils":
                    g.drawLine(p + 3, p, p + 3, p + h / 2);
                    g.drawLine(p, p, p, p + h / 4);
                    g.drawLine(p + 6, p, p + 6, p + h / 4);
                    g.drawLine(p, p + h / 4, p + 6, p + h / 4);
                    g.drawLine(p + 3, p + h / 2,
                            p + 3, p + h);
                    g.drawArc(p + w / 2, p, w / 2, h / 2, -90, 180);
                    g.drawLine(p + w * 3 / 4, p + h / 2,
                            p + w * 3 / 4, p + h);
                    break;

                case "wallet":
                    g.drawRoundRect(p, p + 3, w, h - 5, 4, 4);
                    g.drawRoundRect(
                            p + w / 2, p + h / 3, w / 2, h / 3, 3, 3);
                    g.fillOval(p + w * 2 / 3, p + h / 2 - 1, 3, 3);
                    break;

                case "chart":
                    g.drawLine(p, p + h, p, p);
                    g.drawLine(p, p + h, p + w, p + h);
                    g.drawLine(p + 3, p + h * 3 / 4,
                            p + w / 3, p + h / 2);
                    g.drawLine(p + w / 3, p + h / 2,
                            p + w * 2 / 3, p + h * 2 / 3);
                    g.drawLine(p + w * 2 / 3, p + h * 2 / 3,
                            p + w, p + h / 5);
                    break;

                case "users":
                    g.drawOval(p + w / 3, p, w / 3, h / 3);
                    g.drawArc(p + 1, p + h / 3, w / 2, h / 2,
                            0, 180);
                    g.drawOval(p + w / 2, p + 2, w / 3, h / 3);
                    g.drawArc(p + w / 2, p + h / 3, w / 2, h / 2,
                            0, 180);
                    break;

                case "calendar":
                    g.drawRoundRect(p, p + 3, w, h - 3, 3, 3);
                    g.drawLine(p, p + h / 3, p + w, p + h / 3);
                    g.drawLine(p + w / 4, p, p + w / 4, p + 5);
                    g.drawLine(p + w * 3 / 4, p,
                            p + w * 3 / 4, p + 5);
                    break;

                case "log-out":
                    g.drawRoundRect(p, p, w / 2, h, 3, 3);
                    g.drawLine(p + w / 3, p + h / 2,
                            p + w, p + h / 2);
                    g.drawLine(p + w * 2 / 3, p + h / 3,
                            p + w, p + h / 2);
                    g.drawLine(p + w * 2 / 3, p + h * 2 / 3,
                            p + w, p + h / 2);
                    break;

                default:
                    g.drawOval(p, p, w, h);
                    break;
            }

            g.dispose();
        }

        @Override
        public void paintComponent(Graphics g) {
            paintIcon(this, g, 0, 0);
        }
    }

    // ---------------- MAIN METHOD ----------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
}
