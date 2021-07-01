package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.service.RentalService;
import ro.ubb.catalog.web.converter.GunTypeConverter;
import ro.ubb.catalog.web.converter.RentalConverter;
import ro.ubb.catalog.web.dto.GunTypeDto;
import ro.ubb.catalog.web.dto.RentalDto;
import ro.ubb.catalog.web.dto.RentalsDto;

@RestController
public class RentalController {

    public static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;

    @Autowired
    private GunTypeConverter gunTypeConverter;


    @RequestMapping(value = "/rentals")
    RentalsDto getAllRentals() {
        logger.trace("getAllRentals - method entered;");
        RentalsDto result = new RentalsDto(
                rentalConverter.convertModelsToDtos(
                        rentalService.getAllRentals()));
        logger.trace("getAllRentals - method finished; result = {}", result);
        return result;
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto addRental(@RequestBody RentalDto rentalDto) throws Exception {
        logger.trace("addRental - method entered; rentalDto = {}", rentalDto);
        var rental = rentalConverter.convertDtoToModel(rentalDto);

        var result = rentalService.addRental(rental);

        var resultModel = rentalConverter.convertModelToDto(result);

        logger.trace("addRental - method finished; resultModel = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
    RentalDto updateRental(@PathVariable Long id,
                             @RequestBody RentalDto dto) {
        logger.trace("updateRental - method entered; dto = {}", dto);
        dto.setId(id);
        RentalDto result = rentalConverter.convertModelToDto(
                rentalService.updateRental(
                        rentalConverter.convertDtoToModel(dto)
                ));
        logger.trace("updateRental - method finished; result = {}", result);
        return result;
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id) {
        logger.trace("deleteRental - method entered; result = {}", rentalService.getRentalById(id));
        rentalService.deleteRental(id);
        logger.trace("deleteRental - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{id}")
    RentalDto getRentalById(@PathVariable Long id) {
        return rentalConverter.convertModelToDto(
                        rentalService.getRentalById(id));
    }

    @RequestMapping(value = "/rentals/most")
    GunTypeDto getMostRentedGunType() {
        return gunTypeConverter.convertModelToDto(
                    rentalService.getMostRentedGunType());
    }
}
