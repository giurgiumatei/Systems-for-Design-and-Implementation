package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
public class Rental extends BaseEntity<Long> {
    private int price;
    private long clientId;
    private long gunTypeId;
}


