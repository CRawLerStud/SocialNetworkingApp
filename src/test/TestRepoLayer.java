package test;

import models.Friendship;
import models.User;
import repo.Repository;
import repo.RepositoryException;
import repo.friendship.FriendshipRepository;
import repo.users.UserFileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestRepoLayer {

    public static void executeAll(){
        testUserRepository();
        testFriendshipRepository();
        System.out.println("Repo Layer Tests passed succesfully...");
    }

    private static void testFriendshipRepository() {
        FriendshipRepository friendshipRepository = new FriendshipRepository();

        List<Friendship> friendships = createFriendships();

        try{
            friendshipRepository.save(friendships.get(0));
            friendshipRepository.save(friendships.get(1));
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            friendshipRepository.save(friendships.get(0));
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            friendshipRepository.delete(5L);
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            friendshipRepository.delete(1L);
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            Friendship foundFriendship = friendshipRepository.findOne(2L);
            assert(foundFriendship.getId() == 2L);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            friendshipRepository.findOne(1L);
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

    }

    private static List<Friendship> createFriendships() {
        List<Friendship> result = new ArrayList<>();

        List<User> users = createUsers();

        Friendship friendship1 = new Friendship(users.get(0), users.get(1));
        friendship1.setId(1L);
        Friendship friendship2 = new Friendship(users.get(1), users.get(2));
        friendship2.setId(2L);
        Friendship friendship3 = new Friendship(users.get(2), users.get(4));
        friendship3.setId(3L);
        Friendship friendship4 = new Friendship(users.get(3), users.get(4));
        friendship4.setId(4L);
        Friendship friendship5 = new Friendship(users.get(1), users.get(4));
        friendship5.setId(5L);

        result.add(friendship1);
        result.add(friendship2);
        result.add(friendship3);
        result.add(friendship4);
        result.add(friendship5);

        return result;

    }

    private static void testUserRepository() {
        UserFileRepository userRepository = new UserFileRepository("src/test/testUsers.csv");

        List<User> users = createUsers();

        try{
            userRepository.save(users.get(0));
            userRepository.save(users.get(1));
            userRepository.save(users.get(2));
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            userRepository.save(users.get(0));
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            userRepository.delete(10L);
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            User deletedUser = userRepository.delete(1L);
            assert(deletedUser.getId() == 1L);
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            User foundUser = userRepository.findOne(2L);
            assert(foundUser.getId() == 2L);
        }
        catch(RepositoryException r){
            assert(false);
        }

    }

    private static List<User> createUsers() {
        List<User> result = new ArrayList<>();
        User user1 = new User("Vasile", "Ionut", LocalDate.parse("1995-04-04"));
        user1.setId(1L);
        User user2 = new User("Mihai", "Traian", LocalDate.parse("1982-03-02"));
        user2.setId(2L);
        User user3 = new User("Mihut", "Marian", LocalDate.parse("1999-10-05"));
        user3.setId(3L);
        User user4 = new User("Iordache", "Mircea", LocalDate.parse("2005-03-01"));
        user4.setId(4L);
        User user5 = new User("Socea", "Marian", LocalDate.parse("2007-05-20"));
        user5.setId(5L);

        result.add(user1);
        result.add(user2);
        result.add(user3);
        result.add(user4);
        result.add(user5);

        return result;
    }
}
