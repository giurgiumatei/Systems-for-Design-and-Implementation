package gunshop.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
create table gun_types
(
    id int primary key ,
    name varchar(255),
    category varchar(255),
    providerid int references gun_providers(id)
);
 */

public class GunType extends BaseEntity<Long>{

    private String name;
    private Category category;
    private long gunProviderID;

    private static final String relationName = "gun_types";

    public GunType() {
    }

    public GunType(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public GunType(Long id, String name, Category category) {
        super(id);
        this.name = name;
        this.category = category;
    }

    public GunType(Long id, String name, Category category, long gunProviderID) {
        super(id);
        this.name = name;
        this.category = category;
        this.gunProviderID = gunProviderID;
    }

    public GunType(String parametersString)
    {
        super(Long.parseLong(Arrays.asList(parametersString.split(",")).get(0)));
        List<String> parametersList = Arrays.asList(parametersString.split(","));
        this.name=parametersList.get(1);
        this.category = Category.valueOf(parametersList.get(2));
        this.gunProviderID = Long.parseLong(parametersList.get(3));
    }

    /**
     * Gets the "name" parameter of a GunType entity.
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
     * Gets the "category" parameter of a GunType entity.
     * @return String - category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the object's "category" parameter to the given parameter value.
     * @param category - new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    public long getGunProviderID() {
        return gunProviderID;
    }

    public void setGunProviderID(long gunProviderID) {
        this.gunProviderID = gunProviderID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GunType gunType = (GunType) o;
        return gunProviderID == gunType.gunProviderID && Objects.equals(name, gunType.name) && category == gunType.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, gunProviderID);
    }

    @Override
    public String toString() {
        return "GunType{" +
                "id=" + getId() + "," +
                "name='" + name + '\'' +
                ", category=" + category +
                ", gunProviderID=" + gunProviderID +
                '}';
    }

    @Override
    public String toLine()
    {
        return this.getId() + "," + this.name + "," + this.category + "," + this.gunProviderID;
    }

    /**
     * Creates a Client from an XML node.
     * @return a new Client object.
     */
    @Override
    public GunType createFromNode(Element element){
        return new GunType(
                Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                element.getElementsByTagName("name").item(0).getTextContent(),
                Category.valueOf(element.getElementsByTagName("category").item(0).getTextContent()),
                Long.parseLong(element.getElementsByTagName("providerid").item(0).getTextContent())
        );
    }

    /**
     * Creates an XML node from a Document object.
     * @return a new node Element.
     */
    @Override
    public Element createNodeFromThis(Document document) {
        Element gunTypeElement = document.createElement("gunType");

        BaseEntity.addChildWithTextContent(document, gunTypeElement, "id", Long.toString(this.getId()));
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "name", this.name);
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "category", this.category.toString());
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "providerid", Long.toString(this.getGunProviderID()));
        return gunTypeElement;
    }

    @Override
    public void prepareInsertStatement(String databaseName, String url, String user, String password, BaseEntity<Long> entity) {
        String insertStatement = "INSERT INTO " + relationName + " (id,name,category,providerid) VALUES (?,?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            GunType gunType = (GunType) entity;

            preparedStatement.setLong(1, gunType.getId());
            preparedStatement.setString(2, gunType.name);
            preparedStatement.setString(3, gunType.category.toString());
            preparedStatement.setLong(4, gunType.gunProviderID);

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
        String updateStatement = "UPDATE " + relationName + " SET name=?,category=?,providerid=? WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            GunType gunType = (GunType) entity;

            preparedStatement.setString(1, gunType.name);
            preparedStatement.setString(2, gunType.category.toString());
            preparedStatement.setLong(3, gunType.getId());
            preparedStatement.setLong(4, gunType.getGunProviderID());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Cannot update database!");
        }
    }

    @Override
    public String getSelectStatement() {
        return "SELECT * FROM " + relationName;
    }

    @Override
    public BaseEntity<Long> createFromResultSet(ResultSet resultSet) {
        try {
            long newId = resultSet.getLong("id");
            String newName = resultSet.getString("name");
            String newCategory = resultSet.getString("category");
            long providerId = resultSet.getLong("providerid");

            return new GunType(newId,newName,Category.valueOf(newCategory),providerId);
        } catch (SQLException throwables) {
            System.out.println("Cannot select from database!");
        }
        return null;
    }
}
