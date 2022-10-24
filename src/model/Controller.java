package model;
import java.security.PrivateKey;
import java.util.*;

public class Controller{

    private ArrayList<City> cityArray;
    private ArrayList<Country> countryArray;

    private ArrayList<City> orderCityArray;
    private ArrayList<Country> orderCountryArray;


    public Controller(){

        cityArray=new ArrayList<>();
        countryArray=new ArrayList<>();
        orderCityArray=new ArrayList<>();
        orderCountryArray=new ArrayList<>();

        City city2=new City("1","b","1",2);
        City city1=new City("1","a","1",1);
        City city3=new City("1","c","1",3);

        cityArray.add(city2);
        cityArray.add(city1);
        cityArray.add(city3);
        sortCityByPopulation();

        System.out.println(Arrays.toString(cityArray.toArray()));

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
/*
    public void WriteJson(){

        Gson gson = new Gson();

        for(Patient p:allPatients){
            p.setStatusPatient(StatusPatientEnum.OUT_OF_HOSPITAL);
        }

        String json = gson.toJson(allPatients);

        try {
            FileOutputStream fos = new FileOutputStream(new File("dataBase\\patients.txt"));
            fos.write( json.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Patient> ReadJson() {
        try {
            File file = new File("dataBase\\patients.txt");
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();

            Gson gson = new Gson();
            Patient[] patienstFromJson = gson.fromJson(json, Patient[].class);
            ArrayList<Patient> sent = new ArrayList<>();

            if(patienstFromJson!=null)sent.addAll(List.of(patienstFromJson));

            return sent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

 */

}
