package gunshop.repository;

import gunshop.domain.BaseEntity;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseRepository<ID,T extends BaseEntity<ID>> extends InMemoryRepository<ID,T> {

    private final Class<T> classOfT;
    private final Class<ID> classOfID;
    private final String databaseName;
    private final String url;
    private final String user;
    private final String password;

    public DatabaseRepository(Validator<T> validator, String databaseName, Class<T> type, Class<ID> idType, String url, String user, String password) {
        super(validator);
        this.databaseName = databaseName;
        classOfT = type;
        classOfID = idType;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        try {
            // we call the method from the entity class
            BaseEntity.class.getDeclaredMethod("prepareInsertStatement", String.class,String.class,String.class,String.class,BaseEntity.class)
                    .invoke(
                            entity,
                            this.databaseName,
                            this.url,
                            this.user,
                            this.password,
                            entity
                    );

            // if update is ok we return something else than null to avoid exceptions
            return Optional.of(classOfT.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(ID id) {
        try {
            // here we use directly classOfT which will search in the class with which we initialized the repository
            classOfT.getDeclaredMethod("prepareDeleteStatement", String.class,String.class,String.class,String.class, classOfID)
                    .invoke(
                            classOfT.getDeclaredConstructor().newInstance(),
                            this.databaseName,
                            this.url,
                            this.user,
                            this.password,
                            id
                    );

            // if delete is ok we return something else than null to avoid exceptions
            return Optional.of(classOfT.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        try {
            // we call the method from the entity class
            BaseEntity.class.getDeclaredMethod("prepareUpdateStatement", String.class,String.class,String.class,String.class, BaseEntity.class)
                    .invoke(
                            entity,
                            this.databaseName,
                            this.url,
                            this.user,
                            this.password,
                            entity
                    );

            // if update is ok we return something else than null to avoid exceptions
            return Optional.of(classOfT.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        HashSet<T> entities = new HashSet<>();
        try {
            String selectStatement = (String) BaseEntity.class.getDeclaredMethod("getSelectStatement").invoke(classOfT.getDeclaredConstructor().newInstance());

            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery())
            {

                while (resultSet.next()){
                    entities.add(
                            (T) BaseEntity.class.getDeclaredMethod("createFromResultSet", ResultSet.class)
                                    .invoke(classOfT.getDeclaredConstructor().newInstance(),resultSet)
                    );
                }

            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        if(id == null)
        {
            throw new IllegalArgumentException("Id is null!");
        }

        Set<T> entities = (Set<T>) findAll();


       return entities.stream().filter(t -> t.getId() == id).findAny();


    }
}

