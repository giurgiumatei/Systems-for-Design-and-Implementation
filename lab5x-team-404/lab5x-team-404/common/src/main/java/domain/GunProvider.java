package domain;

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






}
