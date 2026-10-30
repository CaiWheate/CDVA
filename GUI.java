package cva;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/*
Graphical user interface.
 */
public class GUI {

    public GUI() {

        JFrame frame = new JFrame("Country App");

        // Text fields that the user uses for filtering countries.
        JTextField nameField = new JTextField(10);
        JTextField capitalField = new JTextField(10);
        JTextField minPopulationField = new JTextField(6);
        JTextField maxPopulationField = new JTextField(6);
        JTextField continentField = new JTextField(10);
        JTextField minAreaField = new JTextField(6);
        JTextField maxAreaField = new JTextField(6);

        // Checkboxes that the user uses for filtering countries.
        JCheckBox landlockedBox = new JCheckBox("Landlocked");
        JCheckBox unMemberBox = new JCheckBox("UN Member");

        // More text fields that the user uses for filtering countries.
        JTextField regionField = new JTextField(10);
        JTextField subregionField = new JTextField(10);

        // Buttons.
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save Results");
        JButton graphButton = new JButton("Graphing Tools");
        JButton detailsButton = new JButton("Country Details");

        // Country output and display area.
        JTextArea output = new JTextArea(20, 40);
        output.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(output);

        // Input panel.
        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 5, 5));

        inputPanel.add(new JLabel("Name starts with:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Capital starts with:"));
        inputPanel.add(capitalField);

        inputPanel.add(new JLabel("Min population:"));
        inputPanel.add(minPopulationField);
        inputPanel.add(new JLabel("Max population:"));
        inputPanel.add(maxPopulationField);

        inputPanel.add(new JLabel("Continent starts with:"));
        inputPanel.add(continentField);
        inputPanel.add(new JLabel("Min area:"));
        inputPanel.add(minAreaField);

        inputPanel.add(new JLabel("Max area:"));
        inputPanel.add(maxAreaField);
        inputPanel.add(landlockedBox);

        inputPanel.add(unMemberBox);
        inputPanel.add(new JLabel("Region starts with:"));
        inputPanel.add(regionField);
        inputPanel.add(new JLabel("Subregion starts with:"));
        inputPanel.add(subregionField);

        // Sorting options.
        JComboBox<String> sortBox = new JComboBox<>(new String[]{
                "Name A-Z",
                "Name Z-A",
                "Population Low → High",
                "Population High → Low",
                "Area Low → High",
                "Area High → Low"
        });

        // This button layout panel splits the "Search" and "Filter" buttons from the rest of the buttons on the main menu.
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftButtons.add(searchButton);
        leftButtons.add(sortBox);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        rightButtons.add(saveButton);
        rightButtons.add(graphButton);
        rightButtons.add(detailsButton);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(leftButtons, BorderLayout.WEST);
        buttonPanel.add(Box.createHorizontalStrut(40), BorderLayout.CENTER);
        buttonPanel.add(rightButtons, BorderLayout.EAST);

        // Filter panel.
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(inputPanel, BorderLayout.CENTER);
        filterPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Country data.
        CountryRepository repository = new CountryRepository();
        CountryService service = new CountryService();

        List<Country> currentList = service.countries;
        output.setText(renderList(currentList));

        // Searching and sorting logic.
        searchButton.addActionListener(e -> {

            List<Country> filtered = repository.filterCountries(
                    service.countries,
                    empty(nameField),
                    empty(capitalField),
                    parseLong(minPopulationField),
                    parseLong(maxPopulationField),
                    empty(continentField),
                    parseDouble(minAreaField),
                    parseDouble(maxAreaField),
                    landlockedBox.isSelected() ? true : null,
                    unMemberBox.isSelected() ? true : null,
                    empty(regionField),
                    empty(subregionField)
            );

            // Apply selected sorting method.
            String sort = (String) sortBox.getSelectedItem();
            filtered = SortUtil.sort(filtered, sort);

            output.setText(renderList(filtered));
        });

        // Save results to a text file using file manager pop-up window.
        saveButton.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();

                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                try {
                    FileManager.saveCountriesToFile(file, service.countries);
                    JOptionPane.showMessageDialog(frame, "Saved successfully!");
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage()); // Error handling.
                }
            }
        });

        // To open other windows.
        graphButton.addActionListener(e ->
                new GraphingToolsWindow(service.countries)
        );

        detailsButton.addActionListener(e ->
                new CountryListWindow(service.countries)
        );

        // Frame configuration.
        frame.setLayout(new BorderLayout());

        frame.add(filterPanel, BorderLayout.NORTH);
        frame.add(outputScroll, BorderLayout.CENTER);

        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /*
    Returns null if the text is empty.
    Otherwise, returns the text.
    */
    private String empty(JTextField field) {
        String text = field.getText().trim();
        return text.isEmpty() ? null : text;
    }

    /*
    Returns -1 if the text is empty.
    Otherwise it parses the field as a long variable.
     */
    private long parseLong(JTextField field) {
        String text = field.getText().trim();
        return text.isEmpty() ? -1 : Long.parseLong(text);
    }

    /*
    Returns -1 if the text is empty.
    Otherwise it parses the field as a double variable.
     */
    private double parseDouble(JTextField field) {
        String text = field.getText().trim();
        return text.isEmpty() ? -1 : Double.parseDouble(text);
    }

    // Converts a list of countries into a string separated by newlines (\n).
    private String renderList(List<Country> list) {
        StringBuilder builder = new StringBuilder();

        for (Country country : list) {
            builder.append(country.getCommonName())
                    .append("\n");
        }

        return builder.toString();
    }
}