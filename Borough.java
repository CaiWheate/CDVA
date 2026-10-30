import cva;

// An example of a geographical region.
public class Borough extends GeographicalRegion {

    private String country;

    // Constructor here passes the borough name to the parent class and sets the name of the country the borough belongs to.
    public Borough(String name, String country) {
        super(name);
        this.country = country;
    }

    // Returns the country the borough is in.
    public String getCountry() {
        return country;
    }
}