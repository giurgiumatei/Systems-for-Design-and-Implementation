package domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BaseEntity<ID> {

    private ID id;

    public BaseEntity() {
    }

    public BaseEntity(ID id) {
        this.id = id;
    }

    /**
     * Gets the id.
     * @return ID - id
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets the id to a given parameter value.
     * @param id =new id
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * Acts as a toString() for writing in file.
     * @return String - id.toString()
     */
    public String toLine()
    {
        return id.toString();
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }





}
