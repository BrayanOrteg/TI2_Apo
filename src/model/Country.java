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

    public double compareOperator(int var,String o2){

        String [] variables={id,name,countryCode,population+""};
        String o1=variables[var];

        if(var==3){
            double p= Double.parseDouble(o2);
            return population-p;
        }

        return o1.compareTo(o2);
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
