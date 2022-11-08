package service;

import models.User;
import repo.RepositoryException;
import repo.users.UserFileRepository;
import validators.UserValidator;
import validators.ValidationException;

import java.time.LocalDate;
import java.util.List;

public class UserService extends ServiceImplementation<Long, User>{

    private final UserFileRepository repository;

    public UserService(UserValidator validator, UserFileRepository repository) {
        super(validator, repository);
        this.repository = repository;
    }

    public List<User> lastnameStartsWith(String sequence){
        return repository.lastnameStartsWith(sequence);
    }

    public List<User> surnameStartsWith(String sequence){
        return repository.surnameStartsWith(sequence);
    }

    public List<User> lastnameIs(String lastname){
        return repository.lastnameIs(lastname);
    }

    public List<User> surnameIs(String surname){
        return repository.surnameIs(surname);
    }

    public List<User> fullnameIs(String lastname, String surname){
        return repository.fullnameIs(lastname, surname);
    }

    public List<User> usersOlderThan(int minimumAge){
        return repository.usersOlderThan(minimumAge);
    }

    public User changeUserLastname(Long ID, String newLastname) throws RepositoryException, ValidationException {
        User foundUser = repository.findOne(ID);
        validator.validate(new User(newLastname, foundUser.getSurname(), foundUser.getBirthDate()));
        return repository.changeUserLastname(ID, newLastname);
    }

    public User changeUserSurname(Long ID, String newSurname) throws RepositoryException, ValidationException{
        User foundUser = repository.findOne(ID);
        validator.validate(new User(foundUser.getLastname(), newSurname, foundUser.getBirthDate()));
        return repository.changeUserSurname(ID, newSurname);
    }

    public User changeUserBirthdate(Long ID, LocalDate newBirthdate) throws RepositoryException, ValidationException{
        User foundUser = repository.findOne(ID);
        validator.validate(new User(foundUser.getLastname(), foundUser.getSurname(), newBirthdate));
        return repository.changeUserBirthday(ID, newBirthdate);
    }

}
