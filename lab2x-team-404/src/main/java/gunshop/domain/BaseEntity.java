package gunshop.domain;

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

    public void prepareInsertStatement(String databaseName, String url, String user, String password, BaseEntity<ID> entity){}

    public void prepareUpdateStatement(String databaseName, String url, String user, String password, BaseEntity<ID> entity){}

    public void prepareDeleteStatement(String databaseName, String url, String user, String password, ID id){}

    public BaseEntity<ID> createFromResultSet(ResultSet resultSet){return null;}

    public String getSelectStatement(){return null;}

    /**
     * Creates a BaseEntity from an XML node.
     * This method is intended to be used only in child objects.
     * @return null
     */
    public BaseEntity<ID> createFromNode(Element element){return null;}


    /**
     * Creates an XML node from a Document object.
     * * This method is intended to be used only in child objects.
     * @return null
     */
    public Element createNodeFromThis(Document document){return null;}




    // this method can be static and protected as it needs to be called only by it's subclasses having the same purpose
    /**
     * Adds a child node containing text content to its parent node.
     * @param document - given XML file.
     * @param parent - given parent node.
     * @param tagName - given tag name.
     * @param textContent - given text content.
     */
    protected static void addChildWithTextContent(Document document, Element parent, String tagName, String textContent){
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }
}
