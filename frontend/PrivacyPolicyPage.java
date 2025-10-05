import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Standalone Swing version of the Veloce Car Rental Privacy Policy page.
 * Includes full styling, layout, and scrollable content.
 */
public class PrivacyPolicyPage extends JFrame {

    public PrivacyPolicyPage() {
        setTitle("Privacy Policy | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // === Colors and Fonts ===
        Color primary = new Color(0x224364);
        Color accent = new Color(0x466B8F);
        Color textDark = new Color(0x333333);
        Color textSoft = new Color(0x555555);
        Color bgLight = new Color(0xF9F9F9);
        Color white = Color.WHITE;

        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        Font subtitleFont = new Font("SansSerif", Font.PLAIN, 14);
        Font sectionFont = new Font("SansSerif", Font.BOLD, 16);
        Font bodyFont = new Font("SansSerif", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgLight);

        // === HEADER ===
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(white);
        header.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primary, 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Privacy Policy");
        title.setFont(titleFont);
        title.setForeground(primary);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your trust matters to us");
        subtitle.setFont(subtitleFont);
        subtitle.setForeground(textSoft);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        // === MAIN CONTENT ===
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(white);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        // helper: section heading
        java.util.function.Function<String, JLabel> makeHeading = text -> {
            JLabel lbl = new JLabel(text);
            lbl.setFont(sectionFont);
            lbl.setForeground(textDark);
            lbl.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, accent));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            return lbl;
        };

        // helper: paragraph
        java.util.function.Function<String, JTextArea> makeParagraph = text -> {
            JTextArea area = new JTextArea(text);
            area.setFont(bodyFont);
            area.setForeground(textSoft);
            area.setBackground(white);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // ✅ Fix cursor/caret issue:
            area.setFocusable(false);  // prevents caret & text selection
            area.setCursor(Cursor.getDefaultCursor());
            return area;
        };

        // helper: bullet list
        java.util.function.Function<String[], JPanel> makeList = items -> {
            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBackground(white);
            listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            for (String item : items) {
                JLabel lbl = new JLabel("• " + item);
                lbl.setFont(bodyFont);
                lbl.setForeground(textSoft);
                listPanel.add(lbl);
                listPanel.add(Box.createVerticalStrut(5));
            }
            return listPanel;
        };

        // === CONTENT BUILD ===
        content.add(makeParagraph.apply("Last Updated: October 2025"));
        content.add(makeParagraph.apply(
                "At Veloce Car Rental, we value your privacy and are committed to safeguarding the personal information you share with us. "
                        + "This policy outlines how we collect, use, and protect your data when you use our services."
        ));

        content.add(makeHeading.apply("1. Information We Collect"));
        content.add(makeList.apply(new String[]{
                "Personal Details: Name, email, contact number, and login credentials.",
                "Booking Information: Pickup and drop-off dates, times, and locations.",
                "Payment Data: Securely processed payment details via trusted gateways.",
                "Usage Data: Device type, IP address, and browsing behavior to improve our services."
        }));

        content.add(makeHeading.apply("2. How We Use Your Information"));
        content.add(makeList.apply(new String[]{
                "To process car bookings and manage your account.",
                "To send booking confirmations and important notifications.",
                "To enhance our website’s performance and user experience.",
                "To comply with legal and security requirements."
        }));

        content.add(makeHeading.apply("3. Data Protection"));
        content.add(makeParagraph.apply(
                "Your information is stored securely in our protected databases. "
                        + "We use encryption, authentication, and access controls to ensure that your data remains safe and confidential."
        ));

        content.add(makeHeading.apply("4. Sharing of Information"));
        content.add(makeParagraph.apply("We do not sell or rent your data. Information is shared only with:"));
        content.add(makeList.apply(new String[]{
                "Authorized service providers (car partners, payment processors).",
                "Government authorities when legally required."
        }));

        content.add(makeHeading.apply("5. Cookies"));
        content.add(makeParagraph.apply(
                "Our website uses cookies to personalize your experience and improve navigation. "
                        + "You can disable cookies through your browser settings if you prefer."
        ));

        content.add(makeHeading.apply("6. Your Rights"));
        content.add(makeParagraph.apply(
                "You may request access, correction, or deletion of your personal data by contacting us. "
                        + "We will respond promptly and transparently."
        ));

        content.add(makeHeading.apply("7. Updates to This Policy"));
        content.add(makeParagraph.apply(
                "We may occasionally update this Privacy Policy. All revisions will be posted on this page with the updated date."
        ));

        content.add(makeHeading.apply("8. Contact Us"));
        content.add(makeParagraph.apply(
                "For any questions or concerns regarding this policy, please reach out to us:\n"
                        + "Email: support@veloce.com\n"
                        + "Address: Veloce Car Rental, Kochi, Kerala, India"
        ));

        JScrollPane scrollPane = new JScrollPane(content);
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
        SwingUtilities.invokeLater(() -> new PrivacyPolicyPage().setVisible(true));
    }
}
