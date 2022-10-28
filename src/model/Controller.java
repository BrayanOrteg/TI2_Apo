package model;
import java.security.PrivateKey;
import java.util.*;

import Exceptions.FormatException;
import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Controller{

    private ArrayList<City> cityArray;
    private ArrayList<Country> countryArray;

    private ArrayList<City> orderCityArray;
    private ArrayList<Country> orderCountryArray;




    public Controller(){
        countryArray=new ArrayList<>();
        orderCityArray=new ArrayList<>();
        orderCountryArray=new ArrayList<>();

        ReadJson();

        System.out.println(Arrays.toString(cityArray.toArray()));
        System.out.println(Arrays.toString(countryArray.toArray()));




    }

// city
    public void sortCityByName(){
        Collections.sort(cityArray, new Comparator<City>() {

            @Override
            public int compare(City o1, City o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void sortCityByPopulation(){
        Collections.sort(cityArray, new Comparator<City>() {

            @Override
            public int compare(City o1, City o2) {
                return o1.getPopulation()-o2.getPopulation();
            }
        });
    }

    //country
    public void sortCountryByName(){
        Collections.sort(cityArray, new Comparator<City>() {

            @Override
            public int compare(City o1, City o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void sortCountryByPopulation(){
        Collections.sort(cityArray, new Comparator<City>() {

            @Override
            public int compare(City o1, City o2) {
                return o1.getPopulation()-o2.getPopulation();
            }
        });
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


    public void verifyFormat(String [] command,String text) throws Exception{
        if(command[0].equals("INSERT")){
            verifyInsert(command,text);
        }else if(command[0].equals("SELECT")){
            verifySelect(command, text);
        }else if(command[0].equals("DELETE")){
            verifyDelete(command,text);
        }
        else{
            throw new FormatException();
        }
    }


    private void verifyInsert(String [] command,String text) throws Exception {

        String correctFormat;

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

            }else{
                throw new FormatException();
            }

            if (!correctFormat.equals(text)) {
                System.out.println(correctFormat);
                System.out.println(text);
                throw new FormatException();
            }

        }catch (Exception e) {
            throw new FormatException();
        }
    }

    private void verifySelect(String [] command,String text) throws Exception{

        String region;
        String filterVar;
        String operator;
        String condition;
        String orderVar;
        String correctFormat;

        try {

            if (command.length == 4) {

                region = command[3];

                correctFormat = "SELECT * FROM " + region;

            } else if (command.length == 8) {
                region = command[3];
                filterVar = command[5];
                operator = command[6];
                condition = command[7];

                correctFormat = "SELECT * FROM " + region + " WHERE " + filterVar + " " + operator + " " + condition;

            } else if (command.length == 11) {

                region = command[3];
                filterVar = command[5];
                operator = command[6];
                condition = command[7];
                orderVar = command[10];

                correctFormat = "SELECT * FROM " + region + " WHERE " + filterVar + " " + operator + " " + condition + " ORDER BY " + orderVar;

            } else {
                throw new FormatException();
            }
            if (!correctFormat.equals(text)) {
                System.out.println(correctFormat);
                System.out.println(text);
                throw new FormatException();
            }
        }catch (Exception e){
            throw new FormatException();
        }
    }

    private void verifyDelete(String [] command,String text) throws Exception{

        if (command.length == 7) {

            String region = command[2];
            String filterVar = command[4];
            String operator = command[5];
            String condition = command[6];

            String correctFormat = "DELETE FROM "+region+" WHERE "+ filterVar+" " + operator+" " + condition;

            if (!correctFormat.equals(text)) {
                System.out.println(correctFormat);
                System.out.println(text);
                throw new FormatException();
            }
        }else {
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


    public String listCountries(){
       String  out="There are no countries to show";

       if(!countryArray.isEmpty()) {
           out="";
           for (Country p : countryArray) {
               out += p.getName() + " ID: " + p.getId();
           }
       }
       return out;
    }


}
