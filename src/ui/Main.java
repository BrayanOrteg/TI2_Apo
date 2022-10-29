package ui;
import model.Controller;

import java.util.Scanner;
import java.util.UUID;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Main {

    public Main(){
    }

    private static Scanner sc;
    private static  Controller control;


    public static void main(String [] args) {

        sc = new Scanner(System.in);
        Main main= new Main();
        control=new Controller();

        int option=0;

        do{
            option= main.showMenu();
            main.executeOperation(option);

        }while (option!=4);

    }




    public int showMenu() {
        int option=0;

        System.out.println(
                "\n"+
                        "Seleccione una opcion para iniciar\n" +
                        "(1) Insert a comand\n" +
                        "(2) Import data from SQL file\n"+
                        "(3) Show available countries\n"+
                        "(4) Salir\n"

        );
        option= sc.nextInt();
        sc.nextLine();
        return option;
    }


    public void executeOperation(int operation) {

        switch(operation) {

            case 1:
                UUID code=UUID.randomUUID();

                System.out.println("If your are going to insert an element, please use this code: "+code);
                String input=sc.nextLine();

                String cleanInput=control.cleanCommand(input);
                String [] command= cleanInput.split(" ");

                try{
                    System.out.println(control.verifyFormat(command,input));
                }catch (Exception e){
                    System.out.println(e);
                }

                break;

            case 2:
                Chooser chooser=new Chooser();
                String path=chooser.getPath();

                try{
                    control.readSQL(path);
                }catch (Exception e){
                    System.out.println(e);
                }


                break;

            case 3:

                System.out.println(control.listCountries());

                break;

            case 4:
                control.WriteJson();
                System.out.println("Bye!");
                break;


            default:
                System.out.println("Error, option no valid");

        }
    }
}
