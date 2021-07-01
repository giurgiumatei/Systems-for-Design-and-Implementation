package gunshop.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*create table gun_providers(
        id int primary key,
        name varchar(255),
        speciality varchar(255),
        reputation int
        );*/

public class GunProvider extends BaseEntity<Long>
{

    private String name;
    private String speciality;
    private int reputation;
    private static final String relationName = "gun_providers";

    public GunProvider() { }

    public GunProvider(String name, String speciality, int reputation) {
        this.name = name;
        this.speciality = speciality;
        this.reputation = reputation;
    }

    public GunProvider(Long aLong, String name, String speciality, int reputation) {
        super(aLong);
        this.name = name;
        this.speciality = speciality;
        this.reputation = reputation;
    }

    public GunProvider(String parametersString)
    {
        super(Long.parseLong(Arrays.asList(parametersString.split(",")).get(0)));
        List<String> parametersList = Arrays.asList(parametersString.split(","));
        this.name=parametersList.get(1);
        this.speciality=parametersList.get(2);
        this.reputation=Integer.parseInt(parametersList.get(3));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GunProvider provider = (GunProvider) o;
        return Objects.equals(name, provider.name) && Objects.equals(speciality, provider.speciality) && Objects.equals(reputation,provider.reputation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, speciality,reputation);
    }

    @Override
    public String toString() {
        return "GunProvider{" +
                "id='" + this.getId() + '\'' +
                "name='" + name + '\'' +
                ", speciality=" + speciality +
                ", reputation=" + reputation +
                '}';
    }

    @Override
    public String toLine()
    {
        return this.getId() + "," + this.name + "," + this.speciality + "," + this.reputation;
    }
    /**
     * Creates a GunProvider from an XML node.
     * @return a new GunProvider object.
     */
    @Override
    public GunProvider createFromNode(Element element) {
        return new GunProvider(
                Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                element.getElementsByTagName("name").item(0).getTextContent(),
                element.getElementsByTagName("speciality").item(0).getTextContent(),
                Integer.parseInt(element.getElementsByTagName("reputation").item(0).getTextContent()));
    }

    /**
     * Creates an XML node from a Document object.
     * @return a new node Element.
     */
    @Override
    public Element createNodeFromThis(Document document) {
        Element gunTypeElement = document.createElement("gunProvider");

        BaseEntity.addChildWithTextContent(document, gunTypeElement, "id", Long.toString(this.getId()));
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "name", this.name);
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "speciality", this.speciality);
        BaseEntity.addChildWithTextContent(document, gunTypeElement, "reputation", Integer.toString(reputation));
        return gunTypeElement;
    }

    @Override
    public void prepareInsertStatement(String databaseName, String url, String user, String password, BaseEntity<Long> entity) {
        String insertStatement = "INSERT INTO " + relationName + " (id,name,speciality,reputation) VALUES (?,?,?,?)";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(insertStatement)) {

            GunProvider gunProvider = (GunProvider) entity;

            preparedStatement.setLong(1, gunProvider.getId());
            preparedStatement.setString(2, gunProvider.name);
            preparedStatement.setString(3, gunProvider.getSpeciality());
            preparedStatement.setInt(4, gunProvider.getReputation());

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
        String updateStatement = "UPDATE " + relationName + " SET name=?,speciality=?,reputation=? WHERE id=?";
        try (
                var connection = DriverManager.getConnection(url, user, password);
                var preparedStatement = connection.prepareStatement(updateStatement)) {

            GunProvider gunProvider = (GunProvider) entity;

            preparedStatement.setString(1, gunProvider.name);
            preparedStatement.setString(2, gunProvider.getSpeciality());
            preparedStatement.setInt(3, gunProvider.getReputation());
            preparedStatement.setLong(4, gunProvider.getId());

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
            String speciality = resultSet.getString("speciality");
            int reputation = resultSet.getInt("reputation");
            return new GunProvider(newId,newName,speciality,reputation);
        } catch (SQLException throwables) {
            System.out.println("Cannot select from database!");
        }
        return null;
    }


}
