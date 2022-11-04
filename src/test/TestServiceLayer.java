package test;

import models.Friendship;
import models.User;
import repo.Repository;
import repo.RepositoryException;
import repo.friendship.FriendshipRepository;
import repo.users.UserFileRepository;
import service.FriendshipService;
import service.UserService;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.ValidationException;

import java.time.LocalDate;

public class TestServiceLayer {

    public static void executeAll(){
        testUserService();
        testFriendshipService();
        System.out.println("Service Layer Tests passed succesfully!");
    }

    private static void testFriendshipService() {
        FriendshipValidator validator = new FriendshipValidator();
        FriendshipRepository repository = new FriendshipRepository();

        FriendshipService service = new FriendshipService(validator, repository);

        User user1 = new User("Vasile", "Ionut", LocalDate.parse("1995-04-04"));
        user1.setId(1L);
        User user2 = new User("Mihai", "Traian", LocalDate.parse("1982-03-02"));
        user2.setId(2L);

        Friendship goodFriendship = new Friendship(user1, user2);
        goodFriendship.setId(1L);

        try{
            service.save(goodFriendship);
        }
        catch(ValidationException | RepositoryException error){
            assert(false);
        }

        try{
            service.save(goodFriendship);
            assert(false);
        }
        catch(RepositoryException|ValidationException error){
            assert(true);
        }

        Friendship badFriendship = new Friendship(user1, user1);

        try{
            service.save(badFriendship);
            assert(false);
        }
        catch(RepositoryException|ValidationException error){
            assert(true);
        }

        Friendship nullFriendship1 = new Friendship(null, user1);

        try{
            service.save(nullFriendship1);
            assert(false);
        }
        catch(ValidationException|RepositoryException error){
            assert(true);
        }

        Friendship nullFriendship2 = new Friendship(user1, null);

        try{
            service.save(nullFriendship2);
            assert(false);
        }
        catch(ValidationException|RepositoryException error){
            assert(true);
        }

        Friendship nullFriendship3 = new Friendship(null, null);

        try{
            service.save(nullFriendship3);
            assert(false);
        }
        catch(ValidationException|RepositoryException error){
            assert(true);
        }

        try{
            Friendship found = service.findOne(1L);
            assert(found.getId() == 1L);
        }
        catch(RepositoryException error){
            assert(false);
        }

        try{
            Friendship deleted = service.delete(1L);
            assert(deleted.getId() == 1L);
        }
        catch(RepositoryException error){
            assert(false);
        }
    }

    private static void testUserService() {

        UserValidator validator = new UserValidator();
        UserFileRepository repository = new UserFileRepository("src/test/testUsers.csv");
        UserService service = new UserService(validator, repository);

        User goodUser = new User("Tofan", "Cristian", LocalDate.parse("2002-09-20"));
        goodUser.setId(1L);

        try{
            service.save(goodUser);
        }
        catch(RepositoryException|ValidationException v){
            assert(false);
        }

        try{
            service.save(goodUser);
            assert(false);
        }
        catch(RepositoryException|ValidationException v){
            assert(true);
        }

        User invalidUser = new User("asdasd", "asdasd", LocalDate.parse("2010-01-01"));

        try{
            service.save(invalidUser);
            assert(false);
        }
        catch(RepositoryException|ValidationException v){
            assert(true);
        }

        try{
            User foundUser = service.findOne(1L);
            assert(foundUser.getLastname().equals("Tofan"));
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            service.findOne(1000L);
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            service.delete(100L);
            assert(false);
        }
        catch(RepositoryException r){
            assert(true);
        }

        try{
            User deletedUser = service.delete(1L);
            assert(deletedUser.getLastname().equals("Tofan"));
        }
        catch(RepositoryException r){
            assert(false);
        }
    }
}
