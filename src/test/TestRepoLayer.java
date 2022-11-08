package test;

import models.Friendship;
import models.User;
import repo.RepositoryException;
import repo.friendship.FriendshipRepository;
import repo.users.UserFileRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestRepoLayer {

    public static void executeAll(){
        testUserRepository();
        testFriendshipRepository();
        testFilterOperationsUserRepository();
        testFriends();
        System.out.println("Repo Layer Tests passed succesfully...");
    }

    private static void testFriends() {

        User user1 = new User("Tofan", "Cristian Raul", LocalDate.of(2002, 9, 20));
        user1.setId(1L);

        User user2 = new User("Tofan", "Mara Ilinca", LocalDate.of(2002, 8, 3));
        user2.setId(2L);

        try {
            user1.addFriend(user2);
            assert (user1.findFriend(2L) != null);
        }
        catch(RepositoryException r){
            assert(false);
        }

        try{
            User removedUser = user1.removeFriend(2L);
            assert(removedUser.equals(user2));
        }
        catch(RepositoryException r){
            assert(false);
        }

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

        try {
            FileWriter fw = new FileWriter("src/test/testUsers.csv");

            fw.write("");

            fw.close();
        }
        catch(IOException e){
            System.out.println("eroare la stergerea din fisier -test repo");
        }

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

    private static void testFilterOperationsUserRepository(){

        UserFileRepository repository = new UserFileRepository("src/test/testUsersFilters.csv");

        User user1 = new User("Tofan", "Cristian", LocalDate.parse("2002-09-20"));
        user1.setId(1L);
        User user2 = new User("Tofan", "Elena", LocalDate.parse("2008-02-06"));
        user2.setId(2L);
        User user3 = new User("Traian", "Cristian", LocalDate.parse("2006-03-15"));
        user3.setId(3L);
        User user4 = new User("Fron", "Mara", LocalDate.parse("2002-08-03"));
        user4.setId(4L);
        User user5 = new User("Fronea", "Maria", LocalDate.parse("1996-01-20"));
        user5.setId(5L);


        List<User> testSurnameIs = repository.surnameIs("Cristian");
        assert(testSurnameIs.size() == 2);
        assert(testSurnameIs.get(0).getSurname().equals("Cristian"));
        assert(testSurnameIs.get(0).getLastname().equals("Tofan"));
        assert(testSurnameIs.get(1).getSurname().equals("Cristian"));
        assert(testSurnameIs.get(1).getLastname().equals("Traian"));

        List<User> testLastnameIs = repository.lastnameIs("Tofan");
        assert(testLastnameIs.size() == 2);
        assert(testLastnameIs.get(0).getLastname().equals("Tofan"));
        assert(testLastnameIs.get(1).getLastname().equals("Tofan"));

        List<User> testFullnameIs = repository.fullnameIs("Tofan","Cristian");
        assert(testFullnameIs.size() == 1);
        assert(testFullnameIs.get(0).getLastname().equals("Tofan"));
        assert(testFullnameIs.get(0).getSurname().equals("Cristian"));

        List<User> testLastnameStartsWith = repository.lastnameStartsWith("Fron");
        assert(testLastnameStartsWith.size() == 2);
        assert(testLastnameStartsWith.get(0).equals(user4));
        assert(testLastnameStartsWith.get(1).equals(user5));

        List<User> testSurnameStartsWith = repository.surnameStartsWith("Mar");
        assert(testSurnameStartsWith.size() == 2);
        assert(testSurnameStartsWith.get(0).equals(user4));
        assert(testSurnameStartsWith.get(1).equals(user5));

        List<User> testUsersOlderThan = repository.usersOlderThan(18);
        assert(testUsersOlderThan.size() == 3);
        assert(testUsersOlderThan.get(0).equals(user1));
        assert(testUsersOlderThan.get(1).equals(user4));
        assert(testUsersOlderThan.get(2).equals(user5));

        try {
            User modifiedUser = repository.changeUserLastname(1L, "Tofanel");
            assert (modifiedUser.getLastname().equals("Tofanel"));

            repository.changeUserLastname(1L, "Tofan"); //reverse action for above

            modifiedUser = repository.changeUserSurname(4L, "Mara Ilinca");
            assert (modifiedUser.getSurname().equals("Mara Ilinca"));

            repository.changeUserSurname(4L, "Mara"); //reverse action for above

            modifiedUser = repository.changeUserBirthday(3L, LocalDate.parse("2000-01-01"));
            assert (modifiedUser.getBirthDate().equals(LocalDate.parse("2000-01-01")));

            repository.changeUserBirthday(3L, LocalDate.parse("2006-03-15")); //reverse action for above
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
