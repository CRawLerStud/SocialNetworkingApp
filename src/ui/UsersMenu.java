package ui;

import controller.Controller;
import models.User;
import repo.RepositoryException;
import validators.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UsersMenu extends AbstractMenu{

    private final UserFilterMenu filterUI;

    public UsersMenu(Controller controller) {
        super(controller);
        this.filterUI = new UserFilterMenu(controller);
    }

    @Override
    protected void mainMenu(int option) {
        switch (option) {
            case 1 -> printUsersUI();
            case 2 -> addUserUI();
            case 3 -> searchUser();
            case 4 -> deleteUser();
            case 5 -> allUsersWithFriends();
            case 6 -> changeUserLastname();
            case 7 -> changeUserSurname();
            case 8 -> changeUserBirthdate();
            case 9 -> filterUI.execute("Returning to Main User Menu");
            default -> System.out.println("Invalid option");
        }
    }

    private void changeUserBirthdate() {
        try {
            System.out.print("User's ID: ");
            Long ID = Long.parseLong(scanner.nextLine());
            System.out.print("New Birthdate(yyyy-mm-dd): ");
            LocalDate newBirthdate = LocalDate.parse(scanner.nextLine());

            User modifiedUser = controller.changeUserBirthdate(ID, newBirthdate);
            System.out.println(modifiedUser + " has been modified!");
        }
        catch(NumberFormatException | RepositoryException | ValidationException | DateTimeParseException e){
            System.out.println(e.getMessage());
        }
    }

    private void changeUserSurname() {
        try {
            System.out.print("User's ID: ");
            Long ID = Long.parseLong(scanner.nextLine());
            System.out.print("New Surname: ");
            String newSurname = scanner.nextLine();

                User modifiedUser = controller.changeUserSurname(ID, newSurname);
            System.out.println(modifiedUser + " has been modified!");
        }
        catch(NumberFormatException|RepositoryException|ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void changeUserLastname() {
        try {
            System.out.print("User's ID: ");
            Long ID = Long.parseLong(scanner.nextLine());
            System.out.print("New Lastname: ");
            String newLastname = scanner.nextLine();

            User modifiedUser = controller.changeUserLastname(ID, newLastname);
            System.out.println(modifiedUser + " has been modified!");
        }
        catch(NumberFormatException|RepositoryException|ValidationException e){
            System.out.println(e.getMessage());
        }

    }

    private void allUsersWithFriends() {
        controller.allUsers().forEach(x -> {
            System.out.println(x + " has the following friends:");
            int i = 1;
            for(User friend : x.allFriends()){
                System.out.println("Friend " + i + ": " + friend);
                i++;
            }
        });
    }

    private void deleteUser() {
        try {
            System.out.print("ID: ");
            Long ID = Long.parseLong(scanner.nextLine());

            User deletedUser = controller.removeUser(ID);
            System.out.println(deletedUser + " has been removed!");
        }
        catch(RepositoryException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchUser() {
        try {
            System.out.print("ID: ");
            Long ID = Long.parseLong(scanner.nextLine());

            User foundUser = controller.findUser(ID);
            System.out.println(foundUser + " found!");
        }
        catch(RepositoryException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

    private void addUserUI() {
        try {
            System.out.print("ID: ");
            Long ID = Long.parseLong(scanner.nextLine());
            System.out.print("Lastname: ");
            String lastname = scanner.nextLine();
            System.out.print("Surname: ");
            String surname = scanner.nextLine();
            System.out.print("Birthdate(yyyy-mm-dd): ");
            LocalDate birthdate = LocalDate.parse(scanner.nextLine());

            User createdUser = new User(lastname, surname, birthdate);
            createdUser.setId(ID);
            controller.addUser(createdUser);
            System.out.println("User " + createdUser + " has been added!");
        }
        catch(NumberFormatException | ValidationException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void printUsersUI() {
        controller.allUsers().forEach(System.out::println);
    }

    @Override
    protected  void printMenu() {
        System.out.println("1. Print users");
        System.out.println("2. Add user");
        System.out.println("3. Search user");
        System.out.println("4. Delete user");
        System.out.println("5. All Users with their Friends");
        System.out.println("6. Change User Lastname");
        System.out.println("7. Change User Surname");
        System.out.println("8. Change User Birthdate");
        System.out.println("9. Filter Users");
        System.out.println("0. Back");
    }
}
