package service;

import models.User;
import repo.users.UserFileRepository;
import validators.UserValidator;

public class UserService extends ServiceImplementation<Long, User>{

    public UserService(UserValidator validator, UserFileRepository repository) {
        super(validator, repository);
    }
}
