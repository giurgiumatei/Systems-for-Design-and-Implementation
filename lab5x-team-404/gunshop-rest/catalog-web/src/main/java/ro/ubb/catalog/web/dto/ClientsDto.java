package ro.ubb.catalog.web.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientsDto {

    private List<ClientDto> clients;
}
