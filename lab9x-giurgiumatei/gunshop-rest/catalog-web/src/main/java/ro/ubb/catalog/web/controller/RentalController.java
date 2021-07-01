package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.core.service.GunTypeService;
import ro.ubb.catalog.web.converter.GunTypeConverter;
import ro.ubb.catalog.web.converter.RentalConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.GunTypeDto;
import ro.ubb.catalog.web.dto.RentalDto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private GunTypeService gunTypeService;

    @Autowired
    private RentalConverter rentalConverter;

    @Autowired
    private GunTypeConverter gunTypeConverter;


    @RequestMapping(value = "/rentals/{clientId}", method = RequestMethod.GET)
    public Set<RentalDto> getClientRentals(
            @PathVariable final Long clientId) {
        logger.trace("getClientRentals: clientId={}", clientId);
        Client client = clientService.getClientById(clientId);

        return new HashSet<>(rentalConverter.convertModelsToDtos(client.getRentals()));


    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public Set<RentalDto> getAllRentals() {
        logger.trace("getAllRentals");
        Set<RentalDto> rentals = new HashSet<>();
        clientService.getAllClients().stream()
                .map(client -> this.getClientRentals(client.getId()))
                .forEach(rentals::addAll);
        logger.trace("getAllRentals - ended");
        return rentals;


    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto addRental(@RequestBody RentalDto rentalDto) {
        logger.trace("addRental - method entered; rentalDto = {}", rentalDto);
        Rental rental = rentalConverter.convertDtoToModel(rentalDto);
        Rental result;
        result = clientService.addRental(rental);
        RentalDto resultModel = rentalConverter.convertModelToDto(result);
        logger.trace("addRental - method finished; resultModel(type RentalDto) = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/prices/{clientId}", method = RequestMethod.PUT)
    public Set<RentalDto> updateRentalPrices(
            @PathVariable final Long clientId,
            @RequestBody final Set<RentalDto> rentalDtos) {
        logger.trace("updateRentalPrices: clientId={}, rentalDtos={}",
                clientId, rentalDtos);

        throw new RuntimeException("not yet implemented");
    }

    @RequestMapping(value = "/most-rented", method = RequestMethod.GET)
    GunTypeDto getMostRentedGunType() {
        logger.trace("getMostRentedGunType");
        var rentals = this.getAllRentals();


        Map<String, Integer> counter = new HashMap<>();

        //will put an entry in the counter map with GunType as key and number of apparitions as value
        rentals.forEach(rental -> {
            counter.put(rental.getGunTypeName(), counter.getOrDefault(rental.getGunTypeName(), 0) +1);
        });

        //GunType with the maximum number of apparitions
        var mostRentedGunTypeName = counter.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        var mostRentedGunType = this.gunTypeService.getGunTypeByName(mostRentedGunTypeName);
        logger.trace("getMostRentedGunType - ended, mostRentedGunType={}",mostRentedGunType);

        return this.gunTypeConverter.convertModelToDto(mostRentedGunType);
    }
}
