package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.web.dto.ClientDto;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto>{
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        var model = new Client();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setDateOfBirth(dto.getDateOfBirth());
        return model;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = new ClientDto(client.getName(), client.getDateOfBirth());
        dto.setId(client.getId());
        return dto;
    }
}
