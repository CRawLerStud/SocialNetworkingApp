package models;

public class Friendship extends Entity<Long>{
    private final User user1;
    private final User user2;

    public Friendship(User _user1, User _user2) {
        this.user1 = _user1;
        this.user2 = _user2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
}
