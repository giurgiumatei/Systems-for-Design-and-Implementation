package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gunprovider")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class GunProvider extends BaseEntity<Long> {

    private String name;
    private String speciality;
    private int reputation;
}
