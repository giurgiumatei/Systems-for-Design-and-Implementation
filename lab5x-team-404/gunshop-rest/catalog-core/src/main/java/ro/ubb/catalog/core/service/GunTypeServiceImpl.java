package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Category;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.exceptions.GunShopException;
import ro.ubb.catalog.core.model.validators.GunTypeValidator;
import ro.ubb.catalog.core.repository.GunProviderRepository;
import ro.ubb.catalog.core.repository.GunTypeRepository;

import java.util.List;

@Service
public class GunTypeServiceImpl implements GunTypeService {

    public static final Logger logger = LoggerFactory.getLogger(GunTypeService.class);

    @Autowired
    private GunTypeValidator validator;

    @Autowired
    private GunTypeRepository gunTypeRepository;

    @Autowired
    private GunProviderRepository gunProviderRepository;

    @Override
    public List<GunType> getAllGunTypes() {
        logger.trace("getAllGunTypes - method entered");
        return gunTypeRepository.findAll();
    }

    @Override
    public GunType saveGunType(GunType gunType){
        logger.trace("addGunType - method entered; gunType = {}", gunType);
        validator.validate(gunType);
        if(!gunProviderRepository.existsById(gunType.getGunProviderID())) {
            throw new GunShopException("No Client with this ID!");
        }
        GunType save = gunTypeRepository.save(gunType);
        logger.trace("addGunType - method finished; gunType = {}", gunType);
        return save;
    }

    @Override
    public void deleteGunType(Long id) {
        logger.trace("deleteGunType - method entered; gunType = {}", this.gunTypeRepository.findById(id));
        gunTypeRepository.deleteById(id);
        logger.trace("deleteGunType - method finished");
    }

    @Override
    @Transactional
    public GunType updateGunType(GunType gunType) {
        logger.trace("updateGunType - method entered; gunType = {}", gunType);
        validator.validate(gunType);
        GunType gunTypeUpdate = gunTypeRepository.findById(gunType.getId()).orElseThrow();
        gunTypeUpdate.setName(gunType.getName());
        gunTypeUpdate.setCategory(gunType.getCategory());
        gunTypeUpdate.setGunProviderID(gunType.getGunProviderID());
        logger.trace("updateGunType - method finished; gunTypeUpdate = {}", gunTypeUpdate);
        return gunType;
    }

    @Override
    public GunType getGunTypeById(Long id) {
        logger.trace("getGunTypeById - method entered; id = {}", id);
        GunType result = gunTypeRepository.findById(id).orElseThrow();
        logger.trace("getGunTypeById - method finished; result = {}", result);
        return result;
    }

    @Override
    public List<GunType> filterGunTypesByCategory(Category category) {
        logger.trace("filterGunTypesByCategory - method entered; category = {}", category);
        List<GunType> result = gunTypeRepository.findByCategory(category);
        logger.trace("filterGunTypesByCategory - method finished; result = {}", result);
        return result;
    }
}