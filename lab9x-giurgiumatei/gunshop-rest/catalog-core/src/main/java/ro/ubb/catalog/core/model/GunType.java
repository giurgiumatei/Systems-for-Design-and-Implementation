package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "guntype")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class GunType extends BaseEntity<Long> {

    private String name;
    private Category category;

    //one to many
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private GunProvider gunProvider;

    //many to many
    @OneToMany(mappedBy = "gunType", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER)
    private Set<Rental> rentals = new HashSet<>();



    public Set<Client> getClients() {
        return rentals.stream()
                .map(Rental::getClient).collect(Collectors.toUnmodifiableSet());
    }

    public void addClient(Client client) {
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setGunType(this);
        rentals.add(rental);
    }

    public void addPrice(Client client, Integer price) {
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setPrice(price);
        rental.setGunType(this);
        rentals.add(rental);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GunType that = (GunType) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


}
