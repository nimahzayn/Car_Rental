import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;

/**
 * Car Search (frontend-only) — Swing conversion of the provided HTML/CSS/JS page.
 *
 * - No backend calls (uses in-memory dummy car data).
 * - Form fields: pickup, pickup time, drop, drop time, car type, pickup date, drop date.
 * - Clicking Search renders matching car cards in a scrollable, wrap-capable panel.
 * - "Book Now" buttons are present but perform no action (per request).
 *
 * Save as CarSearchUI.java, compile & run:
 * javac CarSearchUI.java
 * java CarSearchUI
 */
public class CarSearchUI extends JFrame {

    // UI components
    private JComboBox<String> pickupBox;
    private JComboBox<String> dropBox;
    private JComboBox<String> typeBox;
    private JSpinner pickupTimeSpinner;
    private JSpinner dropTimeSpinner;
    private JSpinner pickupDateSpinner;
    private JSpinner dropDateSpinner;
    private JButton searchButton;
    private JPanel resultsPanel;
    private JScrollPane resultsScroll;

    // Dummy car data
    private final List<Car> cars = Arrays.asList(
            new Car("Toyota", "Fortuner", "SUV", 7, "Automatic", 4500, "Lulu Mall", "Edapally Toll", true),
            new Car("Hyundai", "Creta", "SUV", 5, "Automatic", 2500, "Pallarivattom Metro", "Kalamaserry", true),
            new Car("Honda", "City", "Sedan", 5, "Manual", 2000, "Edapally Toll", "Lulu Mall", false),
            new Car("Maruti", "Ertiga", "Mini Van", 7, "Manual", 2200, "Kalamaserry", "Pallarivattom Metro", true),
            new Car("Tata", "Tiago", "Hatchback", 5, "Manual", 1200, "Lulu Mall", "Kalamaserry", false)
    );

    public CarSearchUI() {
        setTitle("Find a Car | Veloce Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 760);
        setLocationRelativeTo(null);

        // Root layout, similar background and centered content
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(0xF4F4F4));
        setContentPane(root);

        // Top search container - approximates .search-container styling
        JPanel searchContainer = new JPanel();
        searchContainer.setBackground(Color.WHITE);
        searchContainer.setBorder(new CompoundBorder(
                new EmptyBorder(22, 26, 22, 26),
                new CompoundBorder(new LineBorder(new Color(0xDD, 0xDD, 0xDD), 1), new EmptyBorder(8, 8, 8, 8))
        ));
        searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.Y_AXIS));
        searchContainer.setMaximumSize(new Dimension(560, Integer.MAX_VALUE));
        searchContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel("Find a Car");
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));
        heading.setForeground(new Color(0x333333));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchContainer.add(heading);
        searchContainer.add(Box.createRigidArea(new Dimension(0, 18)));

        // Form panel (vertical flow)
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Helper to build labeled components
        form.add(makeFormRow("Pick-up Location:", pickupBox = new JComboBox<>(new String[]{
                "Lulu Mall", "Edapally Toll", "Kalamaserry", "Pallarivattom Metro"
        }), true));

        // Pick-up time spinner
        pickupTimeSpinner = makeTimeSpinner();
        form.add(makeFormRow("Pick-up Time:", pickupTimeSpinner, false));

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        form.add(makeFormRow("Drop Location:", dropBox = new JComboBox<>(new String[]{
                "Lulu Mall", "Edapally Toll", "Kalamaserry", "Pallarivattom Metro"
        }), true));

        dropTimeSpinner = makeTimeSpinner();
        form.add(makeFormRow("Drop-off Time:", dropTimeSpinner, false));

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        typeBox = new JComboBox<>(new String[]{"SUV", "Sedan", "Mini Van", "Hatchback"});
        form.add(makeFormRow("Car Type:", typeBox, true));

        pickupDateSpinner = makeDateSpinner();
        form.add(makeFormRow("Pick-up Date:", pickupDateSpinner, false));

        dropDateSpinner = makeDateSpinner();
        form.add(makeFormRow("Drop-off Date:", dropDateSpinner, false));

        form.add(Box.createRigidArea(new Dimension(0, 10)));

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0x466B8F));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(new EmptyBorder(8, 20, 8, 20));
        searchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> performSearch());
        form.add(searchButton);

        searchContainer.add(form);

        // Put search container centered at top with margins
        JPanel topWrapper = new JPanel();
        topWrapper.setBackground(new Color(0xF4F4F4));
        topWrapper.setLayout(new BoxLayout(topWrapper, BoxLayout.X_AXIS));
        topWrapper.add(Box.createHorizontalGlue());
        topWrapper.add(searchContainer);
        topWrapper.add(Box.createHorizontalGlue());
        topWrapper.setBorder(new EmptyBorder(20, 10, 10, 10));

        root.add(topWrapper, BorderLayout.NORTH);

        // Results area (cars-display) - wrap layout via FlowLayout
        resultsPanel = new JPanel(new WrapLayout(FlowLayout.CENTER, 20, 20));
        resultsPanel.setBackground(new Color(0xF4F4F4));
        resultsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        resultsScroll = new JScrollPane(resultsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultsScroll.getVerticalScrollBar().setUnitIncrement(16);
        resultsScroll.setBorder(null);

        root.add(resultsScroll, BorderLayout.CENTER);

        // initial state: empty results
        renderResults(Collections.emptyList());
    }

    // Helper: creates a labelled row with component
    private JPanel makeFormRow(String labelText, JComponent comp, boolean compactTopMargin) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout(6, 6));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(new Color(0x333333));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(label, BorderLayout.NORTH);
        panel.add(comp, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!compactTopMargin) panel.setBorder(new EmptyBorder(6, 0, 6, 0));
        else panel.setBorder(new EmptyBorder(2, 0, 2, 0));
        return panel;
    }

    private JSpinner makeTimeSpinner() {
        // Spinner with Date model showing only time
        Date now = new Date();
        SpinnerDateModel model = new SpinnerDateModel(now, null, null, Calendar.MINUTE);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm");
        spinner.setEditor(editor);
        return spinner;
    }

    private JSpinner makeDateSpinner() {
        Date today = new Date();
        SpinnerDateModel model = new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    private void performSearch() {
        // Collect filter values (matching HTML's JS gather)
        String pickup = (String) pickupBox.getSelectedItem();
        String drop = (String) dropBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();

        // In this frontend-only version, we'll filter the dummy 'cars' list
        List<Car> filtered = new ArrayList<>();
        for (Car c : cars) {
            boolean matchesType = type == null || type.isEmpty() || c.type.equalsIgnoreCase(type);
            boolean matchesPickup = pickup == null || pickup.isEmpty() || c.pickupLocation.equalsIgnoreCase(pickup);
            boolean matchesDrop = drop == null || drop.isEmpty() || c.dropLocation.equalsIgnoreCase(drop);
            if (matchesType && matchesPickup && matchesDrop) filtered.add(c);
        }

        renderResults(filtered);
    }

    // Render car cards similar to .car-card styling
    private void renderResults(List<Car> results) {
        resultsPanel.removeAll();

        if (results.isEmpty()) {
            JLabel none = new JLabel("No cars found.");
            none.setFont(new Font("SansSerif", Font.PLAIN, 16));
            none.setForeground(new Color(0x555555));
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(none);
        } else {
            for (Car car : results) {
                resultsPanel.add(makeCarCard(car));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    // Create a card JPanel that visually matches the HTML/CSS card
    private JPanel makeCarCard(Car car) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 320));
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(new Color(0xCCCCCC), 1, true),
                new EmptyBorder(10, 10, 10, 10)));

        // Image placeholder (since frontend-only)
        JPanel imgHolder = new JPanel();
        imgHolder.setPreferredSize(new Dimension(280, 140));
        imgHolder.setBackground(new Color(0xEAECEF));
        imgHolder.setLayout(new BorderLayout());
        JLabel imgLabel = new JLabel(car.make + " " + car.model, SwingConstants.CENTER);
        imgLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        imgLabel.setForeground(new Color(0x333333));
        imgHolder.add(imgLabel, BorderLayout.CENTER);
        imgHolder.setBorder(new EmptyBorder(6, 6, 6, 6));
        card.add(imgHolder, BorderLayout.NORTH);

        // Info panel (center)
        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(8, 6, 8, 6));

        JLabel title = new JLabel(car.make + " " + car.model);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(0x222222));
        info.add(title);
        info.add(Box.createRigidArea(new Dimension(0, 6)));

        info.add(makeInfoLabel("Type: " + car.type));
        info.add(makeInfoLabel("Seats: " + car.seats));
        info.add(makeInfoLabel("Transmission: " + car.transmission));
        info.add(makeInfoLabel("Price per day: \u20B9" + car.pricePerDay));
        info.add(makeInfoLabel("Pickup: " + car.pickupLocation));
        info.add(makeInfoLabel("Drop: " + car.dropLocation));

        card.add(info, BorderLayout.CENTER);

        // Button area
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        btnPanel.setBackground(Color.WHITE);
        JButton bookBtn = new JButton(car.available ? "Book Now" : "Unavailable");
        bookBtn.setEnabled(car.available);
        bookBtn.setBackground(new Color(0x466B8F));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);
        bookBtn.setBorder(new EmptyBorder(6, 12, 6, 12));
        // No action required per user instruction
        btnPanel.add(bookBtn);
        card.add(btnPanel, BorderLayout.SOUTH);

        if (!car.available) {
            // Simulate .car-card.unavailable opacity by dimming components
            card.setOpaque(false);
            card.setBackground(new Color(0xF7F7F7));
            card.setBorder(new CompoundBorder(new LineBorder(new Color(0xCCCCCC), 1, true),
                    new EmptyBorder(10, 10, 10, 10)));
            // Dim child labels
            dimPanel(card);
        }

        // Hover effect: slightly raise card on hover (simple border change)
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new CompoundBorder(new LineBorder(new Color(0xBBBBBB), 2, true),
                        new EmptyBorder(10, 10, 10, 10)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(new CompoundBorder(new LineBorder(new Color(0xCCCCCC), 1, true),
                        new EmptyBorder(10, 10, 10, 10)));
            }
        });

        return card;
    }

    private void dimPanel(Container container) {
        for (Component comp : container.getComponents()) {
            comp.setForeground(new Color(0x888888));
            if (comp instanceof Container) dimPanel((Container) comp);
        }
    }

    private JLabel makeInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(0x555555));
        return lbl;
    }

    // Simple data holder for a car
    private static class Car {
        String make, model, type, transmission, pickupLocation, dropLocation;
        int seats;
        int pricePerDay;
        boolean available;

        Car(String make, String model, String type, int seats, String transmission, int pricePerDay,
            String pickupLocation, String dropLocation, boolean available) {
            this.make = make;
            this.model = model;
            this.type = type;
            this.seats = seats;
            this.transmission = transmission;
            this.pricePerDay = pricePerDay;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
            this.available = available;
        }
    }

    // Custom FlowLayout that wraps components (approximate CSS flex-wrap)
    // Source: common WrapLayout implementations adapted for single-file usage.
    private static class WrapLayout extends FlowLayout {
        public WrapLayout() {
            super();
        }

        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getWidth();
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                int nmembers = target.getComponentCount();

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);

                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (rowWidth + d.width > maxWidth) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }

                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }

                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
                addRow(dim, rowWidth, rowHeight);

                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom + vgap * 2;

                // Adjust for scrollpane small widths
                return dim;
            }
        }

        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);
            if (dim.height > 0) dim.height += getVgap();
            dim.height += rowHeight;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarSearchUI ui = new CarSearchUI();
            ui.setVisible(true);
        });
    }
}
