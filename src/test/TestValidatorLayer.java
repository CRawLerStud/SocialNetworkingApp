package test;

import models.Friendship;
import models.User;
import validators.FriendshipValidator;
import validators.UserValidator;
import validators.ValidationException;

import java.time.LocalDate;

public class TestValidatorLayer {

    public static void executeAll(){
        testUserValidator();
        testFriendshipValidator();
        System.out.println("Validator Layer Tests passed succesfully!");
    }

    private static void testFriendshipValidator() {

        FriendshipValidator validator = new FriendshipValidator();

        User user1 = new User("Tofan", "Cristian", LocalDate.parse("2002-09-20"));
        User user2 = new User("Tofan", "Elena", LocalDate.parse("2005-02-06"));

        Friendship badFriendship = new Friendship(user1, user1);

        try{
            validator.validate(badFriendship);
            assert(false);
        }
        catch(ValidationException v){
            assert(true);
        }

        Friendship goodFriendship = new Friendship(user1, user2);

        try{
            validator.validate(goodFriendship);
        }
        catch(ValidationException v){
            assert(false);
        }

        Friendship nullFriendship = new Friendship(null, user2);

        try{
            validator.validate(nullFriendship);
            assert(false);
        }
        catch(ValidationException v){
            assert(true);
        }

    }

    private static void testUserValidator() {

        UserValidator validator = new UserValidator();

        User goodUser = new User("Tofan", "Cristian", LocalDate.parse("2002-09-20"));
        try{
            validator.validate(goodUser);
        }
        catch(ValidationException v){
            assert(false);
        }

        String badLastname = "123123asdasd";
        goodUser.setLastname(badLastname);

        try{
            validator.validate(goodUser);
            assert(false);
        }
        catch(ValidationException v){
            assert(true);
        }

        goodUser.setLastname("Tofan");
        String badSurname = "asdasd";
        goodUser.setSurname(badSurname);

        try{
            validator.validate(goodUser);
            assert(false);
        }
        catch(ValidationException v){
            assert(true);
        }

        goodUser.setSurname("Raul");
        String badDate = "2014-09-20";
        goodUser.setBirthDate(LocalDate.parse(badDate));

        try{
            validator.validate(goodUser);
            assert(false);
        }
        catch(ValidationException v){
            assert(true);
        }
    }
}
