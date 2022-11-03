package service;

import models.Entity;
import repo.Repository;
import repo.RepositoryException;
import validators.ValidationException;
import validators.Validator;

public class ServiceImplementation<ID, E extends Entity<ID>> implements Service<ID, E>{

    Validator<E> validator;
    Repository<ID, E> repository;

    public ServiceImplementation(Validator<E> validator, Repository<ID, E> repository) {
        this.validator = validator;
        this.repository = repository;
    }

    @Override
    public void save(E entity) throws RepositoryException, ValidationException {
        validator.validate(entity);
        repository.save(entity);
    }

    @Override
    public E delete(ID id) throws RepositoryException {
        return repository.delete(id);
    }

    @Override
    public E findOne(ID id) throws RepositoryException {
        return repository.findOne(id);
    }

    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }
}
