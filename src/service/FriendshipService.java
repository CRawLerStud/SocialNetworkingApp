package service;

import models.Friendship;
import models.User;
import repo.friendship.FriendshipRepository;
import repo.RepositoryException;
import validators.FriendshipValidator;

public class FriendshipService extends ServiceImplementation<Long, Friendship>{

    FriendshipRepository repository;

    public FriendshipService(FriendshipValidator validator, FriendshipRepository repository) {
        super(validator, repository);
        this.repository = repository;
    }

    /**
     * Remove a user from all of his friends' friends list
     * @param user the user that is removed
     * @throws RepositoryException if the users are not friends
     */
    public void removeUserFromFriends(User user) throws RepositoryException{
        repository.removeUserFromFriends(user);
    }

    /**
     * Remove all user's friendships
     * @param user the user that will have all the friendships removed
     * @throws RepositoryException if the friendship is not existent
     */
    public void removeUserFriendships(User user) throws RepositoryException{
        repository.removeUserFriendships(user);
    }
}

