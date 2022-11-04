package repo.friendship;

import models.Friendship;
import models.User;
import repo.InMemoryRepository;
import repo.RepositoryException;

import java.util.ArrayList;
import java.util.List;

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
        return super.delete(aLong);
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

    /**
     * Remove all user's friendships
     * @param user the user that will have all the friendships removed
     * @throws RepositoryException if the friendship is not existent
     */
    public void removeUserFriendships(User user) throws RepositoryException{
        List<Friendship> friendships = new ArrayList<>();
        for(Friendship friendship : findAll()){
            if(friendship.getUser1().equals(user) || friendship.getUser2().equals(user)) {
                friendships.add(friendship);
            }
        }

        for(Friendship friendship: friendships){
            this.delete(friendship.getId());
        }
    }
}
