package ro.ubb.socket.server.database;

import domain.Rental;
import domain.validators.RentalValidator;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;
import utils.Pair;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RentalDbRepository implements RepositoryInterface<Pair<Long,Long>, Rental> {

    private final String url;
    private final String user;
    private final String password;
    private final RentalValidator validator;

    public RentalDbRepository(String url, String user, String password, RentalValidator validator)
    {
        this.url = url;
        this.user = user;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Rental> findOne(Pair<Long, Long> longLongPair) {
        String selectStatement = "SELECT * FROM rentals WHERE clientid=? AND guntypeid=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(selectStatement)) {
            Rental rental = null;
            preparedStatement.setLong(1, longLongPair.getFirst());
            preparedStatement.setLong(2, longLongPair.getSecond());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                rental = new Rental(new Pair<Long,Long>(resultSet.getLong("clientid"),
                        resultSet.getLong("guntypeid")),
                        resultSet.getInt("price"));
            return Optional.of(rental);
        } catch (SQLException throwables) {
            System.out.println("No such rental in the database!");
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Rental> findAll() {
        Set<Rental> entities = new HashSet<>();
        try {
            String selectStatement = "SELECT * FROM rentals";
            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery()) {
                while (resultSet.next()){
                    entities.add(new Rental(new Pair<Long,Long>(resultSet.getLong("clientid"),
                            resultSet.getLong("guntypeid")),
                            resultSet.getInt("price")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        String insertStatement = "INSERT INTO rentals(clientid,guntypeid,price) VALUES (?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            preparedStatement.setLong(1, entity.getClientId());
            preparedStatement.setLong(2, entity.getGunTypeId());
            preparedStatement.setInt(3, entity.getPrice());

            preparedStatement.executeUpdate();

            return Optional.of(entity);
        } catch (SQLException throwables) {
            System.out.println("Cannot add to database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> delete(Pair<Long, Long> longLongPair) {
        Optional<Rental> rentalOptional = findOne(longLongPair);
        String deleteStatement = "DELETE FROM rentals WHERE clientid=? AND guntypeid=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, longLongPair.getFirst());
            preparedStatement.setLong(2, longLongPair.getSecond());

            preparedStatement.executeUpdate();
            return rentalOptional;
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database!");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException {
        String updateStatement = "UPDATE rentals SET price=? WHERE clientid=? and guntypeid=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setLong(2, entity.getClientId());
            preparedStatement.setLong(3, entity.getGunTypeId());

            preparedStatement.executeUpdate();

            return Optional.of(entity);
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }

        return Optional.empty();
    }
}

