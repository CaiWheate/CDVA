package cva;

// A smaller geographical region.
public class Town extends GeographicalRegion {

    private long population;

    // This constructor passes the town name to the parent class and stores the population value.
    public Town(String name, long population) {
        super(name);
        this.population = population;
    }

    // Returns the population count of the town.
    public long getPopulation() {
        return population;
    }
}