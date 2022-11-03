package service;

import models.Friendship;
import models.User;
import repo.FriendshipRepository;
import repo.RepositoryException;
import validators.FriendshipValidator;


public class FriendshipService extends ServiceImplementation<Long, Friendship>{

    FriendshipRepository repository;

    public FriendshipService(FriendshipValidator validator, FriendshipRepository repository) {
        super(validator, repository);
        this.repository = repository;
    }

    public void removeUserFromFriends(User user) throws RepositoryException{
        repository.removeUserFromFriends(user);
    }
}

