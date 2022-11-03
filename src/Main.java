import controller.Controller;
import models.User;
import repo.FriendshipRepository;
import repo.RepositoryException;
import repo.UserFileRepository;
import service.FriendshipService;
import service.UserService;
import ui.UserInterface;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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