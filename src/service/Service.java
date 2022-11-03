package service;

import repo.RepositoryException;
import validators.ValidationException;

public interface Service<ID, T> {

    void save(T entity) throws RepositoryException, ValidationException;
    T delete(ID id) throws RepositoryException;
    T findOne(ID id) throws RepositoryException;
    Iterable<T> findAll();
}
