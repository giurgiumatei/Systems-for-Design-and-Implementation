package gunshop.repository;

import gunshop.domain.BaseEntity;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID,T extends BaseEntity<ID>> implements RepositoryInterface<ID,T>
{

    private final Map<ID, T> entities;
    private final Validator<T> validator;
    public InMemoryRepository(Validator<T> validator)
    {
        this.validator=validator;
        entities=new HashMap<>();
    }

    /**
     * Saves the given entity into the repository.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws GunShopException
     *            if the id is not unique.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        if(entity == null) {
            throw new IllegalArgumentException("Entity is null!");
        }

        validator.validate(entity);

        //TODO : putIfAbsent docs
        return Optional.ofNullable(entities.putIfAbsent(entity.getId() ,entity));
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the deleted entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("Id is null!");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id doesn't exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        if(entity == null)
        {
            throw new IllegalArgumentException("Entity is null!");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k,v) -> entity));
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("Id is null!");
        }
        return Optional.ofNullable(entities.get(id));
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<T> findAll()
    {
//        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return new HashSet<>(entities.values());
    }
}
