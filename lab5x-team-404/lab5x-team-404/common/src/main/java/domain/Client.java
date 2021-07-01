package domain;

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



}
