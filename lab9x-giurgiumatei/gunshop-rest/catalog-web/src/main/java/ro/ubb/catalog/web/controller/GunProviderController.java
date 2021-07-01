package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.core.service.GunProviderService;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.converter.GunProviderConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;
import ro.ubb.catalog.web.dto.GunProviderDto;
import ro.ubb.catalog.web.dto.GunProvidersDto;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@RestController
public class GunProviderController {

    public static final Logger logger = LoggerFactory.getLogger(GunProviderController.class);

    @Autowired
    private GunProviderService gunProviderService;

    @Autowired
    private GunProviderConverter gunProviderConverter;

    @RequestMapping(value = "/gun-providers", method = RequestMethod.GET)
    public List<GunProviderDto> getAllGunProviders() {
        logger.trace("getGunProviders");

        List<GunProvider> gunProviders = gunProviderService.getAllGunProviders();

        logger.trace("getGunProviders: gunProviders={}", gunProviders);

        return new ArrayList<>(gunProviderConverter.convertModelsToDtos(gunProviders));
    }

    @RequestMapping(value = "/gun-providers", method = RequestMethod.POST)
    GunProviderDto addGunProvider(@RequestBody GunProviderDto gunProviderDto){
        logger.trace("addGunProvider - method entered; gunProviderDto = {}", gunProviderDto);
        GunProvider gunProvider = gunProviderConverter.convertDtoToModel(gunProviderDto);

        GunProvider result = gunProviderService.addGunProvider(gunProvider);

        GunProviderDto resultModel = gunProviderConverter.convertModelToDto(result);
        logger.trace("addGunProvider - method finished; resultModel = {}", resultModel);
        return resultModel;
    }

    @RequestMapping(value = "/gun-providers/{id}", method = RequestMethod.PUT)
    GunProviderDto updateGunProvider(@PathVariable Long id,
                            @RequestBody GunProviderDto dto) {
        logger.trace("updateGunProvider - method entered; dto = {}", dto);
        dto.setId(id);
        GunProviderDto result = gunProviderConverter.convertModelToDto(
                gunProviderService.updateGunProvider(
                        gunProviderConverter.convertDtoToModel(dto)
                ));
        logger.trace("updateGunProvider - method finished; result = {}", result);
        return result;
    }

    @RequestMapping(value = "/gun-providers/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteGunProvider(@PathVariable Long id) {
        logger.trace("deleteGunProvider - method entered; result = {}", gunProviderService.getGunProviderById(id));
        gunProviderService.deleteGunProvider(id);
        logger.trace("deleteGunProvider - method finished;");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/gun-providers/{id}")
    GunProviderDto getGunProviderById(@PathVariable Long id) {
        return gunProviderConverter.convertModelToDto(
                gunProviderService.getGunProviderById(id));
    }

    @RequestMapping(value = "gun-providers/sort/name")
    List<GunProviderDto> getGunProvidersSortedByName() {
        logger.trace("getGunProviders");

        List<GunProvider> gunProviders = gunProviderService.getGunProvidersSortedByName();

        logger.trace("getGunProviders: gunProviders={}", gunProviders);

        return new ArrayList<>(gunProviderConverter.convertModelsToDtos(gunProviders));
    }

    //http://localhost:8080/api/gun-providers/filter?speciality=tare&reputation=2
    @RequestMapping(value = "gun-providers/filter", method = RequestMethod.GET)
    GunProvidersDto filterBySpecialityAndReputationGreater(@RequestParam("speciality") String speciality,
                                                           @RequestParam("reputation") int reputation) {
        return new GunProvidersDto(gunProviderConverter.convertModelsToDtos(
                gunProviderService.filterBySpecialityAndReputationGreater(speciality, reputation)));
    }
}