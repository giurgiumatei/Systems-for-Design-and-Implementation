package gunshop.repository;

import gunshop.domain.BaseEntity;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class FileRepository<ID,T extends BaseEntity<ID>> extends InMemoryRepository<ID,T> {

    String fileName;
    Class<T> classOfEntity;


    public FileRepository(Validator<T> validator,String fileName,Class<T> classOfEntity)
    {
        super(validator);
        this.fileName = fileName;
        this.classOfEntity=classOfEntity;
        this.loadData();
    }

    /**
     * Reads the entities from a given text file and loads them into the main memory.
     */
    public void loadData()
    {

        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {

                Class[] cArgs = new Class[1];
                cArgs[0]= String.class;

                try {
                    T entity = classOfEntity.getDeclaredConstructor(cArgs).newInstance(line);
                    this.save(entity);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            });
        }
        catch (Exception e) {
            throw new GunShopException("Could not load from Text File");
        }


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
    public Optional<T> save(T entity) throws ValidatorException {
       Optional<T> optional=super.save(entity);
       if (optional.isEmpty()) { //added
            saveToFile();
            return Optional.empty();
        }
        return optional; //duplicate element
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
        Optional<T> optional = super.delete(id);
        if (optional.isPresent()) {
            saveToFile();
            return optional;
        }
        return Optional.empty();
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
        Optional<T> optional = super.update(entity);
        if(optional.isPresent()) {
            saveToFile();
            return optional;
        }
        return Optional.empty();
    }

    /**
     * Saves all the current entities in the repository to the text file.
     */
    private void saveToFile()
    {
        Path path = Paths.get(fileName);
        /*try {
            Files.write(path, (Iterable<? extends CharSequence>) super.findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE))
        {
            super.findAll().forEach(entity->{
                try {
                    bufferedWriter.write(entity.toLine());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

