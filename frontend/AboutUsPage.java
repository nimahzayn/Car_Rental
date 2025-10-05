import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Elegant modern "About Us" page for Veloce Car Rental.
 * Displays team members with circular images (or initials if missing),
 * short intro text, and clean layout.
 */
public class AboutUsPage extends JFrame {

    public AboutUsPage() {
        setTitle("About Us | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);

        // === COLORS & FONTS ===
        Color primary = new Color(0x224364);
        Color accent = new Color(0x466B8F);
        Color bgLight = new Color(0xF9F9F9);
        Color textDark = new Color(0x333333);
        Color textSoft = new Color(0x555555);
        Color white = Color.WHITE;

        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        Font subtitleFont = new Font("SansSerif", Font.PLAIN, 14);
        Font nameFont = new Font("SansSerif", Font.BOLD, 14);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgLight);

        // === HEADER ===
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(white);
        header.setBorder(new CompoundBorder(
                new LineBorder(primary, 2, true),
                new EmptyBorder(20, 30, 20, 30)
        ));

        JLabel title = new JLabel("About Us");
        title.setFont(titleFont);
        title.setForeground(primary);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Meet the minds behind Veloce Car Rental");
        subtitle.setFont(subtitleFont);
        subtitle.setForeground(textSoft);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

        // === CONTENT ===
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(white);
        content.setBorder(new EmptyBorder(30, 50, 50, 50));

        JTextArea intro = new JTextArea(
                "At Veloce Car Rental, we believe in redefining mobility with simplicity, "
                        + "trust, and innovation. Our passionate team ensures seamless car booking "
                        + "experiences that put customers first — every time."
        );
        intro.setFont(subtitleFont);
        intro.setForeground(textSoft);
        intro.setBackground(white);
        intro.setWrapStyleWord(true);
        intro.setLineWrap(true);
        intro.setEditable(false);
        intro.setFocusable(false);
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        intro.setBorder(new EmptyBorder(0, 10, 25, 10));

        // === TEAM GRID ===
        JPanel teamPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        teamPanel.setBackground(white);

        String[][] members = {
                {"Devika V", "images/devika.jpg"},
                {"Meera Lakshman Rao", "images/meera.jpg"},
                {"Nimah Zayn", "images/nimah.jpg"},
                {"Rebecca Lisa Thomas", "images/rebecca.jpg"},
                {"Archana U.S", "images/archana.jpg"},
                {"Sherin Rajan Reumah", "images/sherin.jpg"}
        };

        for (String[] member : members) {
            teamPanel.add(createMemberCard(member[0], member[1], nameFont, textSoft, primary));
        }

        content.add(intro);
        content.add(teamPanel);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // === FOOTER ===
        JPanel footer = new JPanel();
        footer.setBackground(primary);
        footer.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel footerLabel = new JLabel("© 2025 Veloce Car Rental. All Rights Reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footer.add(footerLabel);

        // === STRUCTURE ===
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /** Creates a circular-profile team card without rectangle frames */
    private JPanel createMemberCard(String name, String imagePath, Font nameFont, Color textSoft, Color primary) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(90, 90));
        imageLabel.setMaximumSize(new Dimension(90, 90));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaled = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);

            // Create circular image
            BufferedImage circular = new BufferedImage(90, 90, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circular.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, 90, 90));
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            imageLabel.setIcon(new ImageIcon(circular));
        } catch (Exception e) {
            // fallback: circular initials
            imageLabel.setText(name.substring(0, 1).toUpperCase());
            imageLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            imageLabel.setForeground(Color.WHITE);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            imageLabel.setOpaque(false);

            imageLabel.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(primary);
                    g2.fillOval(0, 0, c.getWidth(), c.getHeight());
                    g2.setColor(Color.WHITE);
                    FontMetrics fm = g2.getFontMetrics(imageLabel.getFont());
                    int x = (c.getWidth() - fm.stringWidth(imageLabel.getText())) / 2;
                    int y = (c.getHeight() + fm.getAscent()) / 2 - 4;
                    g2.drawString(imageLabel.getText(), x, y);
                    g2.dispose();
                }
            });
        }

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(nameFont);
        nameLabel.setForeground(textSoft);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        card.add(imageLabel);
        card.add(nameLabel);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AboutUsPage aboutUs = new AboutUsPage();
            aboutUs.setVisible(true);
        });
    }
}
