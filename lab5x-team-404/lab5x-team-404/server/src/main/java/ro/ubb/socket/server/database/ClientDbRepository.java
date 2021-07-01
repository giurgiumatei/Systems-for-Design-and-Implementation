package ro.ubb.socket.server.database;

import domain.Client;
import domain.validators.ClientValidator;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientDbRepository implements RepositoryInterface<Long, Client> {

    private final String url;
    private final String user;
    private final String password;
    private final ClientValidator validator;

    public ClientDbRepository(String url, String user, String password, ClientValidator validator) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Client> findOne(Long id) {
        String insertStatement = "SELECT * FROM clients WHERE id=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {
            Client client = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                client = new Client(resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getDate("dateofbirth").toLocalDate());
            return Optional.ofNullable(client);
        } catch (SQLException throwables) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> entities = new HashSet<>();
        try {
            String selectStatement = "SELECT * FROM clients";
            try (var resultSet = DriverManager.getConnection(url,user,password)
                    .prepareStatement(selectStatement)
                    .executeQuery()) {
                while (resultSet.next()){
                    entities.add(new Client(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("dateofbirth").toLocalDate()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Optional<Client> save(Client client) throws ValidatorException {
        validator.validate(client);
        String insertStatement = "INSERT INTO clients (id,name,dateOfBirth) VALUES (?,?,?)";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {

            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.getName());
            // as found in postgres docs
            preparedStatement.setObject(3, client.getDateOfBirth());

            preparedStatement.executeUpdate();
            return Optional.empty();
        } catch (SQLException throwables) {
            return Optional.of(client);
        }
    }

    @Override
    public Optional<Client> delete(Long id) {
        Optional<Client> clientOptional = findOne(id);
        if (clientOptional.isEmpty())
            throw new RuntimeException("No such id!");
        String deleteStatement = "DELETE FROM clients WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            return clientOptional;
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database! (foreign key in general)");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        String updateStatement = "UPDATE clients SET name=?,dateOfBirth=? WHERE id=?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(updateStatement)) {

            Client client = (Client) entity;

            preparedStatement.setString(1, client.getName());
            preparedStatement.setObject(2, client.getDateOfBirth());
            preparedStatement.setLong(3, client.getId());
            preparedStatement.executeUpdate();
            return Optional.of(client);
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }
        return Optional.empty();
    }
}
