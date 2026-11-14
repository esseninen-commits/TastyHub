
package com.myproject.tastyhub;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class FoodManagement extends JFrame {

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

    private static final Color RED =
            new Color(231, 104, 104);

    private static final Color BLUE =
            new Color(105, 174, 235);

    private JTable foodTable;
    private DefaultTableModel model;

    public FoodManagement() {
        setTitle("TastyHub | Food Management");
        setSize(1050, 680);
        setMinimumSize(new Dimension(850, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        initializeComponents();
        loadFoodItems();
    }

    // ---------------- MAIN INTERFACE ----------------

    private void initializeComponents() {

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);

        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, BORDER),
                new EmptyBorder(22, 26, 22, 26)
        ));

        JPanel headingText = new JPanel(
                new GridLayout(2, 1, 0, 6));
        headingText.setOpaque(false);

        JLabel heading = new JLabel("Food Management");
        heading.setFont(
                new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(IVORY);

        JLabel subtitle = new JLabel(
                "Manage your menu, prices and food availability");
        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        headingText.add(heading);
        headingText.add(subtitle);

        JLabel sectionBadge = new JLabel("MENU CONTROL");
        sectionBadge.setFont(
                new Font("SansSerif", Font.BOLD, 10));
        sectionBadge.setForeground(GOLD);
        sectionBadge.setOpaque(true);
        sectionBadge.setBackground(CARD_BG);
        sectionBadge.setBorder(
                new EmptyBorder(9, 12, 9, 12));

        header.add(headingText, BorderLayout.WEST);
        header.add(sectionBadge, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // CONTENT AREA
        JPanel content = new JPanel(
                new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(
                new EmptyBorder(23, 26, 20, 26));

        // TABLE HEADING
        JPanel tableHeading = new JPanel(
                new BorderLayout());
        tableHeading.setBackground(BACKGROUND);

        JPanel tableHeadingText = new JPanel(
                new GridLayout(2, 1, 0, 5));
        tableHeadingText.setOpaque(false);

        JLabel tableTitle = new JLabel("Food Menu");
        tableTitle.setFont(
                new Font("SansSerif", Font.BOLD, 19));
        tableTitle.setForeground(IVORY);

        JLabel tableSubtitle = new JLabel(
                "Add new items or update existing menu information");
        tableSubtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 12));
        tableSubtitle.setForeground(MUTED);

        tableHeadingText.add(tableTitle);
        tableHeadingText.add(tableSubtitle);

        tableHeading.add(
                tableHeadingText, BorderLayout.WEST);

        // FOOD TABLE
        model = new DefaultTableModel(
                new String[]{
                    "Food ID",
                    "Food Name",
                    "Price (UGX)",
                    "Category",
                    "Availability"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(
                    int row, int column) {
                return false;
            }
        };

        foodTable = new JTable(model);
        styleTable();

        JScrollPane scrollPane =
                new JScrollPane(foodTable);

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
        JButton addButton = createButton(
                "＋  Add Food", GOLD);

        JButton editButton = createButton(
                "Edit Food", BLUE);

        JButton deleteButton = createButton(
                "Delete Food", RED);

        JButton refreshButton = createButton(
                "Refresh", CARD_BG);

        JButton closeButton = createButton(
                "Close", CARD_BG);

        addButton.addActionListener(e -> addFood());
        editButton.addActionListener(e -> editFood());
        deleteButton.addActionListener(e -> deleteFood());
        refreshButton.addActionListener(e -> loadFoodItems());
        closeButton.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(BACKGROUND);

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(refreshButton);
        buttons.add(closeButton);

        content.add(buttons, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    // ---------------- TABLE DESIGN ----------------

    private void styleTable() {
        foodTable.setRowHeight(39);
        foodTable.setFont(
                new Font("SansSerif", Font.PLAIN, 13));
        foodTable.setForeground(IVORY);
        foodTable.setBackground(CARD_BG);
        foodTable.setGridColor(BORDER);
        foodTable.setShowVerticalLines(false);
        foodTable.setShowHorizontalLines(true);
        foodTable.setSelectionBackground(
                new Color(69, 61, 48));
        foodTable.setSelectionForeground(IVORY);
        foodTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        foodTable.setAutoCreateRowSorter(true);
        foodTable.setFillsViewportHeight(true);
        foodTable.setIntercellSpacing(
                new Dimension(0, 1));

        JTableHeader tableHeader =
                foodTable.getTableHeader();

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
                    setForeground(IVORY);
                }

                return this;
            }
        };

        foodTable.setDefaultRenderer(
                Object.class, renderer);

        foodTable.getColumnModel()
                .getColumn(0).setPreferredWidth(75);

        foodTable.getColumnModel()
                .getColumn(1).setPreferredWidth(250);

        foodTable.getColumnModel()
                .getColumn(2).setPreferredWidth(160);

        foodTable.getColumnModel()
                .getColumn(3).setPreferredWidth(150);

        foodTable.getColumnModel()
                .getColumn(4).setPreferredWidth(130);
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
                        11, 14, 11, 14)
        ));

        button.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));

        button.setMargin(new Insets(8, 12, 8, 12));

        Color hoverColor = baseColor.equals(GOLD)
                ? new Color(255, 202, 125)
                : baseColor.equals(RED)
                    ? new Color(245, 128, 128)
                    : baseColor.equals(CARD_BG)
                        ? SIDEBAR_BG
                        : baseColor.darker();

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

    // ---------------- LOAD FOOD ITEMS ----------------

    private void loadFoodItems() {
        model.setRowCount(0);

        String sql =
                "SELECT Food_id, name, price, category, available "
                + "FROM food_items ORDER BY Food_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("Food_id"),
                    rs.getString("name"),
                    formatPrice(rs.getDouble("price")),
                    rs.getString("category"),
                    rs.getBoolean("available")
                            ? "Available" : "Unavailable"
                });
            }

        } catch (SQLException ex) {
            showError(
                    "Could not load food items:\n"
                    + ex.getMessage());
        }
    }

    private String formatPrice(double price) {
        return String.format(
                Locale.US, "%,.0f", price);
    }

    // ---------------- ADD FOOD ----------------

    private void addFood() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();

        String[] categories = {
            "Main Meal", "Side", "Drink"
        };

        JComboBox<String> categoryBox =
                new JComboBox<>(categories);

        JCheckBox availableBox =
                new JCheckBox("Available", true);

        styleInput(nameField);
        styleInput(priceField);
        styleInput(categoryBox);
        styleInput(availableBox);

        JPanel form = createFormPanel();

        addFormRow(form, "Food Name:", nameField);
        addFormRow(form, "Price (UGX):", priceField);
        addFormRow(form, "Category:", categoryBox);
        addFormRow(form, "Availability:", availableBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Add Food Item",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            showError("Enter both the food name and price.");
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);

            if (!Double.isFinite(price) || price <= 0) {
                showError(
                        "Enter a valid price greater than zero.");
                return;
            }

        } catch (NumberFormatException ex) {
            showError("Enter a valid numeric price.");
            return;
        }

        String sql =
                "INSERT INTO food_items "
                + "(name, price, category, available) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(
                    3, (String) categoryBox.getSelectedItem());
            ps.setBoolean(4, availableBox.isSelected());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Food item added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadFoodItems();

        } catch (SQLException ex) {
            showError(
                    "Could not add food item:\n"
                    + ex.getMessage());
        }
    }

    // ---------------- EDIT FOOD ----------------

    private void editFood() {
        int selectedRow = foodTable.getSelectedRow();

        if (selectedRow == -1) {
            showError(
                    "Select a food item from the table to edit.");
            return;
        }

        int row =
                foodTable.convertRowIndexToModel(selectedRow);

        int foodId = ((Number)
                model.getValueAt(row, 0)).intValue();

        String oldName =
                String.valueOf(model.getValueAt(row, 1));

        String oldPrice =
                String.valueOf(model.getValueAt(row, 2))
                        .replace(",", "");

        String oldCategory =
                String.valueOf(model.getValueAt(row, 3));

        boolean oldAvailable =
                "Available".equalsIgnoreCase(
                        String.valueOf(model.getValueAt(row, 4)));

        JTextField nameField = new JTextField(oldName);
        JTextField priceField = new JTextField(oldPrice);

        String[] categories = {
            "Main Meal", "Side", "Drink"
        };

        JComboBox<String> categoryBox =
                new JComboBox<>(categories);

        categoryBox.setSelectedItem(oldCategory);

        JCheckBox availableBox =
                new JCheckBox("Available", oldAvailable);

        styleInput(nameField);
        styleInput(priceField);
        styleInput(categoryBox);
        styleInput(availableBox);

        JPanel form = createFormPanel();

        addFormRow(form, "Food Name:", nameField);
        addFormRow(form, "Price (UGX):", priceField);
        addFormRow(form, "Category:", categoryBox);
        addFormRow(form, "Availability:", availableBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Edit Food Item #" + foodId,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            showError("Food name and price are required.");
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);

            if (!Double.isFinite(price) || price <= 0) {
                showError(
                        "Enter a valid price greater than zero.");
                return;
            }

        } catch (NumberFormatException ex) {
            showError("Enter a valid numeric price.");
            return;
        }

        String sql =
                "UPDATE food_items "
                + "SET name = ?, price = ?, category = ?, "
                + "available = ? WHERE Food_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(
                    3, (String) categoryBox.getSelectedItem());
            ps.setBoolean(4, availableBox.isSelected());
            ps.setInt(5, foodId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Food item updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showError("The food item was not found.");
            }

            loadFoodItems();

        } catch (SQLException ex) {
            showError(
                    "Could not update food item:\n"
                    + ex.getMessage());
        }
    }

    // ---------------- DELETE FOOD ----------------

    private void deleteFood() {
        int selectedRow = foodTable.getSelectedRow();

        if (selectedRow == -1) {
            showError(
                    "Select a food item from the table to delete.");
            return;
        }

        int row =
                foodTable.convertRowIndexToModel(selectedRow);

        int foodId = ((Number)
                model.getValueAt(row, 0)).intValue();

        String foodName =
                String.valueOf(model.getValueAt(row, 1));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + foodName + "\" from the menu?\n\n"
                + "This may be blocked if the item is referenced "
                + "by existing orders.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM food_items WHERE Food_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {
            ps.setInt(1, foodId);

            int deleted = ps.executeUpdate();

            if (deleted > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Food item deleted successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showError("The food item was not found.");
            }

            loadFoodItems();

        } catch (SQLException ex) {
            showError(
                    "Could not delete this food item.\n"
                    + "It may be referenced by existing orders.\n\n"
                    + ex.getMessage());
        }
    }

    // ---------------- FORM DESIGN ----------------

    private JPanel createFormPanel() {
        JPanel form = new JPanel(
                new GridLayout(0, 2, 12, 14));

        form.setBackground(BACKGROUND);
        form.setBorder(
                new EmptyBorder(14, 14, 14, 14));

        return form;
    }

    private void addFormRow(
            JPanel form,
            String labelText,
            Component input) {

        JLabel label = new JLabel(labelText);
        label.setFont(
                new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(IVORY);

        form.add(label);
        form.add(input);
    }

    private void styleInput(JComponent input) {
        input.setFont(
                new Font("SansSerif", Font.PLAIN, 13));

        input.setForeground(IVORY);
        input.setBackground(CARD_BG);

        if (input instanceof JCheckBox) {
            input.setOpaque(true);
            input.setBorder(
                    BorderFactory.createEmptyBorder(
                            5, 5, 5, 5));
        }

        if (input instanceof JTextField) {
            JTextField textField = (JTextField) input;

            textField.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER),
                            BorderFactory.createEmptyBorder(
                                    8, 9, 8, 9)
                    )
            );

            // Correctly set the text cursor colour.
            textField.setCaretColor(GOLD);
        }
    }

    // ---------------- ERROR MESSAGES ----------------

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Food Management",
                JOptionPane.ERROR_MESSAGE
        );
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

            new FoodManagement().setVisible(true);
        });
    }
}
