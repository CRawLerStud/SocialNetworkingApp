package ui;

import controller.Controller;
import models.Friendship;
import models.User;
import repo.RepositoryException;
import validators.ValidationException;

public class FriendshipsMenu extends AbstractMenu{

    public FriendshipsMenu(Controller controller) {
        super(controller);
    }

    @Override
    protected void mainMenu(int option) {
        switch (option) {
            case 1 -> printFriendships();
            case 2 -> addFriendship();
            case 3 -> findFriendship();
            case 4 -> removeFriendship();
            default -> System.out.println("Invalid option!");
        }
    }

    private void findFriendship() {
        try {
            System.out.print("ID: ");
            Long ID = Long.parseLong(scanner.nextLine());

            Friendship foundFriendship = controller.findFriendship(ID);
            System.out.println(foundFriendship + " has been found!");
        }
        catch(NumberFormatException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeFriendship() {
        try{
            System.out.print("ID: ");
            Long ID = Long.parseLong(scanner.nextLine());

            Friendship removedFriendship = controller.removeFriendship(ID);
            System.out.println(removedFriendship + " has been removed!");
        }
        catch(NumberFormatException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void addFriendship() {
        try {
            System.out.print("Option: ");
            Long ID = Long.parseLong(scanner.nextLine());
            System.out.print("User 1(ID): ");
            Long userId1 = Long.parseLong(scanner.nextLine());
            System.out.print("User 2(ID): ");
            Long userId2 = Long.parseLong(scanner.nextLine());

            User user1 = controller.findUser(userId1);
            User user2 = controller.findUser(userId2);

            Friendship newFriendship = new Friendship(user1, user2);
            newFriendship.setId(ID);

            controller.addFriendship(newFriendship);
            System.out.println("Friendship was added!");

        }
        catch(RepositoryException | ValidationException | NumberFormatException e){
            System.out.println(e.getMessage());
        }

    }

    private void printFriendships() {
        controller.allFriendships().forEach(System.out::println);
    }

    @Override
    protected void printMenu() {
        System.out.println("1. Print friendships");
        System.out.println("2. Add firdnship");
        System.out.println("3. Find friendship");
        System.out.println("4. Delete friendship");
        System.out.println("0. Back");
    }
}
