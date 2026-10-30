package cva;

public class Country extends GeographicalRegion {

    // All country fields that get initialized by the constructor function.
    String commonName;
    String capital;
    long population;
    String continent;
    double area;
    boolean landlocked;
    boolean unMember;
    String region;
    String subregion;
    String flag; // This string stores a URL to a PNG image file of the flag.

    /*
    This constructor initializes all of the country fields using cleaned data from the JSON.
    The common name of the country is also of course passed to the parent class (GeographicalRegion).
     */
    public Country(String commonName, String capital, long population,
                   String continent, double area, boolean landlocked,
                   boolean unMember, String region, String subregion,
                   String flag) {
        super(commonName);

        this.commonName = commonName;
        this.capital = capital;
        this.population = population;
        this.continent = continent;
        this.area = area;
        this.landlocked = landlocked;
        this.unMember = unMember;
        this.region = region;
        this.subregion = subregion;
        this.flag = flag;
    }

    // These are my getter methods that return values associated with each country object (e.g. name or population).
    String getCommonName() {
        return this.commonName;
    }
    String getCapital() {
        return this.capital;
    }
    long getPopulation() {
        return this.population;
    }
    String getContinent() {
        return this.continent;
    }
    double getArea() {
        return this.area;
    }
    boolean getLandlocked() {
        return this.landlocked;
    }
    boolean getUnMember() {
        return this.unMember;
    }
    String getRegion() {
        return this.region;
    }
    String getSubregion() {
        return this.subregion;
    }
    String getFlag() {
        return this.flag;
    }

    /*
    Returns the actual string name, rather than the class name combined with a memory address.
     */
    @Override
    public String toString() {
        return commonName;
    }
}