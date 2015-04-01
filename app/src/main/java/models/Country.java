package models;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by modsoussi on 3/31/2015.
 */
public class Country implements Comparable<Country> {

    private String name;
    private int rank;
    private String landmass;
    private NumberFormat formatter = new DecimalFormat("#.#########E0");

    public Country(String name, int rank, float landmass){
        this.name = name;
        this.rank = rank;
        this.landmass = formatter.format(landmass);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getLandmass() {
        return landmass;
    }

    public void setLandmass(int landmass) {
        this.landmass = formatter.format(landmass);
    }

    public String toString(){
        return rank + " " + name + " " + landmass;
    }

    @Override
    public int compareTo(Country another) {
        return this.name.compareTo(another.getName());
    }
}
