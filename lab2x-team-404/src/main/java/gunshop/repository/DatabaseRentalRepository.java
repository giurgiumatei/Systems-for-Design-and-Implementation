package gunshop.repository;

import gunshop.domain.BaseEntity;
import gunshop.domain.Rental;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;
import utils.Pair;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class DatabaseRentalRepository implements RepositoryInterface<Pair<Long, Long>, Rental> {

    private final Map<Pair<Long,Long>, Rental> entities;
    private final Validator<Rental> validator;
    private final String databaseName;
    private final String url;
    private final String user;
    private final String password;

    public DatabaseRentalRepository(Validator<Rental> validator, String databaseName, String url, String user, String password)
    {
        entities=new HashMap<>();
        this.validator = validator;
        this.databaseName = databaseName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        try {
            // we call the method from the entity class

            entity.prepareInsertStatement(this.databaseName,this.url,this.user,this.password,entity);

            // if update is ok we return something else than null to avoid exceptions
            return Optional.of(Rental.class.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> delete(Pair<Long,Long> id) {
        try {
            // here we use directly classOfT which will search in the class with which we initialized the repository

            Rental rental=new Rental();
            rental.prepareDeleteStatement(this.databaseName,this.url,this.user,this.password,id);

            // if delete is ok we return something else than null to avoid exceptions
            return Optional.of(Rental.class.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException {
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

            entity.prepareUpdateStatement(this.databaseName,this.url,this.user,this.password,entity);

            // if update is ok we return something else than null to avoid exceptions
            return Optional.of(Rental.class.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public Optional<Rental> findOne(Pair<Long, Long> longLongPair) {
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Rental> findAll() {
        HashSet<Rental> entities = new HashSet<>();
        try {
            String selectStatement = (String) BaseEntity.class.getDeclaredMethod("getSelectStatement").invoke(Rental.class.getDeclaredConstructor().newInstance());

            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery())
            {

                while (resultSet.next()){
                    entities.add(
                            (Rental) BaseEntity.class.getDeclaredMethod("createFromResultSet", ResultSet.class)
                                    .invoke(Rental.class.getDeclaredConstructor().newInstance(),resultSet)
                    );
                }

            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return entities;
    }

}
