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
        String charsToRemove = "'(),";

        /*
        for (char c : charsToRemove.toCharArray()) {
            prueba = prueba.replace(String.valueOf(c), "");
        }

         */

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

    /*
    public static long[] quickSort(long[]a, int first, int last){
        long piv = (a[first] + a[last])/2;
        int i = first;
        int j = last;

        while (i<j){
            while (a[i]<piv) i++;
            while (a[j]>piv) j--;
            if(i<=j){
                long x = a[i];
                a[i] = a[j];
                a[j] = x;
                i++;
                j--;
            }
        }
        if(first<j){
            a = quickSort(a, first, j);
        }
        if(last>i){
            a = quickSort(a, i, last);
        }

        return a;
    }

     */

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

    public void readSQL(String path){

    }

    public void readLine(String input){


    }



}
