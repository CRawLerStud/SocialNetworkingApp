import controller.Controller;
import repo.friendship.FriendshipFileRepository;
import repo.users.UserFileRepository;
import service.*;
import test.Test;
import ui.UserInterface;
import validators.*;

public class Main {
    public static void main(String[] args) {

        Test.executeAll();

        UserValidator userValidator = new UserValidator();
        UserFileRepository userRepository = new UserFileRepository("data/users.csv");
        UserService userService = new UserService(userValidator, userRepository);

        FriendshipValidator friendshipValidator = new FriendshipValidator();
        FriendshipFileRepository friendshipRepository = new FriendshipFileRepository("data/friendships.csv");
        FriendshipService friendshipService = new FriendshipService(friendshipValidator, friendshipRepository);

        Controller controller = new Controller(friendshipService, userService);

        UserInterface ui = new UserInterface(controller);

        ui.mainMenu();
    }
}