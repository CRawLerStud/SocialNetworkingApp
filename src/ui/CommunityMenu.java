package ui;

import controller.Controller;
import models.User;

import java.util.List;

public class CommunityMenu extends AbstractMenu{

    public CommunityMenu(Controller controller){
        super(controller);
    }

    @Override
    protected void mainMenu(int option) {
        switch (option) {
            case 1 -> communitiesUI();
            case 2 -> biggestCommunityUI();
            default -> System.out.println("Invalid option!");
        }
    }

    @Override
    protected void printMenu() {
        System.out.println("1. Discover Communities");
        System.out.println("2. Most sociable Community");
        System.out.println("0. Back");
    }

    private void biggestCommunityUI() {
        List<User> community = controller.mostSociableCommunity();
        System.out.println("The most sociable community is: ");
        community.forEach(System.out::println);
    }

    private void communitiesUI() {
        List<List<User>> communities = controller.discoverCommunities();
        int i = 1;
        for(List<User> community : communities){
            System.out.println("Community " + i);
            community.forEach(System.out::println);
            System.out.println();
            i++;
        }
    }

}
