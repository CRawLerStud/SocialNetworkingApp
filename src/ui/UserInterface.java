package ui;

import controller.Controller;

public class UserInterface extends AbstractMenu{

    UsersMenu usersUI;
    FriendshipsMenu friendshipsUI;
    CommunityMenu communityUI;
    public UserInterface(Controller _controller){
        super(_controller);
        usersUI = new UsersMenu(controller);
        friendshipsUI = new FriendshipsMenu(controller);
        communityUI = new CommunityMenu(controller);
    }

    @Override
    protected void mainMenu(int option) {
        switch (option) {
            case 1 -> usersUI.execute("Returning to Main Menu");
            case 2 -> friendshipsUI.execute("Returning to Main Menu");
            case 3 -> communityUI.execute("Returning to Main Menu");
            default -> System.out.println("Invalid option!");
        }
    }

    @Override
    protected void printMenu() {
        System.out.println("1. Users Menu");
        System.out.println("2. Friendships Menu");
        System.out.println("3. Community Menu");
        System.out.println("0. Exit");
    }
}
