package domain;

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

}
