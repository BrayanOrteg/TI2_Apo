package ui;
import model.Controller;

import java.util.Scanner;

public class Main {

    public Main(){
    }

    private static Scanner sc;
    private static  Controller control=new Controller();


    public static void main(String [] args) {

        sc = new Scanner(System.in);

        Main principal= new Main();


        int option=0;

        do{
            option= principal.showMenu();
            principal.executeOperation(option);

        }while (option!=3);




    }




    public int showMenu() {
        int option=0;

        System.out.println(
                "\n"+
                        "Seleccione una opcion para iniciar\n" +
                        "(1) Insert a comand\n" +
                        "(2) Import data from SQL file\n"+
                        "(3) Salir\n"

        );
        option= sc.nextInt();
        sc.nextLine();
        return option;
    }


    public void executeOperation(int operation) {



        switch(operation) {

            case 1:




                break;

            case 2:




                break;

            case 3:
                System.out.println("Bye!");
                break;


            default:
                System.out.println("Error, option no valid");

        }
    }
}
