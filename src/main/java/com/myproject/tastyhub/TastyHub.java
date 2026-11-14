package com.myproject.tastyhub;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TastyHub {

    // ================= COLOUR THEME =================

    private static final Color BACKGROUND = new Color(16, 24, 32);
    private static final Color SIDEBAR_BG = new Color(23, 35, 47);
    private static final Color CARD_BG = new Color(32, 45, 57);
    private static final Color GOLD = new Color(244, 184, 96);
    private static final Color IVORY = new Color(245, 243, 238);
    private static final Color MUTED = new Color(170, 183, 196);
    private static final Color BORDER = new Color(52, 68, 83);
    private static final Color GREEN = new Color(89, 190, 139);
    private static final Color BLUE = new Color(105, 174, 235);
    private static final Color RED = new Color(231, 104, 104);
    private static final Color WHITE = Color.WHITE;

    private static final int DELIVERY_FEE = 3000;

    
private static final String[] FOOD_NAMES = {
    "Chicken and Chips",
    "Beef Burger",
    "Pizza",
    "Chips",
    "Soda",
    "Grilled Chicken",
    "Beef Wrap",
    "Chicken Burger",
    "Beef Pilau",
    "Chicken Wings",
    "Sausage and Chips",
    "Samosas",
    "Fresh Juice",
    "Bottled Water",
    "Milkshake"
};

private static final String[] IMAGE_FILES = {
    "chicken and chips.jpg",
    "burger.jpg",
    "pizza.jpg",
    "chips.jpg",
    "soda.jpg",
    "grilled chicken.jpg",
    "beef wrap.jpg",
    "chicken burger.jpg",
    "beef pilau.jpg",
    "chicken wings.jpg",
    "sausage and chips.jpg",
    "samosas.jpg",
    "fresh juice.jpg",
    "bottled water.jpg",
    "milkshake.jpg"
};

private static final int[] PRICES = {
    15000,
    12000,
    20000,
    5000,
    2000,
    18000,
    10000,
    14000,
    12000,
    15000,
    8000,
    4000,
    5000,
    1500,
    7000
};

private static final String[] CATEGORIES = {
    "Main Meal",
    "Main Meal",
    "Main Meal",
    "Side",
    "Drink",
    "Main Meal",
    "Main Meal",
    "Main Meal",
    "Main Meal",
    "Main Meal",
    "Side",
    "Side",
    "Drink",
    "Drink",
    "Drink"
};

    private static final LinkedHashMap<String, Integer> cart =
            new LinkedHashMap<>();

    private static JFrame menuFrame;
    private static JLabel cartButtonLabel;

    // ================= COMMON HELPERS =================

    private static JLabel label(String text, int size, Color color,
                                boolean bold) {
        JLabel result = new JLabel(text);
        result.setFont(new Font("Arial",
                bold ? Font.BOLD : Font.PLAIN, size));
        result.setForeground(color);
        return result;
    }

    private static JButton createButton(String text) {
        return styledButton(text, true);
    }

    private static JButton styledButton(String text, boolean primary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(210, 42));
        button.setMinimumSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(250, 44));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setOpaque(true);

        if (primary) {
            button.setBackground(GOLD);
            button.setForeground(BACKGROUND);
            button.setBorder(BorderFactory.createEmptyBorder(
                    10, 16, 10, 16));
        } else {
            button.setBackground(CARD_BG);
            button.setForeground(IVORY);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(9, 15, 9, 15)));
        }

        return button;
    }

    private static JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    private static void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(CARD_BG);
        field.setForeground(IVORY);
        field.setCaretColor(GOLD);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private static String money(int amount) {
        return String.format("%,d", amount);
    }

    // ================= WELCOME SCREEN =================

   
private static void showWelcomeScreen() {
    JFrame frame = new JFrame("TastyHub | Food Delivery");
    frame.setSize(1100, 700);
    frame.setMinimumSize(new Dimension(850, 600));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // Full-window background image
    WelcomeBackgroundPanel backgroundPanel =
            new WelcomeBackgroundPanel();
    backgroundPanel.setLayout(new BorderLayout());
    frame.setContentPane(backgroundPanel);

    // ================= HEADER =================
    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);
    header.setBorder(BorderFactory.createEmptyBorder(
            20, 35, 20, 35));

    JPanel brand = new JPanel();
    brand.setOpaque(false);
    brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

    JLabel title = label("TastyHub", 36, GOLD, true);
    title.setFont(new Font("Serif", Font.BOLD, 36));

    JLabel tagline = label(
            "A TASTE WORTH REMEMBERING", 10, IVORY, true);

    brand.add(title);
    brand.add(Box.createVerticalStrut(3));
    brand.add(tagline);

    JLabel headerRight = label(
            "FOOD  |  QUALITY  |  COMFORT", 11, IVORY, true);

    header.add(brand, BorderLayout.WEST);
    header.add(headerRight, BorderLayout.EAST);

   // ================= MAIN CONTENT =================
JPanel content = new JPanel(new GridBagLayout());
content.setOpaque(false);
content.setBorder(BorderFactory.createEmptyBorder(
        25, 60, 25, 60));

GridBagConstraints contentGbc = new GridBagConstraints();
contentGbc.gridx = 0;
contentGbc.gridy = 0;
contentGbc.weightx = 1.0;
contentGbc.weighty = 1.0;
contentGbc.fill = GridBagConstraints.NONE;
contentGbc.anchor = GridBagConstraints.WEST;

    // LEFT: WELCOME TEXT AND BUTTONS
    JPanel textPanel = new JPanel();
    textPanel.setOpaque(false);
    textPanel.setLayout(new BoxLayout(
            textPanel, BoxLayout.Y_AXIS));

    JLabel eyebrow = label(
            "WELCOME TO THE TABLE", 12, GOLD, true);
    eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel welcome = new JLabel(
            "<html>Good food.<br>Beautiful moments.</html>");
    welcome.setFont(new Font("Serif", Font.BOLD, 42));
    welcome.setForeground(IVORY);
    welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel description = new JLabel(
            "<html><div style='width:340px;'>"
            + "Discover delicious meals prepared to make "
            + "your day a little more special. "
            + "Your next favourite meal is just a few clicks away."
            + "</div></html>");
    description.setFont(new Font("Arial", Font.PLAIN, 15));
    description.setForeground(IVORY);
    description.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel note = label(
            "FRESH FLAVOURS  •  GREAT VALUE  •  EASY ORDERING",
            10, GOLD, true);
    note.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton menuButton = styledButton(
            "EXPLORE OUR MENU", true);
    menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    menuButton.addActionListener(e -> showMenuScreen());

    // WhatsApp support
    JButton whatsappButton = styledButton(
            "WHATSAPP SUPPORT", false);
    whatsappButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    whatsappButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    URL whatsappIconURL =
            TastyHub.class.getResource("/images/whatsapp.png.jpg");

    if (whatsappIconURL != null) {
        ImageIcon whatsappIcon = new ImageIcon(whatsappIconURL);
        Image resizedIcon = whatsappIcon.getImage()
                .getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        whatsappButton.setIcon(new ImageIcon(resizedIcon));
        whatsappButton.setIconTextGap(10);
    }

    whatsappButton.addActionListener(e -> {
        try {
            String message =
                    "Hello TastyHub Support,\n\n"
                    + "I would like to report a complaint.\n\n"
                    + "ORDER DETAILS\n"
                    + "Order Number: \n"
                    + "Customer Name: \n"
                    + "Phone Number: \n\n"
                    + "COMPLAINT DETAILS\n"
                    + "Complaint Type: Delivery delay / Missing item / "
                    + "Wrong order / Other\n"
                    + "Description of Complaint: \n\n"
                    + "Please assist me. Thank you.";

            String url = "https://wa.me/256792455768?text="
                    + java.net.URLEncoder.encode(
                            message,
                            java.nio.charset.StandardCharsets.UTF_8);

            Desktop.getDesktop().browse(
                    java.net.URI.create(url));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Could not open WhatsApp. Please try again.",
                    "WhatsApp Support",
                    JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton exitButton = styledButton(
            "EXIT APPLICATION", false);
    exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    exitButton.addActionListener(e -> {
        int answer = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit TastyHub?",
                "Exit TastyHub",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    });

    textPanel.add(Box.createVerticalGlue());
    textPanel.add(eyebrow);
    textPanel.add(Box.createVerticalStrut(16));
    textPanel.add(welcome);
    textPanel.add(Box.createVerticalStrut(18));
    textPanel.add(description);
    textPanel.add(Box.createVerticalStrut(20));
    textPanel.add(note);
    textPanel.add(Box.createVerticalStrut(28));
    textPanel.add(menuButton);
    textPanel.add(Box.createVerticalStrut(12));
    textPanel.add(whatsappButton);
    textPanel.add(Box.createVerticalStrut(8));
    textPanel.add(exitButton);
    textPanel.add(Box.createVerticalGlue());

    // LEFT COLUMN: main welcome message and actions
    contentGbc.gridx = 0;
    contentGbc.gridy = 0;
    contentGbc.weightx = 0.58;
    contentGbc.weighty = 1.0;
    contentGbc.fill = GridBagConstraints.BOTH;
    contentGbc.anchor = GridBagConstraints.WEST;
    contentGbc.insets = new Insets(0, 0, 0, 30);
    content.add(textPanel, contentGbc);

    // RIGHT COLUMN: simple feature panel (informational only)
    JPanel featurePanel = new JPanel();
    featurePanel.setLayout(new BoxLayout(featurePanel, BoxLayout.Y_AXIS));
    featurePanel.setBackground(new Color(16, 24, 32, 220));
    featurePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)));
    featurePanel.setPreferredSize(new Dimension(310, 330));
    featurePanel.setMaximumSize(new Dimension(340, 380));

    JLabel featureTitle = label("THE TASTYHUB EXPERIENCE", 13, GOLD, true);
    featureTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel featureSubtitle = label("Thoughtful food delivery, made simple.", 12, IVORY, false);
    featureSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

    featurePanel.add(featureTitle);
    featurePanel.add(Box.createVerticalStrut(8));
    featurePanel.add(featureSubtitle);
    featurePanel.add(Box.createVerticalStrut(22));
    featurePanel.add(createWelcomeFeature("01", "Freshly Prepared",
            "Explore a menu of meals and refreshments."));
    featurePanel.add(Box.createVerticalStrut(16));
    featurePanel.add(createFeatureDivider());
    featurePanel.add(Box.createVerticalStrut(16));
    featurePanel.add(createWelcomeFeature("02", "Easy Ordering",
            "Choose your favourites and add them to your cart."));
    featurePanel.add(Box.createVerticalStrut(16));
    featurePanel.add(createFeatureDivider());
    featurePanel.add(Box.createVerticalStrut(16));
    featurePanel.add(createWelcomeFeature("03", "Delivery Estimates",
            "View an estimated delivery time during checkout."));

    contentGbc.gridx = 1;
    contentGbc.gridy = 0;
    contentGbc.weightx = 0.42;
    contentGbc.weighty = 0.0;
    contentGbc.fill = GridBagConstraints.NONE;
    contentGbc.anchor = GridBagConstraints.CENTER;
    contentGbc.insets = new Insets(0, 0, 0, 0);
    content.add(featurePanel, contentGbc);
   

    // ================= FOOTER =================
    JPanel footer = new JPanel(new BorderLayout());
    footer.setOpaque(false);
    footer.setBorder(BorderFactory.createEmptyBorder(
            12, 35, 12, 35));

    JLabel footerLeft = label(
            "MADE WITH CARE, SERVED WITH FLAVOUR",
            10, IVORY, true);

    JLabel footerRight = label(
            "TASTYHUB FOOD DELIVERY", 10, GOLD, true);

    footer.add(footerLeft, BorderLayout.WEST);
    footer.add(footerRight, BorderLayout.EAST);

    // ================= SHOW WINDOW =================
    backgroundPanel.add(header, BorderLayout.NORTH);
    backgroundPanel.add(content, BorderLayout.CENTER);
    backgroundPanel.add(footer, BorderLayout.SOUTH);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}


    private static JPanel createWelcomeFeature(
            String number, String heading, String description) {
        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel numberLabel = label(number, 11, GOLD, true);
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel headingLabel = label(heading, 15, IVORY, true);
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
                "<html><div style='width:245px;'>"
                + description + "</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        item.add(numberLabel);
        item.add(Box.createVerticalStrut(4));
        item.add(headingLabel);
        item.add(Box.createVerticalStrut(4));
        item.add(descriptionLabel);
        return item;
    }

    private static JSeparator createFeatureDivider() {
        JSeparator divider = new JSeparator();
        divider.setForeground(BORDER);
        divider.setBackground(BORDER);
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        return divider;
    }

    // ================= PREMIUM WELCOME VISUAL =================

    
private static class FoodPhotoPanel extends JPanel {

    private final Image foodImage;

    FoodPhotoPanel() {
        setOpaque(false);

        java.net.URL imageURL =
                getClass().getResource("/images/burger.jpg");

        if (imageURL != null) {
            foodImage = new ImageIcon(imageURL).getImage();
        } else {
            foodImage = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        int w = getWidth();
        int h = getHeight();

        java.awt.geom.RoundRectangle2D rounded =
                new java.awt.geom.RoundRectangle2D.Double(
                        0, 0, w, h, 28, 28);

        g2.clip(rounded);

        if (foodImage != null
                && foodImage.getWidth(this) > 0
                && foodImage.getHeight(this) > 0) {

            int iw = foodImage.getWidth(this);
            int ih = foodImage.getHeight(this);

            double scale = Math.max(
                    (double) w / iw,
                    (double) h / ih);

            int dw = (int) (iw * scale);
            int dh = (int) (ih * scale);

            int dx = (w - dw) / 2;
            int dy = (h - dh) / 2;

            g2.drawImage(foodImage, dx, dy, dw, dh, this);

        } else {
            g2.setColor(CARD_BG);
            g2.fillRect(0, 0, w, h);

            g2.setColor(GOLD);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("TastyHub", 30, 50);

            g2.setColor(IVORY);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(
                    "Place Burger.jpg in /images",
                    30, 80);
        }

        // Dark overlay at the bottom for readable text
        g2.setPaint(new GradientPaint(
                0, h * 0.55f, new Color(0, 0, 0, 0),
                0, h, new Color(0, 0, 0, 220)));

        g2.fillRect(0, (int) (h * 0.55), w,
                h - (int) (h * 0.55));

        g2.setColor(GOLD);
        g2.setFont(new Font("Serif", Font.BOLD, 23));
        g2.drawString("Good food.", 24, h - 55);

        g2.setColor(IVORY);
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.drawString(
                "Made for your good moments.",
                25, h - 30);

        g2.dispose();
    }
}

    // ================= FOOD MENU =================

    private static void showMenuScreen() {
        if (menuFrame != null && menuFrame.isDisplayable()) {
            menuFrame.setVisible(true);
            menuFrame.toFront();
            return;
        }

        menuFrame = new JFrame("TastyHub | Our Food Menu");
        menuFrame.setSize(1050, 760);
        menuFrame.setMinimumSize(new Dimension(850, 650));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.getContentPane().setBackground(BACKGROUND);

        menuFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                menuFrame = null;
                cartButtonLabel = null;
            }
        });

        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(SIDEBAR_BG);
        header.setBorder(BorderFactory.createEmptyBorder(
                16, 25, 16, 25));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel brandTitle = label("TastyHub", 30, GOLD, true);
        brandTitle.setFont(new Font("Serif", Font.BOLD, 30));

        brand.add(brandTitle);
        brand.add(Box.createVerticalStrut(4));
        brand.add(label(
                "DELICIOUS FOOD, DELIVERED WITH CARE",
                10, MUTED, true));

        JPanel headerActions = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 5));
        headerActions.setOpaque(false);

        cartButtonLabel = label("Cart (0)", 14, IVORY, true);

        JButton viewCartButton = createButton("View Cart");
        viewCartButton.addActionListener(e -> showCartDialog());

        headerActions.add(cartButtonLabel);
        headerActions.add(viewCartButton);

        header.add(brand, BorderLayout.WEST);
        header.add(headerActions, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout(12, 15));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(
                20, 22, 16, 22));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel menuTitle = label("Explore Our Menu", 28, IVORY, true);
        menuTitle.setFont(new Font("Serif", Font.BOLD, 28));
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = label(
                "Fresh choices for every craving.", 13, MUTED, false);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel searchRow = new JPanel(new BorderLayout(10, 0));
        searchRow.setOpaque(false);
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        JLabel searchLabel = label("Search", 13, IVORY, true);

        JTextField searchField = new JTextField();
        searchField.setToolTipText(
                "Search by food name, e.g. Pizza");
        searchField.setPreferredSize(new Dimension(300, 38));
        styleTextField(searchField);

        searchRow.add(searchLabel, BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);

        JPanel filterRow = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0));
        filterRow.setOpaque(false);
        filterRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] categoryNames = {
            "All", "Main Meal", "Side", "Drink"
        };

        ArrayList<JToggleButton> filterButtons = new ArrayList<>();
        ButtonGroup filterGroup = new ButtonGroup();

        for (String category : categoryNames) {
            JToggleButton button = new JToggleButton(category);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setOpaque(true);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)));

            filterGroup.add(button);
            filterButtons.add(button);
            filterRow.add(button);
        }

        filterButtons.get(0).setSelected(true);

        topPanel.add(menuTitle);
        topPanel.add(Box.createVerticalStrut(4));
        topPanel.add(subtitle);
        topPanel.add(Box.createVerticalStrut(17));
        topPanel.add(searchRow);
        topPanel.add(Box.createVerticalStrut(12));
        topPanel.add(filterRow);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel foodGrid = new JPanel(new GridLayout(0, 2, 16, 16));
        foodGrid.setBackground(BACKGROUND);
        foodGrid.setBorder(BorderFactory.createEmptyBorder(
                4, 4, 8, 4));

        ArrayList<JPanel> cards = new ArrayList<>();

        JLabel noResults = label(
                "No food matches your search or category.",
                15, MUTED, false);
        noResults.setHorizontalAlignment(SwingConstants.CENTER);
        noResults.setBorder(BorderFactory.createEmptyBorder(
                22, 10, 22, 10));
        noResults.setVisible(false);

        for (int i = 0; i < FOOD_NAMES.length; i++) {
            final int index = i;

            JPanel card = new JPanel(new BorderLayout(14, 0));
            card.setBackground(CARD_BG);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(
                            12, 12, 12, 12)));

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(145, 135));
            imageLabel.setOpaque(true);
            imageLabel.setBackground(SIDEBAR_BG);

            URL imageURL = TastyHub.class.getClassLoader()
                    .getResource("images/" + IMAGE_FILES[i]);

            if (imageURL != null) {
                ImageIcon original = new ImageIcon(imageURL);
                Image resized = original.getImage().getScaledInstance(
                        145, 135, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(resized));
            } else {
                imageLabel.setText(
                        "<html><center>Image<br>unavailable</center></html>");
                imageLabel.setForeground(MUTED);
                System.out.println(
                        "Image not found: images/" + IMAGE_FILES[i]);
            }

            JPanel details = new JPanel();
            details.setOpaque(false);
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

            JLabel categoryLabel = label(
                    CATEGORIES[i].toUpperCase(), 10, GOLD, true);
            categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel nameLabel = label(
                    FOOD_NAMES[i], 16, IVORY, true);
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel priceLabel = label(
                    "UGX " + money(PRICES[i]), 16, GOLD, true);
            priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton addButton = createButton("+  Add to Cart");
            addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            addButton.setMaximumSize(new Dimension(220, 38));

            addButton.addActionListener(e -> {
                String food = FOOD_NAMES[index];
                cart.put(food, cart.getOrDefault(food, 0) + 1);
                updateCartButton();

                JOptionPane.showMessageDialog(
                        menuFrame,
                        food + " added to your cart.",
                        "Added to Cart",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            details.add(categoryLabel);
            details.add(Box.createVerticalStrut(7));
            details.add(nameLabel);
            details.add(Box.createVerticalStrut(8));
            details.add(priceLabel);
            details.add(Box.createVerticalGlue());
            details.add(addButton);

            card.add(imageLabel, BorderLayout.WEST);
            card.add(details, BorderLayout.CENTER);
            cards.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(foodGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel menuBody = new JPanel(new BorderLayout());
        menuBody.setOpaque(false);
        menuBody.add(scrollPane, BorderLayout.CENTER);
        menuBody.add(noResults, BorderLayout.SOUTH);

        Runnable applyFilters = () -> {
            String query = searchField.getText().trim().toLowerCase();
            String selectedCategory = "All";

            for (int i = 0; i < filterButtons.size(); i++) {
                if (filterButtons.get(i).isSelected()) {
                    selectedCategory = categoryNames[i];
                    break;
                }
            }

            foodGrid.removeAll();
            int matches = 0;

            for (int i = 0; i < cards.size(); i++) {
                boolean matchesSearch =
                        FOOD_NAMES[i].toLowerCase().contains(query);

                boolean matchesCategory =
                        selectedCategory.equals("All")
                        || CATEGORIES[i].equals(selectedCategory);

                if (matchesSearch && matchesCategory) {
                    foodGrid.add(cards.get(i));
                    matches++;
                }
            }

            noResults.setVisible(matches == 0);
            foodGrid.revalidate();
            foodGrid.repaint();
            menuBody.revalidate();
            menuBody.repaint();
        };

        searchField.getDocument().addDocumentListener(
                new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilters.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilters.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilters.run();
            }
        });

        for (JToggleButton button : filterButtons) {
            button.addActionListener(e -> {
                for (JToggleButton item : filterButtons) {
                    boolean selected = item.isSelected();
                    item.setBackground(selected ? GOLD : CARD_BG);
                    item.setForeground(selected ? BACKGROUND : IVORY);
                }
                applyFilters.run();
            });
        }

        for (JToggleButton button : filterButtons) {
            button.setBackground(
                    button.isSelected() ? GOLD : CARD_BG);
            button.setForeground(
                    button.isSelected() ? BACKGROUND : IVORY);
        }

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(SIDEBAR_BG);
        footer.setBorder(BorderFactory.createEmptyBorder(
                12, 20, 12, 20));

        footer.add(label(
                "TastyHub  |  Quality food at great prices",
                12, MUTED, false), BorderLayout.WEST);

        footer.add(label(
                "FRESH FLAVOURS • GREAT VALUE",
                10, GOLD, true), BorderLayout.EAST);

        content.add(topPanel, BorderLayout.NORTH);
        content.add(menuBody, BorderLayout.CENTER);

        menuFrame.add(header, BorderLayout.NORTH);
        menuFrame.add(content, BorderLayout.CENTER);
        menuFrame.add(footer, BorderLayout.SOUTH);

        applyFilters.run();
        updateCartButton();
        menuFrame.setVisible(true);
    }

    // ================= CART COUNT =================

    private static void updateCartButton() {
        if (cartButtonLabel == null) {
            return;
        }

        int totalQuantity = 0;

        for (int quantity : cart.values()) {
            totalQuantity += quantity;
        }

        cartButtonLabel.setText("Cart (" + totalQuantity + ")");
    }

    // ================= SHOPPING CART =================

    private static void showCartDialog() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Your shopping cart is empty.\n"
                    + "Add food from the menu first.",
                    "Shopping Cart",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(
                menuFrame, "TastyHub | Your Shopping Cart", true);

        dialog.setSize(760, 560);
        dialog.setMinimumSize(new Dimension(620, 450));
        dialog.setLocationRelativeTo(menuFrame);
        dialog.setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(BACKGROUND);
        main.setBorder(BorderFactory.createEmptyBorder(
                22, 22, 18, 22));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = label("Your Shopping Cart", 25, IVORY, true);
        JLabel subtitle = label(
                "Review your items before proceeding to checkout.",
                13, MUTED, false);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(BACKGROUND);

        JLabel subtotalLabel = label("", 14, IVORY, false);
        JLabel deliveryLabel = label("", 14, IVORY, false);
        JLabel totalLabel = label("", 19, GOLD, true);

        for (String food : new ArrayList<>(cart.keySet())) {
            final int index = foodIndex(food);

            if (index < 0) {
                continue;
            }

            JPanel itemCard = new JPanel(new BorderLayout(12, 8));
            itemCard.setBackground(CARD_BG);
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(
                            12, 14, 12, 14)));
            itemCard.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 95));
            itemCard.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel details = new JPanel();
            details.setOpaque(false);
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

            JLabel foodLabel = label(food, 15, IVORY, true);
            JLabel priceLabel = label(
                    "UGX " + money(PRICES[index]) + " each",
                    12, MUTED, false);

            details.add(foodLabel);
            details.add(Box.createVerticalStrut(7));
            details.add(priceLabel);

            JPanel controls = new JPanel(new GridLayout(2, 1, 4, 4));
            controls.setOpaque(false);

            JPanel quantityRow = new JPanel(
                    new FlowLayout(FlowLayout.RIGHT, 6, 0));
            quantityRow.setOpaque(false);

            JLabel quantityLabel = label("Qty:", 12, IVORY, false);

            // FIXED: Create the spinner and its number model.
            JSpinner spinner = new JSpinner(
                    new SpinnerNumberModel(
                            Integer.valueOf(cart.get(food)),
                            Integer.valueOf(1),
                            Integer.valueOf(99),
                            Integer.valueOf(1)
                    )
            );

            spinner.setPreferredSize(new Dimension(65, 28));

            quantityRow.add(quantityLabel);
            quantityRow.add(spinner);

            JPanel actionRow = new JPanel(
                    new FlowLayout(FlowLayout.RIGHT, 8, 0));
            actionRow.setOpaque(false);

            JLabel lineTotal = label("", 13, GOLD, true);

            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Arial", Font.BOLD, 11));
            removeButton.setForeground(RED);
            removeButton.setBackground(CARD_BG);
            removeButton.setFocusPainted(false);
            removeButton.setCursor(
                    new Cursor(Cursor.HAND_CURSOR));

            Runnable updateLine = () -> {
                int quantity = (Integer) spinner.getValue();

                cart.put(food, quantity);

                lineTotal.setText(
                        "UGX " + money(quantity * PRICES[index]));

                updateCartTotals(
                        subtotalLabel, deliveryLabel, totalLabel);

                updateCartButton();
            };

            updateLine.run();

            spinner.addChangeListener(e -> updateLine.run());

            removeButton.addActionListener(e -> {
                cart.remove(food);
                updateCartButton();
                dialog.dispose();

                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            menuFrame,
                            "Your cart is now empty.",
                            "Shopping Cart",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showCartDialog();
                }
            });

            actionRow.add(lineTotal);
            actionRow.add(removeButton);

            controls.add(quantityRow);
            controls.add(actionRow);

            itemCard.add(details, BorderLayout.CENTER);
            itemCard.add(controls, BorderLayout.EAST);

            itemPanel.add(itemCard);
            itemPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel summary = new JPanel(new GridLayout(3, 1, 5, 5));
        summary.setBackground(CARD_BG);
        summary.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));

        summary.add(subtotalLabel);
        summary.add(deliveryLabel);
        summary.add(totalLabel);

        updateCartTotals(subtotalLabel, deliveryLabel, totalLabel);

        JPanel bottom = new JPanel(new BorderLayout(10, 12));
        bottom.setOpaque(false);
        bottom.add(summary, BorderLayout.CENTER);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttons.setOpaque(false);

        JButton continueButton =
                styledButton("Continue Shopping", false);

        JButton checkoutButton =
                styledButton("Proceed to Checkout", true);

        continueButton.addActionListener(e -> dialog.dispose());

        checkoutButton.addActionListener(e -> {
            dialog.dispose();
            checkout();
        });

        buttons.add(continueButton);
        buttons.add(checkoutButton);

        bottom.add(buttons, BorderLayout.SOUTH);

        main.add(heading, BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        dialog.add(main, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // ================= CART TOTALS =================

    private static void updateCartTotals(
            JLabel subtotalLabel,
            JLabel deliveryLabel,
            JLabel totalLabel) {

        int subtotal = calculateCartSubtotal();
        int delivery = cart.isEmpty() ? 0 : DELIVERY_FEE;

        subtotalLabel.setText(
                "Food Subtotal: UGX " + money(subtotal));

        deliveryLabel.setText(
                "Delivery Fee: UGX " + money(delivery));

        totalLabel.setText(
                "Grand Total: UGX " + money(subtotal + delivery));
    }

    private static int calculateCartSubtotal() {
        int subtotal = 0;

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            int index = foodIndex(entry.getKey());

            if (index >= 0) {
                subtotal += PRICES[index] * entry.getValue();
            }
        }

        return subtotal;
    }

    private static int foodIndex(String foodName) {
        for (int i = 0; i < FOOD_NAMES.length; i++) {
            if (FOOD_NAMES[i].equals(foodName)) {
                return i;
            }
        }

        return -1;
    }

    // ================= CHECKOUT =================

    private static void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Your cart is empty. Add food before checking out.",
                    "Empty Cart",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = JOptionPane.showInputDialog(
                menuFrame,
                "Enter Customer Name:",
                "Customer Information",
                JOptionPane.QUESTION_MESSAGE);

        if (name == null) {
            return;
        }

        name = name.trim();

        if (name.isEmpty() || !name.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Invalid name! Please use letters only.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String phoneNo = JOptionPane.showInputDialog(
                menuFrame,
                "Enter phone number (10 digits):",
                "Customer Information",
                JOptionPane.QUESTION_MESSAGE);

        if (phoneNo == null) {
            return;
        }

        phoneNo = phoneNo.trim();

        if (!phoneNo.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Phone number must contain exactly 10 digits.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] deliveryAreas = {
            "Kampala Main Town", "Kasubi", "Nsambya", "Makindye",
            "Kansanga", "Kabalagala", "Muyenga", "Ggaba", "Bunga",
            "Rubaga", "Ntinda", "Bugolobi", "Bukoto", "Kisaasi",
            "Other/outside our delivery areas"
        };

        String deliveryArea = (String) JOptionPane.showInputDialog(
                menuFrame,
                "Please select your delivery area:",
                "TastyHub Delivery Area",
                JOptionPane.QUESTION_MESSAGE,
                null,
                deliveryAreas,
                deliveryAreas[0]);

        if (deliveryArea == null) {
            return;
        }

        if (deliveryArea.equals(
                "Other/outside our delivery areas")) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Sorry, we do not currently deliver to that area.\n"
                    + "Please choose one of our available delivery areas.",
                    "Delivery Area Unavailable",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String address = JOptionPane.showInputDialog(
                menuFrame,
                "Enter your detailed delivery address in "
                + deliveryArea + ":",
                "Delivery Address",
                JOptionPane.QUESTION_MESSAGE);

        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Please enter your delivery address.",
                    "Missing Address",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        address = address.trim();

        Customer customer = new Customer(
                name, phoneNo, deliveryArea, address);

        Order order = new Order(0, customer, address);

        StringBuilder itemBlock = new StringBuilder();

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String foodName = entry.getKey();
            int quantity = entry.getValue();
            int index = foodIndex(foodName);

            if (index < 0 || quantity <= 0) {
                continue;
            }

            FoodItem food = new FoodItem(
                    index + 1,
                    FOOD_NAMES[index],
                    PRICES[index],
                    CATEGORIES[index],
                    true);

            order.addItem(new OrderItem(food, quantity));

            itemBlock.append(foodName)
                    .append(" x ").append(quantity)
                    .append(" = UGX ")
                    .append(money(PRICES[index] * quantity))
                    .append("\n");
        }

        int subtotal = (int) order.getTotal();

        if (subtotal <= 0) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Your cart has no valid food items.",
                    "Invalid Cart",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int grandTotal = subtotal + DELIVERY_FEE;
        int estimatedMinutes =
                DeliveryETA.estimateMinutes(deliveryArea);

        String[] paymentMethods = {
            "Mobile Money", "Cash on Delivery"
        };

        String paymentMethod = (String) JOptionPane.showInputDialog(
                menuFrame,
                "Choose your payment method:\n"
                + "Payment must be confirmed before dispatch.",
                "TastyHub Payment",
                JOptionPane.QUESTION_MESSAGE,
                null,
                paymentMethods,
                paymentMethods[0]);

        if (paymentMethod == null) {
            return;
        }

        String confirmation =
                "Customer: " + name
                + "\nPhone: " + phoneNo
                + "\nDelivery area: " + deliveryArea
                + "\nAddress: " + address
                + "\nPayment method: " + paymentMethod
                + "\n\nORDER ITEMS\n" + itemBlock
                + "\nFood subtotal: UGX " + money(subtotal)
                + "\nDelivery fee: UGX " + money(DELIVERY_FEE)
                + "\nGRAND TOTAL: UGX " + money(grandTotal)
                + "\n\nConfirm this order?";

        int answer = JOptionPane.showConfirmDialog(
                menuFrame,
                confirmation,
                "Confirm TastyHub Checkout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (answer != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Checkout cancelled. Your cart has been kept.",
                    "Order Cancelled",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Save the order using the existing database integration.
        OrderDAO orderDAO = new OrderDAO();
        int savedOrderId = orderDAO.submitOrder(order, paymentMethod);

        if (savedOrderId == -1) {
            JOptionPane.showMessageDialog(
                    menuFrame,
                    "Order could not be saved. Please try again.\n"
                    + "Your cart has been kept.",
                    "Submission Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String receipt =
                "                 TASTYHUB\n"
                + "              ORDER RECEIPT\n"
                + "========================================\n\n"
                + "ORDER NO: TH"
                + String.format("%03d", savedOrderId) + "\n"
                + "STATUS: PENDING\n"
                + "PAYMENT METHOD: " + paymentMethod + "\n\n"
                + "CUSTOMER DETAILS\n"
                + "----------------------------------------\n"
                + "Name: " + name + "\n"
                + "Phone: " + phoneNo + "\n"
                + "Delivery Area: " + deliveryArea + "\n"
                + "Address: " + address + "\n"
                + "Estimated Delivery: " + estimatedMinutes
                + " minutes\n\n"
                + "YOUR ORDER\n"
                + "----------------------------------------\n"
                + itemBlock
                + "----------------------------------------\n"
                + "Food Subtotal: UGX " + money(subtotal) + "\n"
                + "Delivery Fee:  UGX " + money(DELIVERY_FEE) + "\n"
                + "========================================\n"
                + "GRAND TOTAL:   UGX " + money(grandTotal) + "\n"
                + "========================================\n\n"
                + "Thank you for choosing TastyHub!\n"
                + "Fresh food, delivered with care.";

        JTextArea receiptArea = new JTextArea(receipt);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);
        receiptArea.setBackground(CARD_BG);
        receiptArea.setForeground(IVORY);
        receiptArea.setCaretColor(GOLD);
        receiptArea.setBorder(BorderFactory.createEmptyBorder(
                20, 20, 20, 20));
        receiptArea.setCaretPosition(0);
        receiptArea.setLineWrap(false);

        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        receiptScroll.setPreferredSize(new Dimension(480, 440));
        receiptScroll.setBorder(
                BorderFactory.createLineBorder(GOLD, 2));
JPanel receiptPanel = new JPanel(new BorderLayout(10, 12));
receiptPanel.setBackground(BACKGROUND);
receiptPanel.setBorder(
        BorderFactory.createEmptyBorder(8, 8, 8, 8));

receiptPanel.add(receiptScroll, BorderLayout.CENTER);

JPanel receiptActions = new JPanel(
        new FlowLayout(FlowLayout.CENTER));
receiptActions.setBackground(BACKGROUND);
receiptPanel.add(receiptActions, BorderLayout.SOUTH);

JOptionPane.showMessageDialog(
        menuFrame,
        receiptPanel,
        "TastyHub | Order Receipt",
        JOptionPane.PLAIN_MESSAGE);
cart.clear();
updateCartButton();

JOptionPane.showMessageDialog(
        menuFrame,
        "Your order was saved successfully.",
        "Order Successful",
        JOptionPane.INFORMATION_MESSAGE);
}

    // ================= APPLICATION ENTRY POINT =================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TastyHub::showWelcomeScreen);
    }
    
private static class WelcomeBackgroundPanel extends JPanel {

    private final Image backgroundImage;

    public WelcomeBackgroundPanel() {
        URL imageURL = TastyHub.class.getResource("/images/welcome.png");

        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            backgroundImage = null;
        }

        setLayout(new BorderLayout());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        if (backgroundImage != null) {
            g2.drawImage(
                    backgroundImage,
                    0, 0,
                    getWidth(), getHeight(),
                    this);
        } else {
            g2.setColor(SIDEBAR_BG);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark transparent overlay keeps text readable.
        g2.setColor(new Color(0, 0, 0, 155));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}

}