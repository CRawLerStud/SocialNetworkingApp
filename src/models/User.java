package models;

import repo.friendship.FriendsList;
import repo.RepositoryException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class User extends Entity<Long>{
    private String lastname;
    private String surname;
    private LocalDate birthDate;
    private final FriendsList friends;

    public User(String lastname, String surname, LocalDate birthDate) {
        this.lastname = lastname;
        this.surname = surname;
        this.birthDate = birthDate;
        this.friends = new FriendsList();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns an iterable with all the user's friends
     * @return all the user's friends
     */
    public Iterable<User> allFriends(){
        return friends.findAll();
    }

    /**
     * Adds a new friend in the user's friend list
     * @param newFriend the new friend
     * @throws RepositoryException if the new friend is already in the user's friends list
     */
    public void addFriend(User newFriend) throws RepositoryException{
        friends.save(newFriend);
    }

    /**
     * Remove the User with the id ID from the user's friends list
     * @param ID the ID of the user that will be removed
     * @return the user that was removed
     * @throws RepositoryException if the ID is not in the user's friends list
     */
    public User removeFriend(Long ID) throws RepositoryException{
        return friends.delete(ID);
    }

    /**
     * Returns the user with the id ID from the user's friends list
     * @param ID the ID of the user that is looked up
     * @return the user with the id ID from friends list
     * @throws RepositoryException if the ID is not found in the user's friends list
     */
    public User findFriend(Long ID) throws RepositoryException{
        return friends.findOne(ID);
    }

    /**
     * Calculate and return the age of the user
     * @return the age of the user
     */
    public int getYears(){
        return Math.abs(Period.between(birthDate, LocalDate.now()).getYears());
    }

    @Override
    public String toString() {
        return "The user " + this.getId() + " " + this.getLastname() + " " + this.getSurname() + " " + this.getYears() + " years old";
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        User student = (User) o;
        return Objects.equals(student.getId(), this.getId());
    }
}
