import controller.Controller;
import repo.friendship.FriendshipRepository;
import repo.users.UserFileRepository;
import service.*;
import test.Test;
import ui.UserInterface;
import validators.*;

public class Main {
    public static void main(String[] args) {

        Test.executeAll();

        UserFileRepository userRepository = new UserFileRepository("data/users.csv");

        FriendshipValidator friendshipValidator = new FriendshipValidator();
        FriendshipRepository friendshipRepository = new FriendshipRepository();
        FriendshipService friendshipService = new FriendshipService(friendshipValidator, friendshipRepository);

        UserValidator userValidator = new UserValidator();
        UserService userService = new UserService(userValidator, userRepository);

        Controller controller = new Controller(friendshipService, userService);

        UserInterface ui = new UserInterface(controller);

        ui.mainMenu();
    }
}