package repo;

public interface Repository<ID, T> {

    void save(T entity) throws RepositoryException;
    T delete(ID id) throws RepositoryException;
    T findOne(ID id) throws RepositoryException;
    Iterable<T> findAll();
}
