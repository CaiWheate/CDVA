package cva;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// This class is responsible for rendering multiple types of graphs representing country data and statistics.
public class GraphPanel extends JPanel {

    // This data is parsed in from GraphingToolsWindow.
    private List<Country> selectedCountries;
    private String chartType;
    private String chartOption;

    // Data from the UI for drawing.
    public void setChartData(List<Country> selectedCountries, String chartType, String chartOption) {
        this.selectedCountries = selectedCountries;
        this.chartType = chartType;
        this.chartOption = chartOption;
    }

    /*
    paintComponent is called automatically upon a window refresh.
    It renders the selected type of chart or graph.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (selectedCountries == null || selectedCountries.isEmpty()){
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Switch/Case loop to select the correct type of chart or graph.
        switch (chartType) {
            case "Pie Chart":
                drawPie(g2);
                break;
            case "Bar Chart":
                drawBar(g2);
                break;
            case "Scatter Graph":
                drawScatter(g2);
                break;
            case "Top 10 Ranking":
                drawTop10(g2);
                break;
        }
    }

    // Pie chart.
    private void drawPie(Graphics2D g2) {
        Map<String, Double> data = new LinkedHashMap<>();

        // Iterate through selected countries.
        for (Country c : selectedCountries) {
            String key = switch (chartOption) {
                case "Continent Distribution" -> c.getContinent();
                default -> c.getCommonName();
            };

            double value = switch (chartOption) {
                case "Area" -> c.getArea();
                default -> c.getPopulation();
            };

            if (value > 0) {
                data.put(key, data.getOrDefault(key, 0.0) + value);
            }
        }

        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total == 0) return;

        int startAngle = 0;
        int i = 0;

        int size = Math.min(getWidth(), getHeight()) - 120;

        Color[] colors = {
                Color.RED, Color.BLUE, Color.GREEN,
                Color.ORANGE, Color.MAGENTA, Color.CYAN,
                Color.PINK, Color.YELLOW
        };

        Map<String, Color> legendMap = new LinkedHashMap<>();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int angle = (int) Math.round(entry.getValue() / total * 360);

            Color col = colors[i % colors.length];
            legendMap.put(entry.getKey(), col);

            g2.setColor(col);
            g2.fillArc(30, 30, size, size, startAngle, angle);

            startAngle += angle;
            i++;
        }

        int lx = size + 60;
        int ly = 50;

        i = 0;
        for (Map.Entry<String, Color> entry : legendMap.entrySet()) {
            g2.setColor(entry.getValue());
            g2.fillRect(lx, ly + i * 22, 15, 15);

            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(), lx + 20, ly + 12 + i * 22);

            i++;
        }
    }

    // Bar chart.
    private void drawBar(Graphics2D g2) {
        Map<String, Double> data = new LinkedHashMap<>();

        // Iterate through selected countries.
        for (Country c : selectedCountries) {
            double value;

            switch (chartOption) {
                case "Area":
                    value = c.getArea();
                    break;

                case "Population":
                    value = c.getPopulation();
                    break;

                default:
                    value = c.getPopulation() / c.getArea();
                    break;
            }

            data.put(c.getCommonName(), value);
        }

        double max = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

        int w = getWidth();
        int h = getHeight();

        int barWidth = Math.max(20, w / Math.max(1, data.size()));

        int i = 0;
        for (Map.Entry<String, Double> e : data.entrySet()) {

            int barHeight = (int) (e.getValue() / max * (h - 100));

            int x = i * barWidth + 50;
            int y = h - barHeight - 50;

            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, barWidth - 10, barHeight);

            g2.setColor(Color.BLACK);
            g2.drawString(e.getKey(), x, h - 30);

            i++;
        }
    }

    // Scatter graph.
    private void drawScatter(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();

        double maxX = 1;
        double maxY = 1;

        if ("Population vs Area".equals(chartOption)) {
            maxX = selectedCountries.stream().mapToDouble(Country::getArea).max().orElse(1);
            maxY = selectedCountries.stream().mapToDouble(c -> (double) c.getPopulation()).max().orElse(1);
        }
        else {
            maxX = selectedCountries.stream().mapToDouble(c -> c.getArea() > 0 ? c.getPopulation() / c.getArea() : 0).max().orElse(1);
            maxY = selectedCountries.stream().mapToDouble(Country::getArea).max().orElse(1);
        }

        g2.setColor(Color.RED);

        // Iterate through selected countries.
        for (Country c : selectedCountries) {

            double xVal, yVal;

            if ("Population vs Area".equals(chartOption)) {
                xVal = c.getArea();
                yVal = c.getPopulation();
            }
            else {
                // Here, I actively avoid accidentally dividing by zero.
                if (c.getArea() > 0) {
                    xVal = c.getPopulation() / c.getArea();
                }
                else {
                    xVal = 0;
                }

                yVal = c.getArea();
            }

            int x = (int) (xVal / maxX * (w - 100)) + 50;
            int y = h - (int) (yVal / maxY * (h - 100)) - 50;

            g2.fillOval(x, y, 6, 6);
            g2.drawString(c.getCommonName(), x + 5, y);
        }
    }

    // Top 10 ranking.
    private void drawTop10(Graphics2D g2) {

        List<Country> sorted = new ArrayList<>(selectedCountries);

        Comparator<Country> comparator;

        if ("Largest Area".equals(chartOption)) {
            comparator = Comparator.comparingDouble(Country::getArea).reversed();
        } else {
            comparator = Comparator.comparingLong(Country::getPopulation).reversed();
        }

        sorted.sort(comparator);

        int maxCount = Math.min(10, sorted.size());

        double maxValue;

        if ("Largest Area".equals(chartOption)) {
            maxValue = sorted.get(0).getArea();
        }
        else {
            maxValue = sorted.get(0).getPopulation();
        }

        int y = 60;
        int barHeight = 25;

        // Iterate through selected countries.
        for (int i = 0; i < maxCount; i++) {
            Country c = sorted.get(i);

            double value;

            if ("Largest Area".equals(chartOption)) {
                value = c.getArea();
            }
            else {
                value = c.getPopulation();
            }

            int width = (int) (value / maxValue * 600);

            g2.setColor(Color.ORANGE);
            g2.fillRect(50, y, width, barHeight);

            g2.setColor(Color.BLACK);
            g2.drawString(c.getCommonName(), 50, y - 5);

            y += 40;
        }
    }
}