package cva;

import java.util.List;
import java.util.ArrayList;

public class CountryRepository {
    /*
    Filters the full list of the world's countries based on search criteria.
    Each parameter below serves as a completely optional filter.
    */
    public List<Country> filterCountries(
            List<Country> countries,
            String commonNameBeginsWith,
            String capitalBeginsWith,
            long minPopulation,
            long maxPopulation,
            String continentBeginsWith,
            double minArea,
            double maxArea,
            Boolean landlocked,
            Boolean unMember,
            String regionBeginsWith,
            String subregionBeginsWith
    ) {
        List<Country> result = new ArrayList<>();

        // Loop that checks if all countries match the user-defined search criteria.
        for (Country c : countries) {
            boolean matchesCriteria;

            // Filter countries based on what the user has started to type.
            if (commonNameBeginsWith != null && !c.getCommonName().toLowerCase().startsWith(commonNameBeginsWith.toLowerCase())) {
                matchesCriteria = false;
            }
            else if (capitalBeginsWith != null && !c.getCapital().toLowerCase().startsWith(capitalBeginsWith.toLowerCase())) {
                matchesCriteria = false;
            }
            else if (continentBeginsWith != null && !c.getContinent().toLowerCase().startsWith(continentBeginsWith.toLowerCase())) {
                matchesCriteria = false;
            }
            else if (regionBeginsWith != null && !c.getRegion().toLowerCase().startsWith(regionBeginsWith.toLowerCase())) {
                matchesCriteria = false;
            }
            else if (subregionBeginsWith != null && !c.getSubregion().toLowerCase().startsWith(subregionBeginsWith.toLowerCase())) {
                matchesCriteria = false;
            }

            // Filter countries based on the minimum/maximum population and area.
            else if (minPopulation != -1 && c.getPopulation() < minPopulation) {
                matchesCriteria = false;
            }
            else if (maxPopulation != -1 && c.getPopulation() > maxPopulation) {
                matchesCriteria = false;
            }
            else if (minArea != -1 && c.getArea() < minArea) {
                matchesCriteria = false;
            }
            else if (maxArea != -1 && c.getArea() > maxArea) {
                matchesCriteria = false;
            }

            // Filter countries based on whether they are landlocked or a United Nations member.
            else if (landlocked != null && c.getLandlocked() != landlocked) {
                matchesCriteria = false;
            }
            else if (unMember != null && c.getUnMember() != unMember) {
                matchesCriteria = false;
            }

            // If none of the previous conditions fail, then the given country in the loop must satisfy the criteria.
            else {
                matchesCriteria = true;
            }

            // If the country matches the criteria, add it to the arraylist.
            if (matchesCriteria) {
                result.add(c);
            }
        }

        // Returns the arraylist result.
        return result;
    }
}