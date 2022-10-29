package model;

public class Country {

    String id, name, countryCode;
    double population;

    public Country(String id, String name, String countryCode, double population) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.population = population;
    }

    public String toString(){
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
}
