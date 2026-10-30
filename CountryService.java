package cva;

import java.util.ArrayList;
import java.util.List;

import exercise3.RestCountriesWrapper;

/*
This class grabs and processes the raw country data from the API and turns it into class objects.
It does this by converting JSON data into strings and storing them in a list.
 */
public class CountryService {

    // A wrapper used to communicate with the REST Countries API.
    private RestCountriesWrapper rcw;

    // List storing all country objects.
    public List<Country> countries;

    // The constructor starts the connection with the API and loads the country data.
    public CountryService() {
        this.rcw = new RestCountriesWrapper();

        // Prints a message to the console if loading countries fails.
        if (!loadCountries()) {
            System.out.println("Failed to load countries.");
        }
    }

    /*
    Loads the country data from the API and converts it into Country class objects.
    The function returns false if an error occurs and prints the corresponding error to the console.
     */
    private boolean loadCountries() {
        try {
            // Request the countries along with the following selected fields from the API.
            String data = rcw.getAllCountries("name,capital,population,continents,area,landlocked,unMember,region,subregion,flags");

            String[] items = data.substring(1, data.length() - 1).split("\\},\\{");
            countries = new ArrayList<>();

            for (String item : items) {
                if (!item.startsWith("{")) item = "{" + item;
                if (!item.endsWith("}")) item = item + "}";

                int idx; // Indexer.

                /*
                The following code blocks extract the fields' information from the JSON
                and converts it to strings which are used to create our country objects.
                 */

                String commonName = "";
                idx = item.indexOf("\"common\":\"");
                if (idx != -1) {
                    int start = idx + "\"common\":\"".length();
                    int end = item.indexOf("\"", start);
                    commonName = item.substring(start, end);
                }

                String capital = "";
                idx = item.indexOf("\"capital\":[\"");
                if (idx != -1) {
                    int start = idx + "\"capital\":[\"".length();
                    int end = item.indexOf("\"", start);
                    capital = item.substring(start, end);
                }

                long population = 0;
                idx = item.indexOf("\"population\":");
                if (idx != -1) {
                    int start = idx + "\"population\":".length();
                    int end = item.indexOf(",", start);
                    if (end == -1) end = item.indexOf("}", start);
                    population = Long.parseLong(item.substring(start, end).trim());
                }

                double area = 0;
                idx = item.indexOf("\"area\":");
                if (idx != -1) {
                    int start = idx + "\"area\":".length();
                    int end = item.indexOf(",", start);
                    if (end == -1) end = item.indexOf("}", start);
                    area = Double.parseDouble(item.substring(start, end).trim());
                }

                boolean landlocked = false;
                idx = item.indexOf("\"landlocked\":");
                if (idx != -1) {
                    int start = idx + "\"landlocked\":".length();
                    int end = item.indexOf(",", start);
                    if (end == -1) end = item.indexOf("}", start);
                    landlocked = Boolean.parseBoolean(item.substring(start, end).trim());
                }

                String continent = "";
                idx = item.indexOf("\"continents\":[\"");
                if (idx != -1) {
                    int start = idx + "\"continents\":[\"".length();
                    int end = item.indexOf("\"", start);
                    continent = item.substring(start, end);
                }

                boolean unMember = false;
                idx = item.indexOf("\"unMember\":");
                if (idx != -1) {
                    int start = idx + "\"unMember\":".length();
                    int end = item.indexOf(",", start);
                    if (end == -1) end = item.indexOf("}", start);
                    unMember = Boolean.parseBoolean(item.substring(start, end).trim());
                }

                String region = "";
                idx = item.indexOf("\"region\":\"");
                if (idx != -1) {
                    int start = idx + "\"region\":\"".length();
                    int end = item.indexOf("\"", start);
                    region = item.substring(start, end);
                }

                String subregion = "";
                idx = item.indexOf("\"subregion\":\"");
                if (idx != -1) {
                    int start = idx + "\"subregion\":\"".length();
                    int end = item.indexOf("\"", start);
                    subregion = item.substring(start, end);
                }

                String flag = "";
                idx = item.indexOf("\"png\":\"");
                if (idx != -1) {
                    int start = idx + "\"png\":\"".length();
                    int end = item.indexOf("\"", start);
                    flag = item.substring(start, end);
                }

                // Creates a country with the parameters and adds it to the list of countries.
                countries.add(new Country(commonName, capital, population, continent, area, landlocked, unMember, region, subregion, flag));
            }

        }
        catch (Exception e) {
            // Error output.
            e.printStackTrace();
            return false;
        }

        return true;
    }
}