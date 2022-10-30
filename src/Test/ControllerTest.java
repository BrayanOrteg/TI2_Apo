package Test;
import junit.framework.TestCase;
import model.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.UUID;

public class ControllerTest extends TestCase {
    Controller controller;
    public void setupStage1(){
        controller= new Controller();
        controller.ReadJson();
    }
    //Colombia code: 6ec3e8ec-3dd0-11ed-b878-0242ac120002


    //City insert test
    public void test1_InsertCity(){
        setupStage1();
        UUID code=UUID.randomUUID();



        String command="INSERT INTO cities(id, name, countryID, population) VALUES ('"+code.toString()+"', 'Palmira', '6ec3e8ec-3dd0-11ed-b878-0242ac120002', 2222)";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");

        int sizeBefore=controller.getSizeCity();
        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getSizeCity(),sizeBefore+1);

        String delete="DELETE FROM cities WHERE population = 2222";
        inputClean=controller.cleanCommand(delete);
        String [] command2=inputClean.split(" ");

        try {
            controller.verifyFormat(command2,delete);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void test1_InsertCity_InvalidId(){
        setupStage1();
        UUID code=UUID.randomUUID();



        String command="INSERT INTO cities(id, name, countryID, population) VALUES ('"+code.toString()+"', 'Palmira', 'rrrrr', 2222)";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");

        int sizeBefore=controller.getSizeCity();
        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getSizeCity(),sizeBefore);



    }

    //Country insert test
    public void test1_InsertCountry(){
        setupStage1();
        UUID code=UUID.randomUUID();



        String command="INSERT INTO countries(id, name, population, countryCode) VALUES ('"+code.toString()+"', 'Japón', 555.8, '+57')";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");

        int sizeBefore=controller.getCountrySize();

        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getCountrySize(),sizeBefore+1);

        String delete="DELETE FROM countries WHERE population = 555.8";
        inputClean=controller.cleanCommand(delete);
        String [] command2=inputClean.split(" ");

        try {
            controller.verifyFormat(command2,delete);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void test1_InsertCountry_SameId(){
        setupStage1();
        UUID code=UUID.randomUUID();



        String command="INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Japón', 555.8, '+57')";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");

        int sizeBefore=controller.getCountrySize();

        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getCountrySize(),sizeBefore);

    }

    //Select test

    public void test2_SelectCitiesColombia(){
        setupStage1();

        String command="SELECT * FROM cities WHERE country = Colombia";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");
        String test="";
        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getOrderCityArray().size(),2);
        System.out.println(controller.printArray(controller.getOrderCityArray()));

    }

    public void test2_OrderCitiesByName(){
        setupStage1();

        String command="SELECT * FROM cities ORDER BY name";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");
        String test="";
        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getOrderCityArray().size(),4);
        System.out.println(controller.printArray(controller.getOrderCityArray()));
    }

    public void test2_SelectCountries_Population(){
        setupStage1();

        String command="SELECT * FROM countries WHERE population > 100";
        String inputClean=controller.cleanCommand(command);

        String [] commmandToSend=inputClean.split(" ");
        String test="";
        try {
            controller.verifyFormat(commmandToSend,command);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(controller.getOrderCountryArray().size(),2);

        System.out.println(controller.printArray(controller.getOrderCountryArray()));
    }



}
