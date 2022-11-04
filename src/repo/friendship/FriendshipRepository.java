package repo.friendship;

import models.Friendship;
import models.User;
import repo.InMemoryRepository;
import repo.RepositoryException;

public class FriendshipRepository extends InMemoryRepository<Long, Friendship> {

    public FriendshipRepository() {}

    /**
     * Adds a new friendship in the repository
     * @param entity the friendship added
     * @throws RepositoryException <ul>
     *     <li>if the users of the friendship are already friends</li>
     *     <li>if the friendship is already in the repository</li>
     * </ul>
     */
    @Override
    public void save(Friendship entity) throws RepositoryException {
        User user1 = entity.getUser1();
        User user2 = entity.getUser2();
        user1.addFriend(user2);
        user2.addFriend(user1);
        super.save(entity);
    }

    /**
     * Deletes a friendship from the repository
     * @param aLong friendship's id
     * @return the friendship that was deleted
     * @throws RepositoryException if the friendship is not in the repository
     */
    @Override
    public Friendship delete(Long aLong) throws RepositoryException {
        Friendship deletedFriendship = super.delete(aLong);
        User user1 = deletedFriendship.getUser1();
        User user2 = deletedFriendship.getUser2();
        user1.removeFriend(user2.getId());
        user2.removeFriend(user1.getId());
        return deletedFriendship;
    }


    /**
     * Remove a user from all of his friends' friends list
     * @param user the user that is removed from all his friends' friends list
     * @throws RepositoryException if the users are not friends(impossible)
     */
    public void removeUserFromFriends(User user) throws RepositoryException{
        Long userID = user.getId();
        Iterable<User> friends = user.allFriends();
        for(User friend : friends)
            friend.removeFriend(userID);
    }
}
