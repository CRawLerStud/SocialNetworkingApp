package controller;

import models.Friendship;
import models.User;
import repo.RepositoryException;
import service.FriendshipService;
import service.UserService;
import validators.ValidationException;

public class Controller {

    private final FriendshipService friendships;
    private final UserService users;

    public Controller(FriendshipService friendships, UserService users) {
        this.friendships = friendships;
        this.users = users;
    }

    public void addUser(User user1) throws RepositoryException, ValidationException {
        users.save(user1);
    }

    public User removeUser(Long ID) throws RepositoryException{
        User removedUser = users.findOne(ID);
        friendships.removeUserFromFriends(removedUser);
        users.delete(ID);
        return removedUser;
    }

    public User findUser(Long ID) throws RepositoryException{
        return users.findOne(ID);
    }

    public Iterable<User> allUsers(){
        return users.findAll();
    }

    public void addFriendship(Friendship friendship) throws ValidationException, RepositoryException{
        friendships.save(friendship);
    }

    public Friendship removeFriendship(Long ID) throws RepositoryException{
        return friendships.delete(ID);
    }

    public Friendship findFriendship(Long ID) throws RepositoryException{
        return friendships.findOne(ID);
    }

    public Iterable<Friendship> allFriendships(){
        return friendships.findAll();
    }

}
