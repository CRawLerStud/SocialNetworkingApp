package repo;

import models.Entity;

import java.util.HashMap;

public abstract class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E>{

    private final HashMap<ID, E> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public void save(E entity) throws RepositoryException{
        if(entity == null)
            throw new IllegalArgumentException("Entity is null!");
        if(entities.containsKey(entity.getId()))
            throw new RepositoryException("User already in repo!");
        entities.put(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) throws RepositoryException{
        if(id == null)
            throw new IllegalArgumentException("Id is null!");
        if(!entities.containsKey(id))
            throw new RepositoryException("User is not existent!");
        return entities.remove(id);
    }

    @Override
    public E findOne(ID id) throws RepositoryException{
        if(id == null)
            throw new IllegalArgumentException("Id is null!");
        if(!entities.containsKey(id))
            throw new RepositoryException("User is not existent!");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
}
