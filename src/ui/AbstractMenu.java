package ui;

import controller.Controller;

import java.util.Scanner;

public abstract class AbstractMenu {

    protected Controller controller;
    protected Scanner scanner;

    public AbstractMenu(Controller controller){
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void execute(String leavingMessage){
        while(true){
            try{
                printMenu();
                System.out.print("Option: ");
                int option = Integer.parseInt(scanner.nextLine());
                if(option == 0){
                    for(int i=0; i<20; i++)
                        System.out.println();
                    System.out.println(leavingMessage);
                    return;
                }
                mainMenu(option);
            }
            catch(NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }

    protected abstract void mainMenu(int option);

    protected abstract void printMenu();
}
