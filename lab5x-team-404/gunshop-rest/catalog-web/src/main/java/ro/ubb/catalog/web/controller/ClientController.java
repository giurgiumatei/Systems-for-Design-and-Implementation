package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;

import java.time.LocalDate;

@RestController
public class ClientController {

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients")
    ClientsDto getAllClients() {
        logger.trace("getAllClients - method entered");
        return new ClientsDto(
                clientConverter.convertModelsToDtos(
                        clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto addClient(@RequestBody ClientDto clientDto){
        logger.trace("addClient - method entered; clientDto = {}", clientDto);
        Client client = clientConverter.convertDtoToModel(clientDto);
        Client result;
        result = clientService.addClient(client);
        ClientDto resultModel = clientConverter.convertModelToDto(result);
        logger.trace("addClient - method finished; resultModel(type ClientDto) = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                             @RequestBody ClientDto dto) {
        logger.trace("updateClient - method entered; clientDto = {}", dto);
        dto.setId(id);
        ClientDto resultModel = clientConverter.convertModelToDto(
                clientService.updateClient(
                        clientConverter.convertDtoToModel(dto)
                ));
        logger.trace("updateClient - method finished; resultedClientDto = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) {
        logger.trace("deleteClient - method entered; client = {}", clientService.getClientById(id));
        clientService.deleteClient(id);
        logger.trace("deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}")
    ClientDto getClientById(@PathVariable Long id) {
        return clientConverter.convertModelToDto(
                    clientService.getClientById(id));
    }

    @RequestMapping(value = "/clients/filter/{date}")
    ClientsDto getAllClientsBornBefore(@PathVariable @DateTimeFormat(pattern = "d-M-yyyy") LocalDate date) {
        return new ClientsDto(
                clientConverter.convertModelsToDtos(
                    clientService.getAllClientsBornBefore(date)));
    }
}
