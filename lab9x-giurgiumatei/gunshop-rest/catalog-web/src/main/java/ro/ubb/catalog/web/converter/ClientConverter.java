package ro.ubb.catalog.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.web.dto.ClientDto;

import java.util.stream.Collectors;

@Component
public class ClientConverter extends AbstractConverterBaseEntityConverter<Client, ClientDto>{
    @Autowired
    private RentalConverter rentalConverter;

    @Override
    public Client convertDtoToModel(ClientDto dto) {
        var model = new Client();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setDateOfBirth(dto.getDateOfBirth());
        model.setAccount(dto.getAccount());

        //TODO find a way to map ids to gunTypes
        //will map each gunType after its name
      //  model.setRentals(dto.getRentals().stream().map(rentalDto -> rentalConverter.convertDtoToModel(rentalDto)).collect(Collectors.toSet()));

        return model;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
//        ClientDto clientDto = ClientDto.builder()
//                .name(client.getName())
//                .dateOfBirth(client.getDateOfBirth())
//                .account(client.getAccount())
////                .rentals(client.getRentals().stream().map(rental -> rentalConverter.convertModelToDto(rental)).collect(Collectors.toSet()))
//                .build();
//        clientDto.setId(client.getId());
//        return clientDto;
        ClientDto dto = new ClientDto(client.getName(), client.getDateOfBirth(),client.getAccount());
        dto.setId(client.getId());
        return dto;
    }
}
