package frontend;

import backend.DatabaseManager;
import backend.BookingManager;
import backend.User;
import backend.Car;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Swing frontend for Smart Car Rental System
 */
public class BookingFront {

    private static class LocalUser {
        String name;
        String email;
        String password;

        LocalUser(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    private final Map<String, LocalUser> userDatabase = new HashMap<>();
    private LocalUser currentUser;

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Colors, fonts
    private static final Color COLOR_NAVBAR = new Color(0x0A0D32);
    private static final Font FONT_GENERAL = new Font("Serif", Font.PLAIN, 16);
    private static final Font FONT_BUTTON = new Font("Serif", Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 24);

    // Assets
    private ImageIcon loginLogoIcon;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public BookingFront() {
        loadAssets();

        frame = new JFrame("Smart Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel initialPanel = createInitialPanel();
        mainPanel.add(initialPanel, "initial");

        frame.add(mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void loadAssets() {
        try {
            Image loginLogoImage = ImageIO.read(new File("resources/logo-removebg-preview3.png"))
                    .getScaledInstance(250, 100, Image.SCALE_SMOOTH);
            loginLogoIcon = new ImageIcon(loginLogoImage);
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

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

        gbcChoice.gridx = 0;
        gbcChoice.gridy = 0;
        gbcChoice.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        choicePanel.add(logoLabel, gbcChoice);

        gbcChoice.gridy++;
        JButton loginChoiceButton = new JButton("LOGIN");
        styleButton(loginChoiceButton);
        choicePanel.add(loginChoiceButton, gbcChoice);

        gbcChoice.gridy++;
        JButton signupChoiceButton = new JButton("SIGN UP");
        styleButton(signupChoiceButton);
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

    private JPanel createLoginPanel(JPanel container, CardLayout layout) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        panel.add(logoLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel userLabel = new JLabel("Username:");
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField userText = new JTextField(20);
        panel.add(userText, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passText = new JPasswordField(20);
        panel.add(passText, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("SUBMIT");
        JButton backButton = new JButton("BACK");
        styleButton(loginButton);
        styleButton(backButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            LocalUser user = userDatabase.get(username);
            if (user != null && user.password.equals(password)) {
                currentUser = user;
                mainPanel.add(createMainPanel(), "mainApp");
                cardLayout.show(mainPanel, "mainApp");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            userText.setText("");
            passText.setText("");
            layout.show(container, "choice");
        });

        return panel;
    }

    private JPanel createSignUpPanel(JPanel container, CardLayout layout) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel logoLabel = new JLabel(loginLogoIcon);
        panel.add(logoLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameText = new JTextField(20);
        panel.add(nameText, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField emailText = new JTextField(20);
        panel.add(emailText, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passText = new JPasswordField(20);
        panel.add(passText, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton signupButton = new JButton("CREATE");
        JButton backButton = new JButton("BACK");
        styleButton(signupButton);
        styleButton(backButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);

        signupButton.addActionListener(e -> {
            String name = nameText.getText();
            String email = emailText.getText();
            String password = new String(passText.getPassword());
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                JOptionPane.showMessageDialog(frame, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (userDatabase.containsKey(name)) {
                JOptionPane.showMessageDialog(frame, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalUser newUser = new LocalUser(name, email, password);
            userDatabase.put(name, newUser);
            JOptionPane.showMessageDialog(frame, "User created successfully!");
            layout.show(container, "choice");
        });

        backButton.addActionListener(e -> layout.show(container, "choice"));

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome, " + currentUser.name, SwingConstants.CENTER);
        label.setFont(FONT_TITLE);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(COLOR_NAVBAR);
        button.setForeground(Color.WHITE);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingFront ui = new BookingFront();
            ui.show();
        });
    }
}

