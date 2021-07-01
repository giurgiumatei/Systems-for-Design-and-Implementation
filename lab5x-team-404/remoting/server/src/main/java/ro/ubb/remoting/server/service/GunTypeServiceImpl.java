package ro.ubb.remoting.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.remoting.common.domain.Category;
import ro.ubb.remoting.common.domain.GunType;
import ro.ubb.remoting.server.validators.GunTypeValidator;
import ro.ubb.remoting.common.service.GunTypeService;
import ro.ubb.remoting.server.repository.GunTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GunTypeServiceImpl implements GunTypeService{

    public static final Logger log = LoggerFactory.getLogger(GunTypeService.class);

    @Autowired
    private GunTypeValidator validator;

    @Autowired
    private GunTypeRepository gunTypeRepository;

    @Override
    @Transactional
    public void addGunType(GunType gunType) {
        log.trace("addGunType - method entered gunType={}", gunType);
        validator.validate(gunType);
        gunTypeRepository.save(gunType);
        log.trace("addGunType - gunType={} saved", gunType);
    }

    @Override
    @Transactional
    public void deleteGunType(GunType gunType) {
        log.trace("deleteGunType - method entered gunType={}", gunType);
        validator.validate(gunType);
        gunTypeRepository.delete(gunType);
        log.trace("deleteGunType - gunType={} deleted", gunType);
    }

    @Override
    @Transactional
    public void updateGunType(GunType gunType) {
        log.trace("updateGunType - method entered gunType={}", gunType);
        validator.validate(gunType);
        gunTypeRepository.findById(gunType.getId())
                .ifPresent(
                        gunTypeFound ->{
                            //  gunTypeFound.setId(gunType.getId());
                            gunTypeFound.setName(gunType.getName());
                            gunTypeFound.setGunProviderID(gunType.getGunProviderID());
                            gunTypeFound.setCategory(gunType.getCategory());
                            log.trace("updateGunType - updated gunType={}", gunType);
                        }
                );
    }

    @Override
    public GunType getGunTypeById(Long id) {
        return gunTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<GunType> getAllGunTypes() {
        log.trace("getAllGunTypes --- method entered");
        List<GunType> result = gunTypeRepository.findAll();
        log.trace("getAllGunTypes: result={}", result);
        return result;
    }

    @Override
    public List<GunType> filterGunTypesByCategory(Category category) {
        log.trace("filterGunTypesByCategory --- method entered");
        var result = gunTypeRepository.findAll()
                .stream()
                .filter(gunType -> gunType.getCategory() == category)
                .collect(Collectors.toList());
        log.trace("filterGunTypesByCategory: result={}", result);
        return result;
    }

}