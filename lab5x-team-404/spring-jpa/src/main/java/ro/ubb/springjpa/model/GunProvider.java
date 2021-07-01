package ro.ubb.springjpa.model;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class GunProvider extends BaseEntity<Long> {

    private String name;
    private String speciality;
    private int reputation;

    public GunProvider(Long id, String name, String speciality, int reputation) {
        super();
        super.setId(id);
        this.name = name;
        this.speciality = speciality;
        this.reputation = reputation;
    }

    public GunProvider(String name, String speciality, int reputation) {
        this.name = name;
        this.speciality = speciality;
        this.reputation = reputation;
    }
}