package models;

/**
 * Created by modsoussi on 3/31/2015.
 */
public class Country {

    private String name;
    private int rank;
    private int landmass;
    
    public Country(String name, int rank, int landmass){
        this.name = name;
        this.rank = rank;
        this.landmass = landmass;
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

    public int getLandmass() {
        return landmass;
    }

    public void setLandmass(int landmass) {
        this.landmass = landmass;
    }
}
