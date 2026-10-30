package cva;

public class GeographicalRegion {

    // Shared by all child classes.
    protected String name;

    /*
    This constructor sets the name of the region.
    It is called by all of the child classes.
     */
    public GeographicalRegion(String name) {
        this.name = name;
    }

    // Returns the name of the geographical region.
    public String getName() {
        return name;
    }

    /*
    I included this override to stop the object name being printed out, resulting in:
    e.g. exercise3.GeographicalRegion@6f2vb58e
    Instead, this returns the actual string name of the region (not the class name combined with its memory address).
     */
    @Override
    public String toString() {
        return name;
    }
}