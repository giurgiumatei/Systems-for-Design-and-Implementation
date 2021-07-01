package ro.ubb.socket.server.database;

import domain.Category;
import domain.GunType;
import domain.validators.GunTypeValidator;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GunTypeDbRepository implements RepositoryInterface<Long, GunType> {

    private final String url;
    private final String user;
    private final String password;
    private final GunTypeValidator validator;

    public GunTypeDbRepository(String url, String user, String password, GunTypeValidator validator) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Optional<GunType> findOne(Long aLong) {
        String selectStatement = "SELECT * FROM gun_types WHERE id=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(selectStatement)) {
            GunType gunType = null;
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                gunType = new GunType(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        Category.valueOf( resultSet.getString("category")),
                        resultSet.getLong("providerid"));
            return Optional.of(gunType);
        } catch (SQLException throwables) {
            System.out.println("No such gun type in the database!");
        }
        return Optional.empty();
    }

    @Override
    public Iterable<GunType> findAll() {
        Set<GunType> entities = new HashSet<>();
        try {
            String selectStatement = "SELECT * FROM gun_types";
            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery()) {
                while (resultSet.next()){
                    entities.add(new GunType(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            Category.valueOf( resultSet.getString("category")),
                            resultSet.getLong("providerid")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<GunType> save(GunType entity) throws ValidatorException {
        String insertStatement = "INSERT INTO gun_types(id,name,category,providerid) VALUES (?,?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            GunType gunType = (GunType) entity;

            preparedStatement.setLong(1, gunType.getId());
            preparedStatement.setString(2, gunType.getName());
            preparedStatement.setString(3, gunType.getCategory().toString());
            preparedStatement.setLong(4, gunType.getGunProviderID());

            preparedStatement.executeUpdate();

            return Optional.of(gunType);
        } catch (SQLException throwables) {
            System.out.println("Cannot add to database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<GunType> delete(Long aLong) {
        Optional<GunType> gunTypeOptional = findOne(aLong);
        String deleteStatement = "DELETE FROM gun_types WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, aLong);

            preparedStatement.executeUpdate();
            return gunTypeOptional;
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<GunType> update(GunType entity) throws ValidatorException {
        String updateStatement = "UPDATE gun_types SET name=?,category=?,providerid=? WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getCategory().toString());
            preparedStatement.setLong(3, entity.getGunProviderID());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();

            return Optional.of(entity);
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }

        return Optional.empty();
    }
}
