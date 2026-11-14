package com.myproject.tastyhub;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginAdmin extends JFrame {

    // Demo credentials for the school project
    private static final String ADMIN_USERNAME = System.getenv().getOrDefault("TASTYHUB_ADMIN_USER", "admin");
    private static final String ADMIN_PASSWORD = System.getenv().getOrDefault("TASTYHUB_ADMIN_PASSWORD", "TastyHub123");

    // TastyHub theme colors
    private static final Color ORANGE = new Color(232, 93, 4);
    private static final Color CREAM = new Color(255, 247, 237);
    private static final Color CHARCOAL = new Color(37, 37, 37);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(225, 210, 195);
    private static final Color MUTED = new Color(115, 100, 90);

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginAdmin() {
        setTitle("TastyHub | Administrator Login");
        setSize(500, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM);

        // ================= HEADER =================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );
        headerPanel.setBackground(CHARCOAL);
        headerPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        JLabel title = new JLabel("TastyHub");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("ADMINISTRATOR PORTAL");
        subtitle.setFont(new Font("Arial", Font.BOLD, 12));
        subtitle.setForeground(new Color(255, 160, 90));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(7));
        headerPanel.add(subtitle);

        // ================= LOGIN FORM =================

        JPanel formPanel = new JPanel();
        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );
        formPanel.setBackground(CREAM);
        formPanel.setBorder(
                new EmptyBorder(25, 45, 25, 45)
        );

        JLabel welcomeLabel = new JLabel("Welcome back");
        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );
        welcomeLabel.setForeground(CHARCOAL);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel =
                new JLabel("Sign in to manage TastyHub.");
        instructionLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        instructionLabel.setForeground(MUTED);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username");
        styleLabel(usernameLabel);

        usernameField = new JTextField();
        styleTextField(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        styleLabel(passwordLabel);

        passwordField = new JPasswordField();
        styleTextField(passwordField);

        // ================= LOGIN BUTTON =================

        JButton loginButton = new JButton("SIGN IN");
        loginButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        loginButton.setBackground(ORANGE);
        loginButton.setForeground(WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 43)
        );
        loginButton.setPreferredSize(
                new Dimension(300, 43)
        );

        // Simple hover effect
        loginButton.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {
                        loginButton.setBackground(
                                new Color(200, 75, 0)
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {
                        loginButton.setBackground(ORANGE);
                    }
                }
        );

        // ================= CANCEL BUTTON =================

        JButton backButton = new JButton("CANCEL");
        backButton.setFont(
                new Font("Arial", Font.BOLD, 12)
        );
        backButton.setForeground(CHARCOAL);
        backButton.setBackground(CREAM);
        backButton.setFocusPainted(false);
        backButton.setBorder(
                new LineBorder(BORDER, 1, true)
        );
        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 38)
        );

        // ================= FORM LAYOUT =================

        formPanel.add(welcomeLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(instructionLabel);
        formPanel.add(Box.createVerticalStrut(22));

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(7));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(16));

        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(7));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(22));

        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backButton);

        // ================= FOOTER =================

        JLabel footerLabel = new JLabel(
                "TastyHub Food Delivery System"
        );
        footerLabel.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );
        footerLabel.setForeground(MUTED);
        footerLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );
        footerLabel.setBorder(
                new EmptyBorder(0, 0, 12, 0)
        );

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(CREAM);
        footerPanel.add(
                footerLabel,
                BorderLayout.CENTER
        );

        // ================= ACTIONS =================

        loginButton.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());
        usernameField.addActionListener(e ->
                passwordField.requestFocusInWindow()
        );
        backButton.addActionListener(e -> dispose());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void styleLabel(JLabel label) {
        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );
        label.setForeground(CHARCOAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleTextField(JTextField field) {
        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        field.setForeground(CHARCOAL);
        field.setBackground(WHITE);
        field.setCaretColor(ORANGE);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );
        field.setPreferredSize(
                new Dimension(300, 40)
        );
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password =
                new String(passwordField.getPassword());

        if (ADMIN_USERNAME.equals(username)
                && ADMIN_PASSWORD.equals(password)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful. Welcome, Administrator!",
                    "Access Granted",
                    JOptionPane.INFORMATION_MESSAGE
            );

            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setLocationRelativeTo(null);
            dashboard.setVisible(true);

            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username or password. Please try again.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
            passwordField.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginAdmin login = new LoginAdmin();
            login.setVisible(true);
        });
    }
}