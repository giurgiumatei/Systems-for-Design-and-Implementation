package domain;

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


}
