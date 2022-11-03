package service;

import models.Friendship;
import repo.FriendshipRepository;
import validators.FriendshipValidator;


public class FriendshipService extends ServiceImplementation<Long, Friendship>{

    public FriendshipService(FriendshipValidator validator, FriendshipRepository repository) {
        super(validator, repository);
    }
}

