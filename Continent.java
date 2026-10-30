package cva;

/*
Another type of geographical region - a continent.
This has no functionality; its mainly to demonstrate inheritance.
 */
public class Continent extends GeographicalRegion {

    // Constructor passes the name to the parent class.
    public Continent(String name) {
        super(name);
    }
}