package ui;

import controller.Controller;
import models.User;

import java.util.List;

public class UserFilterMenu extends AbstractMenu{

    public UserFilterMenu(Controller controller) {
        super(controller);
    }

    @Override
    protected void mainMenu(int option) {
        switch (option) {
            case 1 -> printUsersWithLastname();
            case 2 -> printUsersWithSurname();
            case 3 -> printUsersWithFullname();
            case 4 -> printUsersLastNameStartsWith();
            case 5 -> printUsersSurnameStartsWith();
            case 6 -> printUsersOlderThan();
            default -> System.out.println("Invalid option!");
        }
    }

    private void printUsersOlderThan() {
        try {
            System.out.print("Minimum Age: ");
            int minimumAge = Integer.parseInt(scanner.nextLine());

            List<User> foundUsers = controller.usersOlderThan(minimumAge);
            System.out.println(foundUsers.size() + " users found!");
            foundUsers.forEach(System.out::println);
        }
        catch(NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

    private void printUsersSurnameStartsWith() {
        System.out.print("Sequence: ");
        String sequence = scanner.nextLine();

        List<User> foundUsers = controller.usersSurnameStartsWith(sequence);
        System.out.println(foundUsers.size() + " users found!");
        foundUsers.forEach(System.out::println);
    }

    private void printUsersLastNameStartsWith() {
        System.out.print("Sequence: ");
        String sequence = scanner.nextLine();

        List<User> foundUsers = controller.usersLastnameStartsWith(sequence);
        System.out.println(foundUsers.size() + " users found!");
        foundUsers.forEach(System.out::println);
    }

    private void printUsersWithFullname() {
        System.out.print("Lastname: ");
        String lastname = scanner.nextLine();

        System.out.print("Surname: ");
        String surname = scanner.nextLine();

        List<User> foundUsers = controller.usersFullnameIs(lastname, surname);
        System.out.println(foundUsers.size() + " users found!");
        foundUsers.forEach(System.out::println);
    }

    private void printUsersWithSurname() {
        System.out.print("Surname: ");
        String surname = scanner.nextLine();

        List<User> foundUsers = controller.usersSurnameIs(surname);
        System.out.println(foundUsers.size() + " users found!");
        foundUsers.forEach(System.out::println);
    }

    private void printUsersWithLastname() {
        System.out.print("Lastname: ");
        String lastname = scanner.nextLine();

        List<User> foundUsers = controller.usersLastnameIs(lastname);
        System.out.println(foundUsers.size() + " users found!");
        foundUsers.forEach(System.out::println);
    }

    @Override
    protected void printMenu() {
        System.out.println("1. Users with lastname");
        System.out.println("2. Users with surname");
        System.out.println("3. Users with fullname");
        System.out.println("4. Users whose lastname starts with sequence");
        System.out.println("5. Users whose surname starts with sequence");
        System.out.println("6. Users that are older than");
    }
}
