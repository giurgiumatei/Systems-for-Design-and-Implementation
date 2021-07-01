package ro.ubb.remoting.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.remoting.common.domain.GunProvider;
import ro.ubb.remoting.server.validators.GunProviderValidator;
import ro.ubb.remoting.common.service.GunProviderService;
import ro.ubb.remoting.server.repository.GunProviderRepository;

import java.util.List;

@Service
public class GunProviderServiceImpl implements GunProviderService{

    public static final Logger log = LoggerFactory.getLogger(GunProviderService.class);

    @Autowired
    private GunProviderValidator validator;

    @Autowired
    private GunProviderRepository gunProviderRepository;

    @Override
    @Transactional
    public void addGunProvider(GunProvider gunProvider) {
        log.trace("addGunProvider - method entered gunProvider={}", gunProvider);
        validator.validate(gunProvider);
        gunProviderRepository.save(gunProvider);
        log.trace("addGunProvider - gunProvider={} saved", gunProvider);
    }

    @Override
    @Transactional
    public void deleteGunProvider(GunProvider gunProvider) {
        log.trace("deleteGunProvider - method entered gunProvider={}", gunProvider);
        validator.validate(gunProvider);
        gunProviderRepository.delete(gunProvider);
        log.trace("deleteGunProvider - gunProvider={} saved", gunProvider);
    }

    @Override
    @Transactional
    public void updateGunProvider(GunProvider gunProvider) {
        log.trace("updateGunProvider - method entered gunProvider={}", gunProvider);
        validator.validate(gunProvider);
        gunProviderRepository.findById(gunProvider.getId())
                .ifPresent(
                        gunProviderFound ->{
                            //gunProviderFound.setId(gunProvider.getId());
                            gunProviderFound.setName(gunProvider.getName());
                            gunProviderFound.setReputation(gunProvider.getReputation());
                            gunProviderFound.setSpeciality(gunProvider.getSpeciality());
                            log.trace("updateGunProvider - updated gunProvider={}", gunProvider);
                        }
                );
    }

    @Override
    public GunProvider getGunProviderById(Long id) {
        return gunProviderRepository.findById(id).get();
    }

    @Override
    public List<GunProvider> getAllGunProviders() {
        log.trace("getAllGunProviders --- method entered");
        List<GunProvider> result = gunProviderRepository.findAll();
        log.trace("getAllGunProviders: result={}", result);
        return result;
    }

}