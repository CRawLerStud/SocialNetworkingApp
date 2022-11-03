package models;

import repo.FriendsList;
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

    public Iterable<User> allFriends(){
        return friends.findAll();
    }

    public void addFriend(User newFriend) throws RepositoryException{
        friends.save(newFriend);
    }

    public User removeFriend(Long ID) throws RepositoryException{
        return friends.delete(ID);
    }

    public User findFriend(Long ID) throws RepositoryException{
        return friends.findOne(ID);
    }

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
