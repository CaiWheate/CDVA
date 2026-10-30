package cva;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
This window panel is used to display detailed information about a specific country chosen by the user.
It attempts to load an image URL and is armoured with necessary error handling.
 */
public class CountryDetailsWindow extends JFrame {

    // This constructor builds a window to show the details of the user-selected country.
    public CountryDetailsWindow(Country c) {
        super(c.getCommonName() + " Details");

        setLayout(new BorderLayout());

        // The variable below "information" is a text area used for displaying the relevant country information.
        JTextArea information = new JTextArea();
        information.setEditable(false);

        // Adding the country information to the text area.
        information.append("Name: " + c.getCommonName() + "\n");
        information.append("Capital: " + c.getCapital() + "\n");
        information.append("Population: " + c.getPopulation() + "\n");
        information.append("Continent: " + c.getContinent() + "\n");
        information.append("Area: " + c.getArea() + "\n");
        information.append("Landlocked: " + c.getLandlocked() + "\n");
        information.append("UN Member: " + c.getUnMember() + "\n");
        information.append("Region: " + c.getRegion() + "\n");
        information.append("Subregion: " + c.getSubregion() + "\n");

        // This is the label used to display the image of the country's flag.
        JLabel flagLabel;

        try {
            URL url = new URL(c.getFlag());
            ImageIcon icon = new ImageIcon(url);

            // Consistent image scaling.
            Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            flagLabel = new JLabel(new ImageIcon(img));
        }
        catch (Exception e) {
            // Fallback text in case the image fails to load.
            flagLabel = new JLabel("Flag not available");
        }

        flagLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(flagLabel, BorderLayout.NORTH);
        add(new JScrollPane(information), BorderLayout.CENTER);

        // Window settings.
        setSize(400, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}