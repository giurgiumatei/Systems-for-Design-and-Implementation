package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.model.exceptions.GunShopException;
import ro.ubb.catalog.core.model.validators.RentalValidator;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.GunTypeRepository;
import ro.ubb.catalog.core.repository.RentalRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalServiceImpl implements RentalService {

    public static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    @Autowired
    private RentalValidator validator;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private GunTypeRepository gunTypeRepository;

    @Autowired
    private ClientRepository clientTypeRepository;

    @Override
    public List<Rental> getAllRentals() {
        logger.trace("getAllRentals - method entered");
        return rentalRepository.findAll();
    }

    @Override
    public Rental addRental(Rental rental) throws GunShopException {
        logger.trace("addRental - method entered; rental = {}", rental);
        validator.validate(rental);
        if(!clientTypeRepository.existsById(rental.getClientId())){
            throw new GunShopException("No Client with this ID!");
        }
        if(!gunTypeRepository.existsById(rental.getGunTypeId())){
            throw new GunShopException("No Gun Type with this ID!");
        }
        Rental save = rentalRepository.save(rental);
        logger.trace("addRental - method finished; save = {}", save);
        return save;
    }

    @Override
    public void deleteRental(Long id) {
        logger.trace("deleteRental - method entered; rental = {}", rentalRepository.findById(id));
        rentalRepository.deleteById(id);
        logger.trace("deleteRental - method finished");
    }

    @Override
    @Transactional
    public Rental updateRental(Rental rental) {
        logger.trace("updateRental - method entered; rental = {}", rental);
        validator.validate(rental);
        Rental rentalUpdate = rentalRepository.findById(rental.getId()).orElseThrow();
        rentalUpdate.setPrice(rental.getPrice());
        rentalUpdate.setClientId(rental.getClientId());
        rentalUpdate.setGunTypeId(rental.getGunTypeId());
        logger.trace("updateRental - method finished; rentalUpdate = {}", rentalUpdate);
        return rental;
    }

    @Override
    public Rental getRentalById(Long id) {
        logger.trace("getRentalById - method entered; id = {}", id);
        Rental result = rentalRepository.findById(id).orElseThrow();
        logger.trace("getRentalById - method finished; result = {}", result);
        return result;
    }

    @Override
    public GunType getMostRentedGunType() {
        //return rentalRepository.getMostRentedGunType();
        logger.trace("getMostRentedGunType - method entered");
        try {
            Map<Long, Integer> count = new HashMap<>();
            this.getAllRentals().forEach(rental -> {
                count.put(rental.getGunTypeId(), count.getOrDefault(rental.getGunTypeId(), 0) + 1);
            });

            var max = count.entrySet().stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get()
                    .getKey();
            var result = gunTypeRepository.findById(max).orElse(null);
            logger.trace("getMostRentedGunType: result={}", result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
