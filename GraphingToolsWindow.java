package cva;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
Provides a GUI for visualising the country data in different graph formats.
The user can add and remove countries from a list before representing the data in a chart or graph.
 */
public class GraphingToolsWindow extends JFrame {

    // Drop-down boxes for selecting the graph type and data option.
    private JComboBox<String> chartTypeCombo;
    private JComboBox<String> chartOptionCombo;

    // Lists representing the available countries.
    private JList<Country> availableList;
    private DefaultListModel<Country> availableModel;
    // Lists representing the selected countries.
    private JList<Country> selectedList;
    private DefaultListModel<Country> selectedModel;

    // The panel for graph drawing.
    private GraphPanel graphPanel;

    public GraphingToolsWindow(List<Country> countries) {
        super("Advanced Graphing Tools");

        setLayout(new BorderLayout());

        // Initialize list models.
        availableModel = new DefaultListModel<>();
        selectedModel = new DefaultListModel<>();

        // Add all countries to the available list model.
        for (Country c : countries) {
            availableModel.addElement(c);
        }

        availableList = new JList<>(availableModel);
        selectedList = new JList<>(selectedModel);

        availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        availableList.setVisibleRowCount(12);
        selectedList.setVisibleRowCount(12);

        // This is for selecting the chart type.
        chartTypeCombo = new JComboBox<>(new String[]{
                "Pie Chart",
                "Bar Chart",
                "Scatter Graph",
                "Top 10 Ranking"
        });

        chartOptionCombo = new JComboBox<>();
        updateChartOptions("Pie Chart");

        /*
        We need to update the data options when we change the type of chart.
        (Data options are not consistent between different chart types).
        */
        chartTypeCombo.addActionListener(e ->
                updateChartOptions((String) chartTypeCombo.getSelectedItem())
        );

        // Add and Remove buttons.
        JButton selectAllBtn = new JButton("++");
        JButton addBtn = new JButton("+");
        JButton removeBtn = new JButton("-");
        JButton deselectAllBtn = new JButton("--");

        // Button dimensions.
        Dimension btnSize = new Dimension(55, 28);
        selectAllBtn.setPreferredSize(btnSize);
        addBtn.setPreferredSize(btnSize);
        removeBtn.setPreferredSize(btnSize);
        deselectAllBtn.setPreferredSize(btnSize);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonRow.add(selectAllBtn);
        buttonRow.add(addBtn);
        buttonRow.add(removeBtn);
        buttonRow.add(deselectAllBtn);

        // Add all countries to the selected list.
        selectAllBtn.addActionListener(e -> {
            for (int i = 0; i < availableModel.size(); i++) {
                Country c = availableModel.get(i);
                if (!selectedModel.contains(c)) {
                    selectedModel.addElement(c);
                }
            }
        });
        // Add the country to the selected list.
        addBtn.addActionListener(e -> {
            Country c = availableList.getSelectedValue();
            if (c != null && !selectedModel.contains(c)) {
                selectedModel.addElement(c);
            }
        });
        // Remove the country from the selected list.
        removeBtn.addActionListener(e -> {
            Country c = selectedList.getSelectedValue();
            if (c != null) {
                selectedModel.removeElement(c);
            }
        });
        // Remove all countries from the selected list.
        deselectAllBtn.addActionListener(e -> {
            selectedModel.clear();
        });

        // The panel on the left with the graph and chart controls.
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));

        JScrollPane availableScroll = new JScrollPane(availableList);
        JScrollPane selectedScroll = new JScrollPane(selectedList);

        availableScroll.setPreferredSize(new Dimension(260, 200));
        selectedScroll.setPreferredSize(new Dimension(260, 200));

        JPanel listsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        listsPanel.add(availableScroll);
        listsPanel.add(selectedScroll);

        leftPanel.add(listsPanel, BorderLayout.CENTER);
        leftPanel.add(buttonRow, BorderLayout.SOUTH);

        leftPanel.setPreferredSize(new Dimension(280, 0));

        // Top panel containing controls.
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Chart:"));
        topPanel.add(chartTypeCombo);

        topPanel.add(new JLabel("Option:"));
        topPanel.add(chartOptionCombo);

        JButton generate = new JButton("Generate");
        topPanel.add(generate);

        // Graph and chart panel.
        graphPanel = new GraphPanel();

        generate.addActionListener(e -> {
            List<Country> selected = new ArrayList<>();

            // Converting JList to a normal list.
            for (int i = 0; i < selectedModel.size(); i++) {
                selected.add(selectedModel.get(i));
            }

            // Make sure at least two countries are selected for comparison.
            if (selected.size() < 2) {
                JOptionPane.showMessageDialog(this, "Select at least 2 countries");
                return;
            }

            // Pass data to the graph and chart panel.
            graphPanel.setChartData(
                    selected,
                    (String) chartTypeCombo.getSelectedItem(),
                    (String) chartOptionCombo.getSelectedItem()
            );

            // Once this is done, refresh the display.
            graphPanel.repaint();
        });

        // Frame components.
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);

        // Window configuration.
        setSize(1050, 550);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // This function updates the available data options depending on the graph/chart type.
    private void updateChartOptions(String chartType) {
        chartOptionCombo.removeAllItems();

        switch (chartType) {

            // Data options are identical for pie and bar charts.
            case "Pie Chart":
            case "Bar Chart":
                chartOptionCombo.addItem("Population");
                chartOptionCombo.addItem("Area");
                chartOptionCombo.addItem("Continent Distribution");
                break;

            // Scatter graphs will be useful for comparing two variables.
            case "Scatter Graph":
                chartOptionCombo.addItem("Population vs Area");
                chartOptionCombo.addItem("Density vs Area");
                break;

            // Shows top 10 values.
            case "Top 10 Ranking":
                chartOptionCombo.addItem("Largest Population");
                chartOptionCombo.addItem("Largest Area");
                break;
        }
    }
}