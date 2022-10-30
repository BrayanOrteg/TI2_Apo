package model;

public class City {

    String id, name, countryId;
    int population;

    public City(String id, String name, String countryId, int population) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.population = population;
    }

    public double compareOperator(int var, String o2){

        String [] variables={id,name,countryId,population+""};
        String o1=variables[var];

        if(var==3){
            double p= Double.parseDouble(o2);
            return population-p;
        }

        return o1.compareTo(o2);
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String toString (){
        return name;
    }
}
