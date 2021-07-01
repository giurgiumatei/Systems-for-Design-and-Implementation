package ro.ubb.catalog.web.dto;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class ClientDto extends BaseDto{

    private String name;
    private LocalDate dateOfBirth;
}
