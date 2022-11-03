package repo;

import models.Friendship;
import models.User;

public class FriendshipRepository extends InMemoryRepository<Long, Friendship>{

    public FriendshipRepository() {}

    @Override
    public void save(Friendship entity) throws RepositoryException {
        super.save(entity);
        User user1 = entity.getUser1();
        User user2 = entity.getUser2();
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    @Override
    public Friendship delete(Long aLong) throws RepositoryException {
        Friendship deletedFriendship = super.delete(aLong);
        User user1 = deletedFriendship.getUser1();
        User user2 = deletedFriendship.getUser2();
        user1.removeFriend(user2.getId());
        user2.removeFriend(user1.getId());
        return deletedFriendship;
    }

    public void removeUserFromFriends(User user) throws RepositoryException{
        Long userID = user.getId();
        Iterable<User> friends = user.allFriends();
        for(User friend : friends)
            friend.removeFriend(userID);
    }
}
