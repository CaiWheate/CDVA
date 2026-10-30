package cva;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/*
After generating a list of countries the user can save the list as a nicely formatted text file.
It contains the "common" names of each country and other relevant information contained in columns.
The order of the countries list will depend on which filter is selected in the main menu at the time.
 */

public class FileManager {

    // To ensure neat formatting, I measured the maximum characters used by each piece of information.
    private static int nameWidth = 45;
    private static int capitalWidth = 25;
    private static int populationWidth = 10;
    private static int continentWidth = 13;
    private static int areaWidth = 11;
    private static int regionWidth = 25;
    private static int subregionWidth = 25;
    private static int booleanWidth = 5;
    private static int urlWidth = 130;
    // The total width is used to for the long horizontal separator line at the top of the text file.
    private static int totalWidth = nameWidth + capitalWidth + populationWidth + continentWidth + areaWidth + regionWidth + subregionWidth + (2 * booleanWidth);

    public static void saveCountriesToFile(File file, List<Country> countries) throws Exception {

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("PLEASE ZOOM OUT IN YOUR TEXT EDITOR!\n");

            // The header of the file which contains column names.
            writer.write(String.format(
                    "%-" + 45 + "s | %-" + 25 + "s | %" + 10 + "s | %-" + 13 + "s | %" + 11 + "s | %-" + 25 + "s | %-" + 25 + "s | %-" + 5 + "s | %-" + 5 + "s | %-" + 130 + "s%n",
                    "Name", "Capital", "Population", "Continent", "Area",
                    "Region", "Subregion", "Landlocked", "UN", "Flag URL"
            ));

            // The separator line between the header and the rows of data.
            writer.write("-".repeat(totalWidth) + "\n");

            // Loop that generates the rows of data.
            for (Country c : countries) {

                writer.write(String.format("%-" + nameWidth
                                + "s | %-" + capitalWidth
                                + "s | %" + populationWidth
                                + "d | %-" + continentWidth
                                + "s | %" + areaWidth
                                + ".2f | %-" + regionWidth
                                + "s | %-"  + subregionWidth
                                + "s | %-" + booleanWidth
                                + "s | %-" + booleanWidth
                                + "s | %-" + urlWidth + "s%n",

                        trim(c.getCommonName(), nameWidth),
                        trim(c.getCapital(), capitalWidth),
                        c.getPopulation(),
                        trim(c.getContinent(), continentWidth),
                        c.getArea(),
                        trim(c.getRegion(), regionWidth),
                        trim(c.getSubregion(), subregionWidth),
                        String.valueOf(c.getLandlocked()),
                        String.valueOf(c.getUnMember()),
                        trim(c.getFlag(), urlWidth)
                ));
            }
        }
    }

    private static String trim(String value, int max) {

        // If a string is null, use an empty character.
        if (value == null) {
            return "";
        }

        // Return value as it is, if it follows the length constraint.
        if (value.length() <= max) {
            return value;
        }
        else {
            // If the value is too long the program cuts it and adds some elipses.
            String shortened = value.substring(0, max - 3);
            return shortened + "...";
        }
    }
}