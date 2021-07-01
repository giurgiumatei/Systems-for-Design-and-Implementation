package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class Client extends BaseEntity<Long> {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    //one to one
    @Embedded
    private Account account;

    //many to many
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER)
    private Set<Rental> rentals = new HashSet<>();





    public Set<GunType> getGunTypes() {
        rentals = rentals == null ? new HashSet<>() :
                rentals;
        return this.rentals.stream().
                map(Rental::getGunType).collect(Collectors.toUnmodifiableSet());
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public void addGunTypes(Set<Rental> rentals)
    {
        rentals.forEach(this::addRental);
    }

    public void addPrice(GunType gunType, Integer price) {
        Rental rental = new Rental();
        rental.setGunType(gunType);
        rental.setPrice(price);
        rental.setClient(this);
        rentals.add(rental);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
