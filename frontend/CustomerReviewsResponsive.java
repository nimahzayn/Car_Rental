import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerReviewsResponsive extends JFrame {

    private JTextArea reviewTextArea;
    private JButton postButton;
    private JPanel reviewsContainer;
    private ArrayList<JLabel> starLabels;
    private int selectedRating = 0;
    private boolean isLoggedIn = false; // Simulated login status

    public CustomerReviewsResponsive() {
        setTitle("Customer Reviews | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ====== Root Container ======
        JPanel rootPanel = new JPanel(new BorderLayout(20, 20));
        rootPanel.setBackground(new Color(0xF9, 0xF9, 0xF9));
        rootPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(rootPanel);

        // ====== Header ======
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(0x22, 0x43, 0x64), 2, true),
                new EmptyBorder(20, 30, 20, 30)
        ));

        JLabel headerTitle = new JLabel("Customer Reviews", SwingConstants.CENTER);
        headerTitle.setFont(new Font("Poppins", Font.BOLD, 28));
        headerTitle.setForeground(new Color(0x22, 0x43, 0x64));
        headerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headerSubtitle = new JLabel("Share your experience and see what others think about Veloce");
        headerSubtitle.setFont(new Font("Poppins", Font.PLAIN, 15));
        headerSubtitle.setForeground(new Color(0x55, 0x55, 0x55));
        headerSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(headerTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(headerSubtitle);

        rootPanel.add(headerPanel, BorderLayout.NORTH);

        // ====== Main Section (Scrollable Center) ======
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(0xDD, 0xDD, 0xDD), 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Wrap inside scrollable viewport
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        rootPanel.add(mainScrollPane, BorderLayout.CENTER);

        // ====== “Write a Review” Section ======
        JLabel writeTitle = new JLabel("Write a Review");
        writeTitle.setFont(new Font("Poppins", Font.BOLD, 20));
        writeTitle.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(0x46, 0x6B, 0x8F)));
        writeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(writeTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel reviewBox = new JPanel();
        reviewBox.setLayout(new GridBagLayout());
        reviewBox.setBackground(Color.WHITE);
        reviewBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;

        // Star Rating Panel
        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        starsPanel.setBackground(Color.WHITE);

        starLabels = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            JLabel star = new JLabel("★");
            star.setFont(new Font("SansSerif", Font.PLAIN, 30));
            star.setForeground(new Color(0xCC, 0xCC, 0xCC));
            int rating = i;
            star.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    highlightStars(rating);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    highlightStars(selectedRating);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedRating = rating;
                    highlightStars(selectedRating);
                }
            });
            starLabels.add(star);
            starsPanel.add(star);
        }

        reviewBox.add(starsPanel, gbc);

        // Review TextArea
        reviewTextArea = new JTextArea(5, 50);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setFont(new Font("Poppins", Font.PLAIN, 14));
        reviewTextArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0xCC, 0xCC, 0xCC), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        reviewTextArea.setText(isLoggedIn ? "" : "Log in to share your review...");
        reviewTextArea.setEnabled(isLoggedIn);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        reviewBox.add(new JScrollPane(reviewTextArea), gbc);

        // Post Button
        postButton = new JButton("Post Review");
        postButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        postButton.setBackground(new Color(0x22, 0x43, 0x64));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorder(new EmptyBorder(10, 18, 10, 18));
        postButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        postButton.setEnabled(isLoggedIn);

        postButton.addActionListener(e -> postReview());

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.weighty = 0;
        reviewBox.add(postButton, gbc);

        mainPanel.add(reviewBox);
        mainPanel.add(Box.createVerticalStrut(25));

        // ====== “What Others Say” Section ======
        JLabel othersTitle = new JLabel("What Others Say");
        othersTitle.setFont(new Font("Poppins", Font.BOLD, 20));
        othersTitle.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(0x46, 0x6B, 0x8F)));
        othersTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(othersTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        reviewsContainer = new JPanel();
        reviewsContainer.setLayout(new BoxLayout(reviewsContainer, BoxLayout.Y_AXIS));
        reviewsContainer.setBackground(Color.WHITE);
        reviewsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        addReview("★★★★☆", "Sarah J.", "Booking was smooth and the car was spotless! Highly recommend Veloce.");
        addReview("★★★★★", "Ravi K.", "Affordable rates and on-time delivery. Great experience overall.");
        addReview("★★★★☆", "Emily R.", "Customer service was very helpful during pickup. Will rent again!");

        mainPanel.add(reviewsContainer);
        mainPanel.add(Box.createVerticalGlue());

        // ====== Footer ======
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(0x22, 0x43, 0x64));
        footerPanel.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel footerLabel = new JLabel("© 2025 Veloce Car Rental. All Rights Reserved.", SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        rootPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void addReview(String stars, String author, String text) {
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
        reviewPanel.setBackground(Color.WHITE);
        reviewPanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xDD, 0xDD, 0xDD)));

        JLabel starsLabel = new JLabel(stars);
        starsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        starsLabel.setForeground(new Color(0x46, 0x6B, 0x8F));

        JLabel authorLabel = new JLabel(author);
        authorLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        authorLabel.setForeground(new Color(0x22, 0x43, 0x64));

        JTextArea reviewText = new JTextArea(text);
        reviewText.setFont(new Font("Poppins", Font.PLAIN, 14));
        reviewText.setEditable(false);
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setOpaque(false);
        reviewText.setBorder(null);

        reviewPanel.add(starsLabel);
        reviewPanel.add(authorLabel);
        reviewPanel.add(reviewText);
        reviewPanel.add(Box.createVerticalStrut(10));

        reviewsContainer.add(reviewPanel, 0);
        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }

    private void highlightStars(int count) {
        for (int i = 0; i < starLabels.size(); i++) {
            starLabels.get(i).setForeground(i < count ? new Color(0x46, 0x6B, 0x8F) : new Color(0xCC, 0xCC, 0xCC));
        }
    }

    private void postReview() {
        String text = reviewTextArea.getText().trim();
        if (text.isEmpty() || selectedRating == 0) return;

        StringBuilder starString = new StringBuilder();
        for (int i = 0; i < selectedRating; i++) starString.append("★");
        for (int i = selectedRating; i < 5; i++) starString.append("☆");

        addReview(starString.toString(), "You", text);
        reviewTextArea.setText("");
        selectedRating = 0;
        highlightStars(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new CustomerReviewsResponsive().setVisible(true);
        });
    }
}
