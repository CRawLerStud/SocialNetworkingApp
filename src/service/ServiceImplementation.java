package service;

import models.Entity;
import repo.Repository;
import repo.RepositoryException;
import validators.ValidationException;
import validators.Validator;

public class ServiceImplementation<ID, E extends Entity<ID>> implements Service<ID, E>{

    protected Validator<E> validator;
    protected Repository<ID, E> repository;

    public ServiceImplementation(Validator<E> validator, Repository<ID, E> repository) {
        this.validator = validator;
        this.repository = repository;
    }

    /**
     * Validates a entity and adds it in the repository
     * @param entity the entity that will be added
     * @throws RepositoryException if the entity is already in the repository
     * @throws ValidationException if the entity is not valid
     */
    @Override
    public void save(E entity) throws RepositoryException, ValidationException {
        validator.validate(entity);
        repository.save(entity);
    }

    /**
     * Remove a entity from the repository
     * @param id entity's id
     * @return the entity that was removed
     * @throws RepositoryException if the entity is not in the repository
     */
    @Override
    public E delete(ID id) throws RepositoryException {
        return repository.delete(id);
    }

    /**
     * Search for a entity in the repository
     * @param id entity's id
     * @return the looked up entity
     * @throws RepositoryException if the entity is not in the repository
     */
    @Override
    public E findOne(ID id) throws RepositoryException {
        return repository.findOne(id);
    }

    /**
     * Returns a iterable with all the users from the repository
     * @return a iterable with all the users from the repository
     */
    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }
}
