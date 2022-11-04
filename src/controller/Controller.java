package controller;

import models.Friendship;
import models.User;
import repo.RepositoryException;
import service.FriendshipService;
import service.UserService;
import utils.CommunityUtils;
import validators.ValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Controller {

    private final FriendshipService friendships;
    private final UserService users;

    public Controller(FriendshipService friendships, UserService users) {
        this.friendships = friendships;
        this.users = users;
        String friendshipsFilename = "data/friendships.csv";
        loadFriendshipsFile(friendshipsFilename);
    }

    /**
     * Loads multiple friendships from path filename
     * @param filename the path of the file that contains all the friendships
     */
    private void loadFriendshipsFile(String filename){
        Path path = Paths.get(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            for(String line : lines){
                String[] words = line.split(";");
                Long ID = Long.parseLong(words[0]);
                Long firstUserID = Long.parseLong(words[1]);
                Long secondUserID = Long.parseLong(words[2]);
                User user1 = users.findOne(firstUserID);
                User user2 = users.findOne(secondUserID);
                Friendship newFriendship = new Friendship(user1, user2);
                newFriendship.setId(ID);
                friendships.save(newFriendship);
            }
        }
        catch(IOException | RepositoryException | ValidationException e) {
            System.err.printf("Error while reading from friends file!\n%s\n", e.getMessage());
        }
    }

    /**
     * Adds a new user in the users' repository
     * @param user1 the user that is added in the repository
     * @throws RepositoryException if the user is already in the repository
     * @throws ValidationException if the user is not valid
     */
    public void addUser(User user1) throws RepositoryException, ValidationException {
        users.save(user1);
    }

    /**
     * Removes a user from the users' repository
     * @param ID user's id
     * @return the user that was removed
     * @throws RepositoryException if the user is not in the users' repository
     */
    public User removeUser(Long ID) throws RepositoryException{
        User removedUser = users.findOne(ID);
        friendships.removeUserFromFriends(removedUser);
        friendships.removeUserFriendships(removedUser);
        users.delete(ID);
        return removedUser;
    }

    /**
     * Finds a user by its ID
     * @param ID user's ID
     * @return the user that is looked up
     * @throws RepositoryException if the user is not in the users' repository
     */
    public User findUser(Long ID) throws RepositoryException{
        return users.findOne(ID);
    }

    /**
     * Returns all the users from users' repository
     * @return all the users
     */
    public Iterable<User> allUsers(){
        return users.findAll();
    }

    /**
     * Adds a new friendship in the friendships' repository
     * @param friendship the new friendship
     * @throws ValidationException if the friendship is not valid
     * @throws RepositoryException if the friendship is already in friendships' repository
     */
    public void addFriendship(Friendship friendship) throws ValidationException, RepositoryException{
        friendships.save(friendship);
    }

    /**
     * Remove a friendship from the friendships' repository
     * @param ID the removed friendship's id
     * @return the removed friendship
     * @throws RepositoryException if the friendship is not in the friendships' repository
     */
    public Friendship removeFriendship(Long ID) throws RepositoryException{
        return friendships.delete(ID);
    }

    /**
     * Finds a friendship by its ID
     * @param ID friendship's ID
     * @return the friendship with the id ID
     * @throws RepositoryException if the ID is not in the friendship repository
     */
    public Friendship findFriendship(Long ID) throws RepositoryException{
        return friendships.findOne(ID);
    }

    /**
     * Returns an iterable with all friendships
     * @return all friendships
     */
    public Iterable<Friendship> allFriendships(){
        return friendships.findAll();
    }

    /**
     * Discovers all the communities(connected components) from the social networking(graph made of users and friendships)
     * @return all the communities ( list of communities, where community is a list of users)
     */
    public List<List<User>> discoverCommunities(){
        return CommunityUtils.discoverCommunities(users.findAll());
    }

    /**
     * Discovers the most sociable community(the connected component with the longest path)
     * @return the most sociable community(List of users)
     */
    public List<User> mostSociableCommunity(){
        return CommunityUtils.mostSociableCommunity(users.findAll());
    }

}
