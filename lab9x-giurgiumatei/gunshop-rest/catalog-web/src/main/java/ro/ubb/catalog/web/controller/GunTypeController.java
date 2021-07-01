package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Category;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.exceptions.GunShopException;
import ro.ubb.catalog.core.service.GunTypeService;
import ro.ubb.catalog.web.converter.GunTypeConverter;
import ro.ubb.catalog.web.dto.GunTypeDto;
import ro.ubb.catalog.web.dto.GunTypesDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GunTypeController {

    public static final Logger logger = LoggerFactory.getLogger(GunTypeController.class);

    @Autowired
    private GunTypeService gunTypeService;

    @Autowired
    private GunTypeConverter gunTypeConverter;

    @RequestMapping(value = "/gun-types")
    public List<GunTypeDto> getAllGunTypes() {
        logger.trace("getGunTypes");

        List<GunType> gunTypes = gunTypeService.getAllGunTypes();

        logger.trace("getGunTypes: gunTypes={}", gunTypes);

        return new ArrayList<>(gunTypeConverter.convertModelsToDtos(gunTypes));
    }

    @RequestMapping(value = "/gun-types", method = RequestMethod.POST)
    GunTypeDto addGunType(@RequestBody GunTypeDto gunTypeDto){
        logger.trace("addGunType - method entered; gunTypeDto = {}", gunTypeDto);
        var gunType = gunTypeConverter.convertDtoToModel(gunTypeDto);
        GunType result;
        try {
            result = gunTypeService.saveGunType(gunType);
        } catch (Exception e) {
            throw new RuntimeException("Could not add");
        }

        var resultModel = gunTypeConverter.convertModelToDto(result);

        logger.trace("addGunType - method finished; resultModel = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/gun-types/{id}", method = RequestMethod.PUT)
    GunTypeDto updateGunType(@PathVariable Long id,
                             @RequestBody GunTypeDto dto) {
        logger.trace("updateGunType - method entered; dto = {}", dto);
        dto.setId(id);
        GunTypeDto result = gunTypeConverter.convertModelToDto(
                gunTypeService.updateGunType(
                        gunTypeConverter.convertDtoToModel(dto)
                ));

        logger.trace("updateGunType - method finished; result = {}", result);
        return result;
    }

    @RequestMapping(value = "/gun-types/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteGunType(@PathVariable Long id) {
        logger.trace("deleteGunType - method entered; result = {}", gunTypeService.getGunTypeById(id));
        gunTypeService.deleteGunType(id);
        logger.trace("deleteGunType - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/gun-types/{id}")
    GunTypeDto getGunTypeById(@PathVariable Long id) {
        return gunTypeConverter.convertModelToDto(
                gunTypeService.getGunTypeById(id));
    }

    @RequestMapping(value = "/gun-types/filter/{category}")
    GunTypesDto filterGunTypesByCategory(@PathVariable Category category) {
        return new GunTypesDto(
                gunTypeConverter.convertModelsToDtos(
                        gunTypeService.filterGunTypesByCategory(category)));
    }
}