package ro.ubb.springjpa.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Client extends BaseEntity<Long> {

    private String name;
    private LocalDate dateOfBirth;

    public Client(long id, String name, LocalDate dateOfBirth) {
        super();
        super.setId(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Client(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
}
