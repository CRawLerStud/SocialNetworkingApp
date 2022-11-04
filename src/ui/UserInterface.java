package ui;

import controller.Controller;
import models.Friendship;
import models.User;
import repo.RepositoryException;
import validators.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    Scanner scanner;
    Controller controller;
    public UserInterface(Controller _controller){
        this.controller = _controller;
        scanner = new Scanner(System.in);
    }

    public void mainMenu(){
        while(true){
            try{
                printMainMenu();
                System.out.print("Option: ");
                int option = Integer.parseInt(scanner.nextLine());
                switch(option){
                    case 0:
                        return;
                    case 1:
                        usersMenu();
                        break;
                    case 2:
                        friendshipsMenu();
                        break;
                    case 3:
                        communitiesMenu();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }
            catch(NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void communitiesMenu() {
        while(true){
            try{
                printCommunitiesMenu();
                System.out.print("Option: ");
                int option = Integer.parseInt(scanner.nextLine());
                switch(option){
                    case 0:
                        return;
                    case 1:
                        discoverCommunitiesUI();
                        break;
                    case 2:
                        mostSociableCommunityUI();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }
            catch(NumberFormatException error){
                System.out.println(error.getMessage());
            }
        }
    }

    private void mostSociableCommunityUI() {
        List<User> community = controller.mostSociableCommunity();
        System.out.println("The most sociable community: ");
        System.out.println(community);
    }

    private void discoverCommunitiesUI() {
        List<List<User>> result = controller.discoverCommunities();
        System.out.println(result.size() + " communities found!");
        result.forEach(System.out::println);
    }

    private void printCommunitiesMenu() {
        System.out.println("1. Discover Communities");
        System.out.println("2. Most Sociable Community");
        System.out.println("0. Back");
    }

    private void usersMenu() {
        while(true){
            try {
                printUsersMenu();
                System.out.print("Option: ");
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 0:
                        return;
                    case 1:
                        controller.allUsers().forEach(System.out::println);
                        break;
                    case 2:
                        try {
                            addUserUI();
                        } catch (RepositoryException | ValidationException |
                                 DateTimeParseException | NumberFormatException error) {
                            System.out.println(error.getMessage());
                        }
                        break;
                    case 3:
                        try{
                            searchUserUI();
                        }
                        catch(RepositoryException | NumberFormatException error){
                            System.out.println(error.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            removeUserUI();
                        } catch (RepositoryException | NumberFormatException error) {
                            System.out.println(error.getMessage());
                        }
                        break;
                    case 5:
                        printAllUsersFriendsUI();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }
            catch(NumberFormatException error){
                System.out.println(error.getMessage());
            }
        }
    }

    private void searchUserUI() throws RepositoryException{
        System.out.print("User's ID: ");
        Long ID = Long.parseLong(scanner.nextLine());
        User foundUser = controller.findUser(ID);
        System.out.println(foundUser);
    }

    private void printAllUsersFriendsUI() {
        for(User user : controller.allUsers()) {
            System.out.println(user + " IS FRIEND WITH");
            System.out.println(user.allFriends());
        }
    }

    private void printFriendshipsMenu() {
        System.out.println("1. Print friendships");
        System.out.println("2. Add friendship");
        System.out.println("3. Delete friendship");
        System.out.println("0. Back");
    }

    private void friendshipsMenu() {
        while(true){
            printFriendshipsMenu();
            System.out.print("Option: ");
            int option = Integer.parseInt(scanner.nextLine());
            switch(option){
                case 0:
                    return;
                case 1:
                    controller.allFriendships().forEach(System.out::println);
                    break;
                case 2:
                    try {
                        addFriendshipUI();
                    }
                    catch(RepositoryException | ValidationException | NumberFormatException error){
                        System.out.println(error.getMessage());
                    }
                    break;
                case 3:
                    try{
                        searchFriendshipUI();
                    }
                    catch(RepositoryException| NumberFormatException error){
                        System.out.println(error.getMessage());
                    }
                case 4:
                    try {
                        removeFriendshipUI();
                    }
                    catch(RepositoryException|NumberFormatException error){
                        System.out.println(error.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void searchFriendshipUI() throws RepositoryException{
        System.out.print("Friendship ID: ");
        Long ID = Long.parseLong(scanner.nextLine());
        Friendship foundFriendship = controller.findFriendship(ID);
        System.out.println(foundFriendship);
    }

    private void removeFriendshipUI() throws RepositoryException{
        System.out.println("Remove a friendship MENU");
        System.out.print("Friendship ID: ");
        Long friendshipID = Long.parseLong(scanner.nextLine());
        Friendship removedFriendship = controller.removeFriendship(friendshipID);
        System.out.println("Friendship " + removedFriendship + " has been removed!");
    }

    private void addFriendshipUI() throws RepositoryException, ValidationException{
        System.out.println("Create a new friendship MENU");
        System.out.print("New Friendship ID: ");
        Long friendshipID = Long.parseLong(scanner.nextLine());
        System.out.print("User 1 ID: ");
        Long firstUserID = Long.parseLong(scanner.nextLine());
        User firstUser = controller.findUser(firstUserID);
        System.out.print("User 2 ID: ");
        Long secondUserID = Long.parseLong(scanner.nextLine());
        User secondUser = controller.findUser(secondUserID);
        Friendship newFriendship = new Friendship(firstUser, secondUser);
        newFriendship.setId(friendshipID);
        controller.addFriendship(newFriendship);
        System.out.println("Friendship added successfully!");
    }

    private void removeUserUI() throws RepositoryException {
        System.out.print("ID: ");
        Long ID = Long.parseLong(scanner.nextLine());
        User deletedUser = controller.removeUser(ID);
        System.out.println(deletedUser + " has been removed!");
    }

    private void addUserUI() throws ValidationException, RepositoryException {
        System.out.print("ID: ");
        Long ID = Long.parseLong(scanner.nextLine());
        System.out.print("Lastname: ");
        String lastname = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Birthdate(yyyy-mm-dd): ");
        LocalDate birthdate = LocalDate.parse(scanner.nextLine());
        User newUser = new User(lastname, surname, birthdate);
        newUser.setId(ID);
        controller.addUser(newUser);
        System.out.println("User " + newUser + " added successfully!");
    }

    private void printUsersMenu() {
        System.out.println("1. Print users");
        System.out.println("2. Add user");
        System.out.println("3. Search user");
        System.out.println("4. Delete user");
        System.out.println("5. All Users with their Friends");
        System.out.println("0. Back");
    }

    private void printMainMenu() {
        System.out.println("1. Users Menu");
        System.out.println("2. Friendships Menu");
        System.out.println("3. Communities Menu");
        System.out.println("0. Exit");
    }
}
