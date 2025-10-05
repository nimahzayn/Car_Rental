import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Main application class for the Smart Car Rental System.
 * This class sets up the main frame and uses a CardLayout to switch
 * between the login/signup screen, the main application view, and the my cars page.
 */
public class SmartCarRentalSystem {


    // --- User Class ---
    private static class User {
        String name;
        String emailOrPhone;
        String password;


        User(String name, String emailOrPhone, String password) {
            this.name = name;
            this.emailOrPhone = emailOrPhone;
            this.password = password;
        }
    }


    // --- In-memory user database ---
    private final Map<String, User> userDatabase = new HashMap<>();


    // Global colors and fonts for a consistent, sleek design
    private static final Color COLOR_NAVBAR = new Color(0x0A0D32); // Navy Blue for a refined look
    private static final Color COLOR_BACKGROUND = new Color(0x1A237E); // Deeper, more refined blue
    private static final Color COLOR_TEXT = new Color(0xFFFFFF);       // White
    private static final Color COLOR_PROFILE_ICON = new Color(0x0D47A1); // Darker blue for contrast
    private static final Color COLOR_ACCENT = new Color(0x2196F3);     // A brighter blue for accents
    private static final Font FONT_GENERAL = new Font("Serif", Font.PLAIN, 16);
    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 24);
    private static final Font FONT_BUTTON = new Font("Serif", Font.BOLD, 14);


    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User currentUser;
    private Image backgroundImage;
    private ImageIcon mainLogoIcon;
    private ImageIcon loginLogoIcon;


    // Regex patterns for validation
private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$");


    public SmartCarRentalSystem() {
        // --- Load Assets ---
        loadAssets();


        // --- Main Frame Setup ---
        frame = new JFrame("Smart Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null); // Center the frame on screen


        // --- CardLayout for View Switching ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        // --- Create and add different views (panels) ---
        JPanel initialPanel = createInitialPanel();
        mainPanel.add(initialPanel, "initial");
        // Main app and My Cars panels will be added dynamically after login


        frame.add(mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Optimize for full-screen
    }
   
    /**
     * Loads images from files.
     */
    private void loadAssets() {
        try {
            backgroundImage = ImageIO.read(new File("car.jpg"));
            // Logo for the app bar
            Image mainLogoImage = ImageIO.read(new File("bg-removebg-preview2.png")).getScaledInstance(150, 60, Image.SCALE_SMOOTH);
            mainLogoIcon = new ImageIcon(mainLogoImage);
            // Logo for the login screen
            Image loginLogoImage = ImageIO.read(new File("logo-removebg-preview3.png")).getScaledInstance(250, 100, Image.SCALE_SMOOTH);
            loginLogoIcon = new ImageIcon(loginLogoImage);
        } catch (IOException e) {
            System.err.println("Error loading images. Make sure car.jpg and logo-removebg-preview3.png are in the correct directory.");
            backgroundImage = null; // Set to null if loading fails
            mainLogoIcon = null;
            loginLogoIcon = null;
        }
    }




    /**
     * Creates the very first panel asking the user to either Login or Sign Up.
     * @return A JPanel for the initial choice.
     */
    private JPanel createInitialPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
       
        CardLayout innerCardLayout = new CardLayout();
        JPanel formPanel = new JPanel(innerCardLayout);
        formPanel.setOpaque(false);


        JPanel choicePanel = new JPanel(new GridBagLayout());
        choicePanel.setOpaque(false);
        GridBagConstraints gbcChoice = new GridBagConstraints();
        gbcChoice.insets = new Insets(10, 10, 10, 10);
       
        // Add the logo image instead of text
        gbcChoice.gridx = 0;
        gbcChoice.gridy = 0;
        gbcChoice.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        choicePanel.add(logoLabel, gbcChoice);


        gbcChoice.gridy++;
        JButton loginChoiceButton = new JButton("LOGIN");
        styleLoginButton(loginChoiceButton);
        choicePanel.add(loginChoiceButton, gbcChoice);


        gbcChoice.gridy++;
        JButton signupChoiceButton = new JButton("SIGN UP");
        styleLoginButton(signupChoiceButton);
        choicePanel.add(signupChoiceButton, gbcChoice);


        JPanel loginPanel = createLoginPanel(formPanel, innerCardLayout);
        JPanel signupPanel = createSignUpPanel(formPanel, innerCardLayout);
       
        formPanel.add(choicePanel, "choice");
        formPanel.add(loginPanel, "login");
        formPanel.add(signupPanel, "signup");
       
        panel.add(formPanel);


        loginChoiceButton.addActionListener(e -> innerCardLayout.show(formPanel, "login"));
        signupChoiceButton.addActionListener(e -> innerCardLayout.show(formPanel, "signup"));


        return panel;
    }




    /**
     * Creates the login form panel.
     * @param container The parent panel with CardLayout.
     * @param layout The CardLayout manager.
     * @return A JPanel with the login form.
     */
    private JPanel createLoginPanel(JPanel container, CardLayout layout) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        // Add the logo image instead of text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        panel.add(logoLabel, gbc);


        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel userLabel = new JLabel("Username (Name):");
        userLabel.setFont(FONT_GENERAL);
        userLabel.setForeground(Color.BLACK);
        panel.add(userLabel, gbc);


        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField userText = new JTextField(20);
        userText.setFont(FONT_GENERAL);
        panel.add(userText, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(FONT_GENERAL);
        passLabel.setForeground(Color.BLACK);
        panel.add(passLabel, gbc);


        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passText = new JPasswordField(20);
        passText.setFont(FONT_GENERAL);
        panel.add(passText, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton loginButton = new JButton("SUBMIT");
        JButton backButton = new JButton("BACK");
        styleLoginButton(loginButton);
        styleLoginButton(backButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);


        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            User user = userDatabase.get(username);


            if (user != null && user.password.equals(password)) {
                currentUser = user;
                // Add new panels for the logged-in user
                mainPanel.add(createMainApplicationPanel(), "mainApp");
                mainPanel.add(createMyCarsPanel(), "myCars");
                cardLayout.show(mainPanel, "mainApp");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
       
        backButton.addActionListener(e -> {
            userText.setText("");
            passText.setText("");
            layout.show(container, "choice");
        });


        return panel;
    }
   
    /**
     * Creates the sign-up form panel.
     * @param container The parent panel with CardLayout.
     * @param layout The CardLayout manager.
     * @return A JPanel with the sign-up form.
     */
    private JPanel createSignUpPanel(JPanel container, CardLayout layout) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        // Add the logo image instead of text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        panel.add(logoLabel, gbc);


        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(FONT_GENERAL);
        nameLabel.setForeground(Color.BLACK);
        panel.add(nameLabel, gbc);
       
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameText = new JTextField(20);
        nameText.setFont(FONT_GENERAL);
        panel.add(nameText, gbc);
       
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(FONT_GENERAL);
        emailLabel.setForeground(Color.BLACK);
        panel.add(emailLabel, gbc);


        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField emailText = new JTextField(20);
        emailText.setFont(FONT_GENERAL);
        panel.add(emailText, gbc);
       
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Set Password:");
        passLabel.setFont(FONT_GENERAL);
        passLabel.setForeground(Color.BLACK);
        panel.add(passLabel, gbc);
       
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passText = new JPasswordField(20);
        passText.setFont(FONT_GENERAL);
        panel.add(passText, gbc);
       
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton signupButton = new JButton("CREATE PROFILE");
        JButton backButton = new JButton("BACK");
        styleLoginButton(signupButton);
        styleLoginButton(backButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);
       
        signupButton.addActionListener(e -> {
            String name = nameText.getText();
            String email = emailText.getText();
            String password = new String(passText.getPassword());


            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (name.length() < 2 || name.length() > 50) {
                JOptionPane.showMessageDialog(frame, "Name must be between 2 and 50 characters.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                JOptionPane.showMessageDialog(frame, "Invalid email format. Please use text@text.com.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and contain a number, a special character, and both uppercase and lowercase letters.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (userDatabase.containsKey(name)) {
                JOptionPane.showMessageDialog(frame, "Username (Name) already exists.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            User newUser = new User(name, email, password);
            userDatabase.put(name, newUser);
            currentUser = newUser;
           
            // Add new panels for the new user
            mainPanel.add(createMainApplicationPanel(), "mainApp");
            mainPanel.add(createMyCarsPanel(), "myCars");
            cardLayout.show(mainPanel, "mainApp");
        });


        backButton.addActionListener(e -> {
            nameText.setText("");
            emailText.setText("");
            passText.setText("");
            layout.show(container, "choice");
        });


        return panel;
    }




    /**
     * Applies a sleek style to the buttons on the login page.
     */
    private void styleLoginButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(new Color(0x333333));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }




    /**
     * Creates the main application panel that appears after logging in.
     * @return A JPanel containing the main application UI.
     */
    private JPanel createMainApplicationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BACKGROUND);


        // --- App Bar (Top) ---
        panel.add(createAppBar(), BorderLayout.NORTH);


        // --- Main Content Area ---
        panel.add(createContentArea(), BorderLayout.CENTER);


        return panel;
    }
   
    /**
     * Creates the "My Cars" page panel.
     * @return A JPanel representing the My Cars page.
     */
    private JPanel createMyCarsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BACKGROUND);
        panel.add(createAppBar(), BorderLayout.NORTH); // Re-use the app bar


        JLabel title = new JLabel("My Rented Cars", SwingConstants.CENTER);
        title.setFont(FONT_TITLE.deriveFont(40f));
        title.setForeground(COLOR_TEXT);
       
        JButton backButton = new JButton("← Back to Home");
        styleMenuButton(backButton);
        backButton.setFont(FONT_GENERAL);
        backButton.setOpaque(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainApp"));
       
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        topPanel.add(backButton, BorderLayout.WEST);


        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(title, BorderLayout.CENTER);


        return panel;
    }




    /**
     * Creates the top application bar with logo, profile, and menu.
     * @return A JPanel representing the app bar.
     */
    private JComponent createAppBar() {
        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBackground(COLOR_NAVBAR);
        appBar.setBorder(new EmptyBorder(45, 50, 45, 50)); // Thicker and more spaced navbar


        // --- Logo (Left) ---
        JLabel logo = new JLabel(mainLogoIcon);
        appBar.add(logo, BorderLayout.WEST);


        // --- Right Menu Items ---
        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 0)); // Moved a little to the right
        rightMenu.setOpaque(false);


        // --- Profile Button ---
        JButton profileButton = new JButton();
        profileButton.setIcon(new CircularProfileIcon(currentUser.name.substring(0, 1).toUpperCase()));
        styleMenuButton(profileButton);


        JPopupMenu profileMenu = new JPopupMenu();
        stylePopupMenu(profileMenu);
       
        // Add profile silhouette to the menu
        JMenuItem profileIconItem = new JMenuItem(new ImageIcon(getScaledSilhouette()));
        profileIconItem.setEnabled(false); // Make it non-clickable
        profileMenu.add(profileIconItem);


        profileMenu.add(new JMenuItem("Name: " + currentUser.name));
        profileMenu.add(new JMenuItem(currentUser.emailOrPhone));
        profileMenu.addSeparator();
        JMenuItem myCarsItem = new JMenuItem("My Cars");
        myCarsItem.addActionListener(e -> cardLayout.show(mainPanel, "myCars"));
        profileMenu.add(myCarsItem);
        JMenuItem logoutItem = new JMenuItem("Log Out");
        logoutItem.addActionListener(e -> {
            currentUser = null;
            // Remove user-specific panels
            if (mainPanel.getComponentCount() > 1) {
                 mainPanel.remove(2); // myCars
                 mainPanel.remove(1); // mainApp
            }
            cardLayout.show(mainPanel, "initial");
        });
        profileMenu.add(logoutItem);




        profileButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                profileMenu.show(e.getComponent(), 0, e.getComponent().getHeight());
            }
        });


        // --- Hamburger Menu Button ---
        JButton hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Serif", Font.BOLD, 24));
        styleMenuButton(hamburgerButton);
       
        hamburgerButton.addActionListener(e -> showHamburgerMenu());
       
        rightMenu.add(profileButton);
        rightMenu.add(hamburgerButton);
        appBar.add(rightMenu, BorderLayout.EAST);


        return appBar;
    }
   
    /**
     * Gets a simple silhouette image to be used in the profile menu.
     */
    private Image getScaledSilhouette() {
        BufferedImage silhouette = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = silhouette.createGraphics();
        g2.setColor(Color.WHITE);
        // This is a simple placeholder to prevent errors. A real image would be better.
        g2.dispose();
        return silhouette.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
    }
   
    /**
     * Creates and shows a JDialog that acts as a full-height hamburger menu.
     */
    private void showHamburgerMenu() {
        JDialog menuDialog = new JDialog(frame, true); // Modal dialog
        menuDialog.setUndecorated(true);
        menuDialog.setBackground(new Color(0,0,0,0)); // Transparent background
       
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(COLOR_NAVBAR);
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, COLOR_BACKGROUND));


        // Close button at the top
        JButton closeButton = new JButton("X");
        closeButton.setForeground(COLOR_TEXT);
        closeButton.setBackground(COLOR_NAVBAR);
        closeButton.setFont(new Font("Serif", Font.BOLD, 20));
        closeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        closeButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        closeButton.addActionListener(e -> menuDialog.dispose());
        menuPanel.add(closeButton);


        String[] menuItems = {"Home", "About Us", "Contact Us", "FAQs", "Reviews", "Privacy Policy"};
        for (String item : menuItems) {
            JButton menuItemButton = new JButton(item);
            menuItemButton.setForeground(COLOR_TEXT);
            menuItemButton.setBackground(COLOR_NAVBAR);
            menuItemButton.setFont(FONT_GENERAL.deriveFont(18f));
            menuItemButton.setFocusPainted(false);
            menuItemButton.setBorder(new EmptyBorder(20, 40, 20, 40));
            menuItemButton.setHorizontalAlignment(SwingConstants.LEFT);
            menuItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            menuItemButton.addActionListener(e -> menuDialog.dispose()); // Close menu on click
            menuPanel.add(menuItemButton);
        }


        menuDialog.add(menuPanel);
        menuDialog.pack();
        menuDialog.setSize(300, frame.getHeight());
        menuDialog.setLocation(frame.getX() + frame.getWidth() - 300, frame.getY());
       
        // Listener to close menu on outside click
        menuDialog.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getX() < menuDialog.getWidth() - menuPanel.getWidth()) {
                    menuDialog.dispose();
                }
            }
        });
        menuDialog.setVisible(true);
    }
   
    /**
     * Creates the main content area with a background and the "Find My Car" panel.
     * @return A JLayeredPane containing the content.
     */
    private JComponent createContentArea() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(frame.getSize()); // Required for layered pane to respect its components


        // --- Background Panel (Layer 0) ---
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback gradient if image fails to load
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, COLOR_BACKGROUND, 0, getHeight(), new Color(0,0,0));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        backgroundPanel.setOpaque(true);


        // --- UI Panel (Layer 1) ---
        JPanel uiPanel = new JPanel(new GridBagLayout());
        uiPanel.setOpaque(false);
        uiPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());


        // Use a 3:1 ratio for content distribution
        GridBagConstraints gbcText = new GridBagConstraints();
        gbcText.gridx = 0;
        gbcText.weightx = 0.75;
        gbcText.anchor = GridBagConstraints.CENTER;
       
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));


        JLabel veloceLabel = new JLabel("VELOCE.");
        veloceLabel.setFont(new Font("Serif", Font.BOLD, 100));
        veloceLabel.setForeground(COLOR_TEXT);
        veloceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(veloceLabel);


        JLabel sloganLabel = new JLabel("The smart way to rent.");
        sloganLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        sloganLabel.setForeground(COLOR_TEXT);
        sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(sloganLabel);


        uiPanel.add(textPanel, gbcText);


        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 1;
        gbcButton.weightx = 0.25;
        gbcButton.anchor = GridBagConstraints.CENTER;
       
        JButton findCarButton = new RoundedButton("Find My Car");
        findCarButton.setFont(FONT_BUTTON.deriveFont(24f));
        findCarButton.setBackground(COLOR_NAVBAR);
        findCarButton.setForeground(COLOR_TEXT);
        findCarButton.setFocusPainted(false);
        findCarButton.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));
        findCarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
       
        uiPanel.add(findCarButton, gbcButton);


        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(uiPanel, JLayeredPane.PALETTE_LAYER);


        return layeredPane;
    }


    /**
     * Applies a consistent style to menu buttons in the app bar.
     */
    private void styleMenuButton(JButton button) {
        button.setForeground(COLOR_TEXT);
        button.setBackground(COLOR_NAVBAR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
    }
   
    /**
     * Applies a consistent dark style to popup menus.
     */
    private void stylePopupMenu(JPopupMenu menu) {
        menu.setBackground(COLOR_NAVBAR);
        menu.setBorder(BorderFactory.createLineBorder(COLOR_BACKGROUND));
        for (Component comp : menu.getComponents()) {
            if (comp instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) comp;
                menuItem.setBackground(COLOR_NAVBAR);
                menuItem.setForeground(COLOR_TEXT);
                menuItem.setFont(FONT_GENERAL);
            }
        }
    }


    /**
     * Displays the application window.
     */
    public void show() {
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            SmartCarRentalSystem app = new SmartCarRentalSystem();
            app.show();
        });
    }


    /**
     * A custom Icon that renders a colored circle with a character in the center.
     */
    class CircularProfileIcon implements Icon {
        private final String letter;
        private static final int SIZE = 38; // Adjusted size for better visibility


        public CircularProfileIcon(String letter) {
            this.letter = letter;
        }


        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            // Draw circle
            g2d.setColor(COLOR_PROFILE_ICON);
            g2d.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));


            // Draw letter
            g2d.setColor(COLOR_TEXT);
            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            FontMetrics fm = g2d.getFontMetrics();
            int letterX = x + (SIZE - fm.stringWidth(letter)) / 2;
            int letterY = y + (fm.getAscent() + (SIZE - (fm.getAscent() + fm.getDescent())) / 2);
            g2d.drawString(letter, letterX, letterY);


            g2d.dispose();
        }


        @Override
        public int getIconWidth() {
            return SIZE;
        }


        @Override
        public int getIconHeight() {
            return SIZE;
        }
    }
   
    /**
     * A custom JButton that draws a rounded rectangle background.
     */
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }


        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           
            int width = getWidth();
            int height = getHeight();
           
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, 20, 20));
           
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
}