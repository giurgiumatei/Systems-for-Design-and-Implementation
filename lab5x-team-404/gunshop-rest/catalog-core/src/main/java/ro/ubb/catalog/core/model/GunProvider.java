package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class GunProvider extends BaseEntity<Long> {

    private String name;
    private String speciality;
    private int reputation;
}
