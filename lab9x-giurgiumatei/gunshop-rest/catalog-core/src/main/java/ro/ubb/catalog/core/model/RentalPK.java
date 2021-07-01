package ro.ubb.catalog.core.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RentalPK implements Serializable {
    private Client client;
    private GunType gunType;
}
