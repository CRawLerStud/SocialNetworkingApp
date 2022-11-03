import models.User;
import repo.FriendshipRepository;
import repo.RepositoryException;
import repo.UserFileRepository;
import service.FriendshipService;
import service.UserService;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserFileRepository repository = new UserFileRepository("data/users.csv");

        FriendshipValidator friendshipValidator = new FriendshipValidator();
        FriendshipRepository friendshipRepository = new FriendshipRepository();
        FriendshipService friendshipService = new FriendshipService(friendshipValidator, friendshipRepository);

        UserValidator validator = new UserValidator();
        UserService service = new UserService(validator, repository);
        Scanner scanner = new Scanner(System.in);

        while(true){
            printMenu();
            System.out.print("Option: ");
            int option = Integer.parseInt(scanner.nextLine());
            switch(option){
                case 0:
                    return;
                case 1:
                    service.findAll().forEach(System.out::println);
                    break;
                case 2:
                    try {
                        System.out.print("ID: ");
                        Long ID = Long.parseLong(scanner.nextLine());
                        System.out.print("Lastname: ");
                        String lastname = scanner.nextLine();
                        System.out.print("Surname: ");
                        String surname = scanner.nextLine();
                        System.out.print("BirthDate(yyyy-mm-dd): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        User newUser = new User(lastname, surname, date);
                        newUser.setId(ID);
                        service.save(newUser);
                        System.out.println(newUser + " added!");
                    }
                    catch(RepositoryException | ValidationException |
                          DateTimeParseException| NumberFormatException error){
                        System.out.println(error.getMessage());
                    }
                    break;
                case 3:
                    try{
                        System.out.print("ID: ");
                        Long ID = Long.parseLong(scanner.nextLine());
                        User deletedUser = service.delete(ID);
                        System.out.println(deletedUser + " removed!");
                    }
                    catch(RepositoryException | NumberFormatException error){
                        System.out.println(error.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        }
    }

    private static void printMenu() {
        System.out.println("1. Print users");
        System.out.println("2. Add user");
        System.out.println("3. Delete user");
        System.out.println("0. Exit");
    }
}