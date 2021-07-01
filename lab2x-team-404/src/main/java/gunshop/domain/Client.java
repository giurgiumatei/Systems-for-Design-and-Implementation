package gunshop.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
create table clients
(
    id int primary key ,
    name varchar(255),
    dateOfBirth Date
);
 */

/**
 * Client class
 */
public class Client extends BaseEntity<Long>{

    private String name;
    private LocalDate dateOfBirth;

    private static final String relationName = "clients";

    public Client() {
    }

    public Client(long id, String name, LocalDate dateOfBirth) {
        super(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Client(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Client(String parametersString)
    {
        super(Long.parseLong(Arrays.asList(parametersString.split(",")).get(0)));
        List<String> parametersList = Arrays.asList(parametersString.split(","));
        this.name=parametersList.get(1);
        var date=parametersList.get(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        this.dateOfBirth= LocalDate.parse(date, formatter);
    }


    /**
     * Gets the "name" parameter of a Client entity.
     * @return String - name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the object's "name" parameter to the given parameter value.
     * @param name - new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the "dateOfBirth" parameter of a Client entity.
     * @return LocalDate - dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the object's "dateOfBirth" parameter to the given parameter value.
     * @param dateOfBirth - new dateOfBirth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(dateOfBirth, client.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + this.getId() + '\'' +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public String toLine()
    {
        return this.getId() + "," + this.name + "," + this.dateOfBirth.getDayOfMonth() + "/" + this.dateOfBirth.getMonthValue() + "/" + this.dateOfBirth.getYear();
    }
    /**
     * Creates a Client from an XML node.
     * @return a new Client object.
     */
    @Override
    public Client createFromNode(Element element){
        return new Client(
                Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                element.getElementsByTagName("name").item(0).getTextContent(),
                LocalDate.parse(element.getElementsByTagName("dateOfBirth").item(0).getTextContent(), DateTimeFormatter.ofPattern("d/M/yyyy"))
        );
    }

    @Override
    public void prepareInsertStatement(String databaseName, String url, String user, String password, BaseEntity<Long> entity) {
        String insertStatement = "INSERT INTO " + relationName + " (id,name,dateOfBirth) VALUES (?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            Client client = (Client) entity;

            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.name);
            // as found in postgres docs
            preparedStatement.setObject(3, client.dateOfBirth);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot add to database!");
        }
    }

    @Override
    public void prepareDeleteStatement(String databaseName, String url, String user, String password, Long id) {
        String deleteStatement = "DELETE FROM " + relationName + " WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database!");
        }
    }

    @Override
    public void prepareUpdateStatement(String databaseName, String url, String user, String password, BaseEntity<Long> entity) {
        String updateStatement = "UPDATE " + relationName + " SET name=?,dateOfBirth=? WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            Client client = (Client) entity;

            preparedStatement.setString(1, client.name);
            // as found in postgres docs
            preparedStatement.setObject(2, client.dateOfBirth);
            preparedStatement.setLong(3, client.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }
    }

    @Override
    public String getSelectStatement() {
        return "SELECT * FROM clients";
    }

    @Override
    public BaseEntity<Long> createFromResultSet(ResultSet resultSet) {
        try {
            long newId = resultSet.getLong("id");
            String newName = resultSet.getString("name");
            LocalDate newDate = resultSet.getDate("dateOfBirth").toLocalDate();

            return new Client(newId,newName,newDate);
        } catch (SQLException throwables) {
            System.out.println("Cannot get elements!");
        }
        return null;
    }

    /**
     * Creates an XML node from a Document object.
     * @return a new node Element.
     */
    @Override
    public Element createNodeFromThis(Document document) {
        Element clientElement = document.createElement("client");

        BaseEntity.addChildWithTextContent(document, clientElement, "id", Long.toString(this.getId()));
        BaseEntity.addChildWithTextContent(document, clientElement, "name", this.name);
        BaseEntity.addChildWithTextContent(document, clientElement, "dateOfBirth", this.dateOfBirth.format(DateTimeFormatter.ofPattern("d/M/yyyy")));

        return clientElement;
    }



}
