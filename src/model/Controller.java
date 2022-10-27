package model;
import java.security.PrivateKey;
import java.util.*;
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

    public void readSQL(String path) {
        try {
            //Read SQL
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String text=reader.readLine();
            String [] command;

            while (text != null) {

                text=cleanCommand(text);



                command= text.split(" ");
                if(command.length>2){
                    if(command[0].equals("INSERT")){
                        verifyInsert(command);

                    }else if(command[0].equals("SELECT")){
                        System.out.println(text);
                        verifySelect(command);
                    }else if(command[0].equals("DELETE")){
                        verifyDelete(command);
                    }

                }else {
                    System.out.println("Invalid command");
                }



                text = reader.readLine();
            }

            fis.close();

            }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }


    }
    /*
    INSERT INTO countriesid name population countryCode VALUES 6ec3e8ec-3dd0-11ed-b878-0242ac120002 Colombia 50.2 +57
INSERT INTO citiesid name countryID population VALUES e4aa04f6-3dd0-11ed-b878-0242ac120002 Cali 6ec3e8ec-3dd0-11ed-b878-0242ac120002 2.2
SELECT * FROM cities WHERE name = Guadalajara ORDER BY population
     */

    private void verifyInsert(String [] command){

        String correctFormat;
        if (command[2].equals("countriesid")){
            correctFormat="INSERT INTO countriesid name population countryCode VALUES 6ec3e8ec-3dd0-11ed-b878-0242ac120002 Colombia 50.2 +57";
            String [] arrayCorrectFormat= correctFormat.split(" ");

            if(command.length==11){
                arrayCorrectFormat [7]=command[7];
                arrayCorrectFormat[8]=command[8];
                arrayCorrectFormat[9]=command[9];
                arrayCorrectFormat[10]=command[10];

                StringBuffer cadena = new StringBuffer();
                StringBuffer cadena2= new StringBuffer();
                for (int x=0;x<command.length;x++){
                    cadena =cadena.append(command[x]);
                    cadena2= cadena2.append(arrayCorrectFormat[x]);
                }

                if (cadena.toString().equals(cadena2.toString())){
                    System.out.println("Correct format");
                }
            }

        } else if (command[2].equals("citiesid")) {


        }
    }

    private void verifySelect(String [] command){
        if(command[3].equals("countries")){


        } else if (command[3].equals("cities")) {


        }
    }

    private void verifyDelete(String [] command){
        if (command[2].equals("countriesid")){


        } else if (command[2].equals("citiesid")) {


        }
    }

    private String cleanCommand(String command){
        String charsToRemove = "'(),";

        for (char c : charsToRemove.toCharArray()) {
            command = command.replace(String.valueOf(c), "");
        }

        return command;

    }


    public void readLine(String input){


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
