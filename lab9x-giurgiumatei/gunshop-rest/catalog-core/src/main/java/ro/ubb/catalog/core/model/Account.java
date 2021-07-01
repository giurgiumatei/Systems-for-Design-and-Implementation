package ro.ubb.catalog.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {

    private String email;
    private String username;
    private String password;

}
