package ro.ubb.socket.server.database;

import domain.GunProvider;
import domain.validators.GunProviderValidator;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GunProviderDbRepository implements RepositoryInterface<Long, GunProvider> {

    private final String url;
    private final String user;
    private final String password;
    private final GunProviderValidator validator;

    public GunProviderDbRepository(String url, String user, String password, GunProviderValidator validator) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<GunProvider> findOne(Long id) {
        String insertStatement = "SELECT * FROM gun_providers WHERE id=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {
            GunProvider gunProvider = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                gunProvider = new GunProvider(resultSet.getLong("id"),
                                      resultSet.getString("name"),
                                      resultSet.getString("speciality"),
                                      resultSet.getInt("reputation"));
            return Optional.ofNullable(gunProvider);
        } catch (SQLException throwables) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<GunProvider> findAll() {
        Set<GunProvider> entities = new HashSet<>();
        try {
            String selectStatement = "SELECT * FROM gun_providers";
            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery()) {
                while (resultSet.next()){
                    entities.add(new GunProvider(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("speciality"),
                            resultSet.getInt("reputation")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<GunProvider> save(GunProvider gunProvider) throws ValidatorException {
        String insertStatement = "INSERT INTO gun_providers (id,name,speciality,reputation) VALUES (?,?,?,?)";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {

            preparedStatement.setLong(1, gunProvider.getId());
            preparedStatement.setString(2, gunProvider.getName());
            preparedStatement.setString(3, gunProvider.getSpeciality());
            preparedStatement.setInt(4, gunProvider.getReputation());
            preparedStatement.executeUpdate();
            return Optional.of(gunProvider);
        } catch (SQLException throwables) {
            System.out.println("Cannot add to database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<GunProvider> delete(Long id) {
        Optional<GunProvider> gunProviderOptional = findOne(id);
        if (gunProviderOptional.isEmpty())
            throw new RuntimeException("No such id!");
        String deleteStatement = "DELETE FROM gun_providers WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            return gunProviderOptional;
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<GunProvider> update(GunProvider gunProvider) throws ValidatorException {
        String updateStatement = "UPDATE gun_providers SET name=?,speciality=?,reputation=? WHERE id=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(updateStatement)) {

            preparedStatement.setString(1, gunProvider.getName());
            preparedStatement.setString(2, gunProvider.getSpeciality());
            preparedStatement.setInt(3, gunProvider.getReputation());
            preparedStatement.setLong(4, gunProvider.getId());
            preparedStatement.executeUpdate();
            return Optional.of(gunProvider);
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }
        return Optional.empty();
    }
}
