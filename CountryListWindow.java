package cva;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountryListWindow extends JFrame {

    public CountryListWindow(List<Country> countries) {
        super("Select a Country");

        setLayout(new BorderLayout());

        // Sort alphabetically
        List<Country> sorted = new ArrayList<>(countries);
        sorted.sort(Comparator.comparing(Country::getCommonName));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 10, 5, 5)); // 4 columns

        for (Country c : sorted) {
            JButton button = new JButton(c.getCommonName());

            // Optional: make buttons a consistent size
            button.setPreferredSize(new Dimension(120, 30));

            button.addActionListener(e -> {
                new CountryDetailsWindow(c);
            });

            panel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(panel);

        // Improve scrolling speed (nice UX touch)
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        add(scrollPane, BorderLayout.CENTER);

        pack();
        setSize(getWidth(), getHeight());

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}