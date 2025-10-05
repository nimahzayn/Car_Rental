import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Standalone Java Swing version of the "FAQs | Veloce Car Rental" page.
 * Includes interactive expand/collapse FAQ sections.
 */
public class FAQPage extends JFrame {

    public FAQPage() {
        setTitle("FAQs | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // === COLORS ===
        Color primary = new Color(0x224364);
        Color accent = new Color(0x466B8F);
        Color textDark = new Color(0x333333);
        Color textSoft = new Color(0x555555);
        Color bgLight = new Color(0xF9F9F9);
        Color white = Color.WHITE;

        // === FONTS ===
        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        Font subtitleFont = new Font("SansSerif", Font.PLAIN, 14);
        Font questionFont = new Font("SansSerif", Font.BOLD, 16);
        Font answerFont = new Font("SansSerif", Font.PLAIN, 14);

        // === MAIN PANEL ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgLight);

        // === HEADER ===
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(white);
        header.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primary, 2, true),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        JLabel title = new JLabel("Frequently Asked Questions");
        title.setFont(titleFont);
        title.setForeground(primary);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Quick answers to your most common queries");
        subtitle.setFont(subtitleFont);
        subtitle.setForeground(textSoft);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        // === FAQ SECTION ===
        JPanel faqContainer = new JPanel();
        faqContainer.setLayout(new BoxLayout(faqContainer, BoxLayout.Y_AXIS));
        faqContainer.setBackground(white);
        faqContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        // Helper method to make FAQ panels
        java.util.function.BiFunction<String, String, JPanel> makeFAQ = (question, answer) -> {
            JPanel faqPanel = new JPanel();
            faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
            faqPanel.setBackground(white);
            faqPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xDDDDDD)));
            faqPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel qLabel = new JLabel(question);
            qLabel.setFont(questionFont);
            qLabel.setForeground(primary);
            qLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea aLabel = new JTextArea(answer);
            aLabel.setFont(answerFont);
            aLabel.setForeground(textSoft);
            aLabel.setBackground(white);
            aLabel.setLineWrap(true);
            aLabel.setWrapStyleWord(true);
            aLabel.setEditable(false);
            aLabel.setVisible(false);
            aLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            aLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 0));

            // Toggle show/hide on click
            faqPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    aLabel.setVisible(!aLabel.isVisible());
                    faqPanel.revalidate();
                    faqPanel.repaint();
                }
            });

            faqPanel.add(Box.createVerticalStrut(8));
            faqPanel.add(qLabel);
            faqPanel.add(aLabel);
            faqPanel.add(Box.createVerticalStrut(8));
            return faqPanel;
        };

        // Add all FAQs
        faqContainer.add(makeFAQ.apply(
                "How do I book a car with Veloce?",
                "Simply log in, choose your pickup and drop-off locations, select dates, and click “Let’s Drive.” You’ll see all available cars instantly."
        ));
        faqContainer.add(makeFAQ.apply(
                "Do I need a driver’s license to rent a car?",
                "Yes, a valid driver’s license is mandatory at the time of pickup. You may also need to present an ID for verification."
        ));
        faqContainer.add(makeFAQ.apply(
                "What payment methods are accepted?",
                "We accept all major credit and debit cards through secure payment gateways."
        ));
        faqContainer.add(makeFAQ.apply(
                "Can I cancel or modify my booking?",
                "Yes, you can manage your bookings through the “My Cars” section. Cancellations made 24 hours prior to pickup are free of charge."
        ));
        faqContainer.add(makeFAQ.apply(
                "Is there a deposit required?",
                "In most cases, yes. A small refundable deposit may be required to ensure vehicle safety."
        ));
        faqContainer.add(makeFAQ.apply(
                "What happens if my rental period ends?",
                "You’ll receive a system notification prompting you to return the car to the drop-off location. Our staff will mark it as “Returned.”"
        ));

        JScrollPane scrollPane = new JScrollPane(faqContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // === FOOTER ===
        JPanel footer = new JPanel();
        footer.setBackground(primary);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel footerLabel = new JLabel("© 2025 Veloce Car Rental. All Rights Reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footer.add(footerLabel);

        // === FINAL STRUCTURE ===
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FAQPage().setVisible(true));
    }
}

