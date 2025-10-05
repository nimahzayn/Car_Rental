import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ContactUsPage extends JFrame {

    public ContactUsPage() {
        setTitle("Contact Us | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        Color primary = new Color(0x224364);
        Color textSoft = new Color(0x555555);
        Color bgLight = new Color(0xF9F9F9);
        Color white = Color.WHITE;

        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        Font bodyFont = new Font("SansSerif", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgLight);

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(white);
        header.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primary, 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Contact Us");
        title.setFont(titleFont);
        title.setForeground(primary);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("We’re here to help you drive smarter");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(textSoft);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        // CONTENT
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(white);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        JTextArea info = new JTextArea(
                "Have a question or need support?\n\n"
                        + "📧 Email: support@veloce.com\n"
                        + "📞 Phone: +91 98765 43210\n"
                        + "🏢 Address: Veloce Car Rental, Kochi, Kerala, India\n\n"
                        + "Our customer care team is available 24/7 to assist with your bookings, "
                        + "payment queries, and rental support."
        );
        info.setFont(bodyFont);
        info.setForeground(textSoft);
        info.setBackground(white);
        info.setEditable(false);
        info.setFocusable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);

        content.add(info);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // FOOTER
        JPanel footer = new JPanel();
        footer.setBackground(primary);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel footerLabel = new JLabel("© 2025 Veloce Car Rental. All Rights Reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footer.add(footerLabel);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactUsPage().setVisible(true));
    }
}

