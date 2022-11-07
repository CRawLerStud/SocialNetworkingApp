package repo;

import models.Entity;

import java.util.HashMap;

public abstract class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E>{

    private final HashMap<ID, E> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    /**
     * Adds an entity in the repository
     * @param entity the entity that will be added
     * @throws RepositoryException <ul>
     *     <li>the entity is null</li>
     *     <li>the entity is in the repository</li>
     * </ul>
     */
    @Override
    public void save(E entity) throws RepositoryException{
        if(entity == null)
            throw new IllegalArgumentException("Entity is null!");
        if(entities.containsKey(entity.getId()))
            throw new RepositoryException("Entity already in repo!");
        entities.put(entity.getId(), entity);
    }

    /**
     * Removes an entity from the repository
     * @param id  the entity's id
     * @return the entity that was removed
     * @throws RepositoryException <ul>
     *     <li>if the id is null</li>
     *     <li>if the entity is not in the repository</li>
     * </ul>
     */
    @Override
    public E delete(ID id) throws RepositoryException{
        if(id == null)
            throw new IllegalArgumentException("Id is null!");
        if(!entities.containsKey(id))
            throw new RepositoryException("Entity is not existent!");
        return entities.remove(id);
    }

    /**
     * Search for the entity with id ID from the repository
     * @param id the entity's id that is looked up
     * @return the entity with id ID from repository
     * @throws RepositoryException <ul>
     *     <li>if id is null</li>
     *     <li>if the id is not in the repository</li>
     * </ul>
     */
    @Override
    public E findOne(ID id) throws RepositoryException{
        if(id == null)
            throw new IllegalArgumentException("Id is null!");
        if(!entities.containsKey(id))
            throw new RepositoryException("Entity is not existent!");
        return entities.get(id);
    }

    /**
     * Returns a list with all the entities
     * @return the list with all the entities
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
}
