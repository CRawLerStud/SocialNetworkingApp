package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Long>{
    private final User user1;
    private final User user2;
    private final LocalDateTime friendsFrom;

    public Friendship(User _user1, User _user2, LocalDateTime _friendsFrom){
        this.user1 = _user1;
        this.user2 = _user2;
        this.friendsFrom = _friendsFrom;
    }

    public Friendship(User _user1, User _user2) {
        this.user1 = _user1;
        this.user2 = _user2;
        friendsFrom = LocalDateTime.now();
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        Friendship student = (Friendship) o;
        return Objects.equals(student.getId(), this.getId());
    }

    @Override
    public String toString() {
        return this.getId() + ";" + this.getUser1().getId() + ";" + this.getUser2().getId() + ";" + this.getFriendsFrom();
    }
}
