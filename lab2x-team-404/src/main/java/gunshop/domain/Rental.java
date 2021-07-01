package gunshop.domain;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Pair;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
create table rentals
(
    clientId int references clients(id),
    gunTypeId int references gun_types(id),
    price int,
    PRIMARY KEY (clientId,gunTypeId)
);
 */

public class Rental extends BaseEntity<Pair<Long, Long>> {

    private int price;

    private static final String relationName = "rentals";

    public Rental() {
    }

    public Rental(int price) {
        this.price = price;
    }

    public Rental(Pair<Long, Long> longPair)
    {
        super(longPair);
    }

    public Rental(Pair<Long, Long> longPair, int price)
    {
        super(longPair);
        this.price = price;
    }

    public Rental(String parametersString)
    {
        super(new Pair<>(Long.parseLong(Arrays.asList(parametersString.split(",")).get(0)),
                Long.parseLong(Arrays.asList(parametersString.split(",")).get(1))));

        List<String> parametersList = Arrays.asList(parametersString.split(","));
        this.price=Integer.parseInt(parametersList.get(2));
    }


    /**
     * Gets the "id" parameter of the Client class that aggregates into the Rental class .
     * @return Long - id
     */
    public Long getClientId() {
        return super.getId().getFirst();
    }

    /**
     * Gets the "id" parameter of the GunType class that aggregates into the Rental class .
     * @return Long - id
     */
    public Long getGunTypeId() {
        return super.getId().getSecond();
    }

    /**
     * Gets the "price" parameter of a Rental entity.
     * @return int - price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the object's "price" parameter to the given parameter value.
     * @param price - new price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        if(getId().equals(rental.getId()))
            return price == rental.price;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId().getFirst(), super.getId().getSecond(), price);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "Client ID ='" + super.getId().getFirst() + '\'' +
                ", Gun ID ='" + super.getId().getSecond() + '\'' +
                ", price =" + this.price +
                "} ";
    }

    @Override
    public String toLine()
    {
        return this.getId().getFirst() + "," + this.getId().getSecond() + "," + this.price;
    }

    /**
     * Creates a Client from an XML node.
     * @return a new Client object.
     */
    @Override
    public Rental createFromNode(Element element){

        String[] idAsStringArray = element.getElementsByTagName("id").item(0).getTextContent()
                .replace("(","")
                .replace(")","")
                .split(",");

        return new Rental(
                new Pair<>(
                        Long.parseLong(idAsStringArray[0]),
                        Long.parseLong(idAsStringArray[1])
                ),
                Integer.parseInt(element.getElementsByTagName("price").item(0).getTextContent())
        );
    }

    /**
     * Creates an XML node from a Document object.
     * @return a new node Element.
     */
    @Override
    public Element createNodeFromThis(Document document) {
        Element rentalElement = document.createElement("rental");

        BaseEntity.addChildWithTextContent(document, rentalElement, "id",this.getId().toString());
        BaseEntity.addChildWithTextContent(document, rentalElement, "price",Integer.toString(this.price));

        return rentalElement;
    }

    @Override
    public void prepareInsertStatement(String databaseName, String url, String user, String password, BaseEntity<Pair<Long, Long>> entity) {
        String insertStatement = "INSERT INTO " + relationName + " (clientid,guntypeid,price) VALUES (?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            Rental rental = (Rental) entity;

            preparedStatement.setLong(1, rental.getId().getFirst());
            preparedStatement.setLong(2, rental.getId().getSecond());
            preparedStatement.setInt(3, rental.price);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot add to database!");
        }
    }

    @Override
    public void prepareDeleteStatement(String databaseName, String url, String user, String password, Pair<Long, Long> id) {
        String deleteStatement = "DELETE FROM " + relationName + " WHERE clientid=? AND guntypeid=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setLong(1, id.getFirst());
            preparedStatement.setLong(2, id.getSecond());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot delete from database!");
        }
    }

    @Override
    public void prepareUpdateStatement(String databaseName, String url, String user, String password, BaseEntity<Pair<Long, Long>> entity) {
        String updateStatement = "UPDATE " + relationName + " SET price=? WHERE clientid=? AND guntypeid=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            Rental rental = (Rental) entity;

            preparedStatement.setInt(1, rental.price);
            preparedStatement.setLong(2, rental.getId().getFirst());
            preparedStatement.setLong(3, rental.getId().getSecond());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }
    }

    @Override
    public String getSelectStatement() {
        return "SELECT * FROM rentals";
    }

    @Override
    public BaseEntity<Pair<Long, Long>> createFromResultSet(ResultSet resultSet) {
        try {
            long newClientId = resultSet.getLong("clientId");
            long newGunTypeId = resultSet.getLong("gunTypeId");
            int newPrice = resultSet.getInt("price");

            return new Rental(new Pair<>(newClientId,newGunTypeId),newPrice);
        } catch (SQLException throwables) {
            System.out.println("Cannot select from database!");
        }
        return null;
    }
}
