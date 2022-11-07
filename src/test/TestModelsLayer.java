package test;

import models.Entity;
import models.Friendship;
import models.User;

import java.time.LocalDate;
import java.util.Objects;

public class TestModelsLayer {

    public static void executeAll(){
        testEntity();
        testUser();
        testFriendship();
        System.out.println("Domain Layer Tests passed succesfully...");
    }

    private static void testFriendship() {
        User friend1 = new User("Tofan", "Raul", LocalDate.parse("2002-09-20"));
        User friend2 = new User("Fron", "Mara", LocalDate.parse("2002-08-03"));
        Friendship friendship = new Friendship(friend1, friend2);

        assert(friendship.getUser1().getLastname().equals("Tofan"));
        assert(friendship.getUser2().getLastname().equals("Fron"));

        Long ID = 1L;
        friendship.setId(ID);

        assert(Objects.equals(friendship.getId(), ID));

        assert(friendship.toString().equals("1;null;Tofan;Raul;2002-09-20;null;Fron;Mara;2002-08-03"));

        Friendship sameFriendship = new Friendship(friend2, friend2);
        sameFriendship.setId(ID);

        assert(sameFriendship.equals(friendship));
    }

    private static void testUser(){
        User user1 = new User("Tofan", "Cristian Raul", LocalDate.parse("2002-09-20"));
        Long ID = 1L;
        user1.setId(ID);

        assert(Objects.equals(user1.getId(), ID));
        assert(user1.getSurname().equals("Cristian Raul"));
        assert(user1.getLastname().equals("Tofan"));
        assert(user1.getYears() == 20);

        String newLastname = "Traian";
        String newSurname = "Vasile";
        LocalDate newDate = LocalDate.parse("1974-02-03");
        user1.setLastname(newLastname);
        user1.setSurname(newSurname);
        user1.setBirthDate(newDate);

        assert(user1.getLastname().equals(newLastname));
        assert(user1.getSurname().equals(newSurname));
        assert(user1.getBirthDate().equals(newDate));

        assert(user1.toString().equals("1;Traian;Vasile;1974-02-03"));

        User user2 = new User("Vasile", "Ionut", LocalDate.parse("2000-01-01"));
        user2.setId(ID);

        assert(user2.equals(user1));
    }

    private static void testEntity(){
        Entity<Long> entity = new Entity<>();
        Long ID = 123456L;
        entity.setId(ID);
        assert(Objects.equals(entity.getId(), ID));
        ID = 345678L;
        entity.setId(ID);
        assert(Objects.equals(entity.getId(), ID));
    }
}
