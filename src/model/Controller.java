package model;
import java.security.PrivateKey;
import java.util.*;

import Exceptions.*;
import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Controller{

    private ArrayList<City> cityArray;
    private ArrayList<Country> countryArray;

    private ArrayList<City> orderCityArray;
    private ArrayList<Country> orderCountryArray;

    private ArrayList<String> operators;



    public Controller(){
        countryArray=new ArrayList<>();
        orderCityArray=new ArrayList<>();
        orderCountryArray=new ArrayList<>();
        operators=new ArrayList<>();

        operators.add("<");
        operators.add(">");
        operators.add("=");


        ReadJson();

        System.out.println(Arrays.toString(cityArray.toArray()));
        System.out.println(Arrays.toString(countryArray.toArray()));

    }

// city
    public void sortCityByName(){
        Collections.sort(orderCityArray, Comparator.comparing(City::getName));
    }

    public void sortCityByPopulation(){
        Collections.sort(orderCityArray, Comparator.comparingInt(City::getPopulation));
    }

    public void sortCityById(){
        Collections.sort(orderCityArray, Comparator.comparing(City::getId));
    }

    public void sortCityByCountyId(){
        Collections.sort(orderCityArray, Comparator.comparing(City::getCountryId));
    }

    //country
    public void sortCountryByName(){
        Collections.sort(orderCountryArray, Comparator.comparing(Country::getName));
    }

    public void sortCountryByPopulation(){
        Collections.sort(orderCountryArray, (o1, o2) -> (int)(o1.getPopulation()-o2.getPopulation()));
    }

    public void sortCountryById(){
        Collections.sort(orderCountryArray, Comparator.comparing(Country::getId));
    }

    public void sortCountryByCode(){
        Collections.sort(orderCountryArray, Comparator.comparing(Country::getCountryCode));
    }

    public void sortPrincipalCountryById(){
        Collections.sort(countryArray, Comparator.comparing(Country::getId));
    }




    //Json methods
    public void WriteJson(){

        Gson gson = new Gson();

        String citiesJson = gson.toJson(cityArray);
        String countriesJson = gson.toJson(countryArray);

        try {
            FileOutputStream fos = new FileOutputStream(new File("dataBase\\Cities.txt"));
            fos.write( citiesJson.getBytes(StandardCharsets.UTF_8) );
            fos.close();

            fos = new FileOutputStream(new File("dataBase\\Countries.txt"));
            fos.write( countriesJson.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ReadJson() {
        try {

            //Read cities
            File file = new File("dataBase\\Cities.txt");
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();

            //Read countries
            file = new File("dataBase\\Countries.txt");
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            String json2 = "";
            String line2;
            if((line2=reader.readLine())!=null){
                json2= line2;
            }
            fis.close();

            Gson gson = new Gson();
            City[] citiesFromJson = gson.fromJson(json, City[].class);
            ArrayList<City> toSendCities = new ArrayList<>();

            Gson gson2= new Gson();
            Country[] countriesFromJson = gson.fromJson(json2, Country[].class);
            ArrayList<Country> toSendCountries = new ArrayList<>();


            if(citiesFromJson!=null)toSendCities.addAll(List.of(citiesFromJson));

            if(countriesFromJson!=null)toSendCountries.addAll(List.of(countriesFromJson));

            cityArray=toSendCities;
            countryArray=toSendCountries;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSQL(String path) throws Exception {

            //Read SQL
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String text=reader.readLine();
            String [] command;
            String cleanText="";

            while (text != null) {

                cleanText=cleanCommand(text);

                command= cleanText.split(" ");
                if(command.length>2){
                    verifyFormat(command,text);
                }else {
                    throw new FormatException();
                }
                text = reader.readLine();
            }

            fis.close();

    }
    /*
    INSERT INTO countriesid name population countryCode VALUES 6ec3e8ec-3dd0-11ed-b878-0242ac120002 Colombia 50.2 +57
    INSERT INTO citiesid name countryID population VALUES e4aa04f6-3dd0-11ed-b878-0242ac120002 Cali 6ec3e8ec-3dd0-11ed-b878-0242ac120002 2.2
    SELECT * FROM cities WHERE name = Guadalajara ORDER BY population
     */


    public String verifyFormat(String [] command,String text) throws Exception{
        String out="";
        if(command[0].equals("INSERT")){
            verifyInsert(command,text);
        }else if(command[0].equals("SELECT")){
            out=printArray(verifySelect(command,text));
        }else if(command[0].equals("DELETE")){
            verifyDelete(command,text);
        }
        else{
            throw new FormatException();
        }
        return out;
    }


    private String verifyInsert(String [] command,String text) throws Exception {

        String correctFormat;
        String out="";

        try {
            if (command[2].equals("countriesid")) {
                String id;
                String countryName;
                String countryCode;
                String population;

                if (command.length == 11) {
                    id = command[7];
                    countryName = command[8];
                    population = command[9];
                    countryCode = command[10];

                }else if(command.length == 12){
                    id = command[7];
                    countryName = command[8] +" " +command[9];
                    population = command[10];
                    countryCode = command[11];
                }else {
                    throw new FormatException();
                }

                correctFormat = "INSERT INTO countries(id, name, population, countryCode) VALUES ('" + id + "', '" + countryName + "', " + population + ", '" + countryCode + "')";

                if (!correctFormat.equals(text)) {

                    throw new FormatException();
                }

                double doublePop=Double.parseDouble(population);

                if(searchCountry(id)){
                    throw new CountryExistsException();
                }

                Country newCountry=new Country(id,countryName,countryCode,doublePop);
                countryArray.add(newCountry);
                WriteJson();

                out="A new country has been added";

            }else if (command[2].equals("citiesid")) {

                String id;
                String cityName;
                String countryId;
                String population;

                if (command.length == 11) {
                    id = command[7];
                    cityName = command[8];
                    countryId= command[9];
                    population= command[10];

                }else if(command.length == 12){
                    id = command[7];
                    cityName = command[8] + " " +command[9];
                    countryId= command[10];
                    population= command[11];
                }else {
                    throw new FormatException();
                }

                correctFormat = "INSERT INTO cities(id, name, countryID, population) VALUES ('" + id + "', '" + cityName + "', '" + countryId + "', " + population + ")";

                if (!correctFormat.equals(text)) {

                    throw new FormatException();
                }

                //double Pop=Double.parseDouble(population);

                int intPop=Integer.parseInt(population);

                sortPrincipalCountryById();


                if(searchCountry(countryId)){
                    City newCity= new City(id,cityName,countryId,intPop);
                    cityArray.add(newCity);
                    WriteJson();
                    out="A new city has been added";
                }else{
                    throw new CountryNotFoundException();
                }

            }else{
                throw new FormatException();
            }

        }catch (CountryNotFoundException e){
            throw new CountryNotFoundException();
        }catch (NumberFormatException e){
            throw new TypeException();
        }catch (CountryExistsException e){
            throw new CountryExistsException();
        }catch (Exception e) {
            throw new FormatException();
        }

        return out;
    }

    private ArrayList verifySelect(String [] command,String text) throws Exception{

        String region="";
        String filterVar="";
        String operator="";
        String condition="";
        String orderVar="";
        String correctFormat="";

        text=cleanCommand(text);

        try {

            if (command.length == 4 || command.length == 7) {

                region = command[3];

                correctFormat = "SELECT * FROM " + region;

                if(command.length == 4){

                    if (!correctFormat.equals(text)) {

                        throw new FormatException();

                    }
                }
                if(command.length==7){
                    orderVar = command[6];
                }

                if (region.equals("countries")) {

                    orderCountryArray=countryArray;
                    if(command.length==7){
                        sortForCountries(orderVar);
                    }
                    return orderCountryArray;

                } else if (region.equals("cities")) {

                    orderCityArray=cityArray;
                    if(command.length==7){
                        sortForCities(orderVar);
                    }
                    return orderCityArray;
                } else {
                    throw new VariableException();
                }

            }else if (command.length == 8 || command.length == 11) {
                region = command[3];
                filterVar = command[5];
                operator = command[6];
                condition = command[7];

                correctFormat = "SELECT * FROM " + region + " WHERE " + filterVar + " " + operator + " " + condition;

                if (!correctFormat.equals(text) && command.length==8) {

                    throw new FormatException();
                }

                if(!operators.contains(operator)){
                    throw new VariableException();
                }

                if (command.length == 11) {

                    orderVar = command[10];

                    correctFormat += " ORDER BY " + orderVar;

                    if (!correctFormat.equals(text)) {
                        throw new FormatException();
                    }
                }

                if (region.equals("countries")){
                    orderCountryArray=filterSwitchCountry(filterVar,condition,operator,region);

                    if (command.length==11) {
                        if (orderCountryArray != null) {

                            sortForCountries(orderVar);
                        }
                    }
                    return orderCountryArray;

                }else if (region.equals("cities")) {
                    orderCityArray=filterSwitchCity(filterVar,condition,operator,region);

                    if (command.length==11) {
                        if (orderCityArray != null) {
                            sortForCities(orderVar);
                        }
                    }
                    return orderCityArray;
                }else throw new VariableException();

            }else{
                throw new FormatException();
            }

        }catch (VariableException e){
            throw new VariableException();
        }catch (Exception e){
            throw new FormatException();
        }
    }

    private void sortForCountries(String orderVar)throws Exception{
        if (!orderVar.equals("")) {
            if (orderVar.equals("name")) {
                sortCountryByName();
            } else if (orderVar.equals("population")) {
                sortCountryByPopulation();
            } else if (orderVar.equals("id")) {
                sortCountryById();
            } else if (orderVar.equals("CountryCode")) {
                sortCountryByCode();
            } else throw new VariableException();

        } else throw new VariableException();
    }

    private void sortForCities(String orderVar)throws Exception{
        if (!orderVar.equals("")) {
            if (orderVar.equals("name")) {
                sortCityByName();
            } else if (orderVar.equals("population")) {
                sortCityByPopulation();
            } else if (orderVar.equals("id")) {
                sortCityById();
            } else if (orderVar.equals("Country")) {
                sortCityByCountyId();
            } else throw new VariableException();

        } else throw new VariableException();
    }

    private ArrayList<Country> filterSwitchCountry(String filterVar, String condition, String operator, String region) throws Exception{

        if (region.equals("countries")) {

            switch (filterVar){

                case "population":
                    selectCountries(operator,condition,3);
                    break;

                case "name":

                    selectCountries(operator,condition,1);
                    break;

                case "code":
                    selectCountries(operator,condition,2);
                    break;

                case "id":
                    selectCountries(operator,condition,0);
                    break;

                default:
                    throw new VariableException();
            }

            return orderCountryArray;
        } else{
            throw new VariableException();
        }

    }

    private ArrayList<City> filterSwitchCity(String filterVar, String condition, String operator, String region) throws Exception{
        if (region.equals("cities")) {

            switch (filterVar){

                case "population":
                    selectCities(operator,condition,3);
                    break;

                case "name":
                    selectCities(operator,condition,1);
                    break;

                case "country":
                    condition=searchCountryByName(condition);
                    if(condition.equals(""))throw new VariableException();
                    selectCities(operator,condition,2);
                    break;

                case "id":
                    selectCities(operator,condition,0);
                    break;

                default:
                    throw new VariableException();

            }
            return orderCityArray;

        } else {
            throw new VariableException();
        }
    }

    private void verifyDelete(String [] command,String text) throws Exception{

        text=cleanCommand(text);

        try {

            if (command.length == 3) {

                String region = command[2];

                String correctFormat = "DELETE FROM " + region;

                if (!correctFormat.equals(text)) {
                    System.out.println(correctFormat);
                    System.out.println(text);
                    throw new FormatException();
                }

                if (region.equals("countries")){
                    countryArray.clear();
                    WriteJson();
                }else if (region.equals("cities")){
                    cityArray.clear();
                    WriteJson();
                }else{
                    throw new VariableException();
                }

            }else if (command.length == 7) {

                String region = command[2];
                String filterVar = command[4];
                String operator = command[5];
                String condition = command[6];

                String correctFormat = "DELETE FROM " + region + " WHERE " + filterVar + " " + operator + " " + condition;

                if(!operators.contains(operator)){
                    throw new VariableException();
                }

                if (!correctFormat.equals(text)) {
                    System.out.println(correctFormat);
                    System.out.println(text);
                    throw new FormatException();
                }

                if (region.equals("countries")) {
                    orderCountryArray=filterSwitchCountry(filterVar,condition,operator,region);

                    for(Country obj: orderCountryArray){
                        countryArray.remove(obj);
                        WriteJson();
                    }

                } else if (region.equals("cities")) {
                    orderCityArray=filterSwitchCity(filterVar,condition,operator,region);

                    for(City obj: orderCityArray){
                        cityArray.remove(obj);
                        WriteJson();
                    }

                } else {
                    throw new VariableException();
                }

            } else {
                throw new FormatException();
            }
        }catch(VariableException e){
            throw new VariableException();
        }catch (Exception e){
            throw new FormatException();
        }

    }


    public String cleanCommand(String command){
        String charsToRemove = "'(),";

        for (char c : charsToRemove.toCharArray()) {
            command = command.replace(String.valueOf(c), "");
        }

        return command;

    }

    public boolean searchCountry(String id){

        boolean verify=false;

        for (int i=0; i<countryArray.size();i++){
            if(countryArray.get(i).getId().equals(id)){
                verify=true;
            }
        }
        return verify;
    }

    public String searchCountryByName(String name){

        String id="";

        for (int i=0; i<countryArray.size();i++){
            if(countryArray.get(i).getName().equals(name)){
                id=countryArray.get(i).getId();
            }
        }
        return id;
    }

    public void selectCountries(String operator, String condition, int var){
        orderCountryArray=new ArrayList<>();

        if (operator.equals(">")){
            for(int i=0; i <countryArray.size();i++){
                if(countryArray.get(i).compareOperator(var,condition)> 0) {
                    orderCountryArray.add(countryArray.get(i));
                }
            }
        } else if (operator.equals("<")) {
            for(int i=0; i <countryArray.size();i++){
                if(countryArray.get(i).compareOperator(var,condition) < 0) {
                    orderCountryArray.add(countryArray.get(i));
                }
            }
        }else {
            for(int i=0; i <countryArray.size();i++){
                if(countryArray.get(i).compareOperator(var,condition) == 0) {
                    orderCountryArray.add(countryArray.get(i));
                }
            }
        }
    }

    public void selectCities(String operator, String condition, int var){
        orderCityArray=new ArrayList<>();
        for(int i=0; i <cityArray.size();i++) {
            if (operator.equals(">")) {
                if (cityArray.get(i).compareOperator(var,condition)> 0) {
                    orderCityArray.add(cityArray.get(i));
                }
            } else if (operator.equals("<")) {
                if (cityArray.get(i).compareOperator(var,condition) < 0) {
                    orderCityArray.add(cityArray.get(i));
                }
            } else {
                if (cityArray.get(i).compareOperator(var,condition) == 0) {
                    orderCityArray.add(cityArray.get(i));
                }
            }
        }
    }




    public String listCountries(){
       String  out="There are no countries to show";

       if(!countryArray.isEmpty()) {
           out="";
           for (Country p : countryArray) {
               out += p.getName() + " ID: " + p.getId()+"\n";
           }
       }
       return out;
    }

    public <T> String printArray(ArrayList<T> array){
        StringBuilder out= new StringBuilder();

        for(T obj: array){
            out.append(obj).append("\n");
        }
        return out.toString();
    }

    public int getSizeCity(){
        return cityArray.size();
    }

    public int getCountrySize(){
        return countryArray.size();
    }

    public ArrayList<City> getOrderCityArray() {
        return orderCityArray;
    }

    public ArrayList<Country> getOrderCountryArray() {
        return orderCountryArray;
    }

    public ArrayList<City> getCityArray() {
        return cityArray;
    }
}
