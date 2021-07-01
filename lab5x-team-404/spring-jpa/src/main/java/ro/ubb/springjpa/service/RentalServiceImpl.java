package ro.ubb.springjpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.springjpa.model.GunType;
import ro.ubb.springjpa.model.Rental;
import ro.ubb.springjpa.model.validators.RentalValidator;
import ro.ubb.springjpa.repository.ClientRepository;
import ro.ubb.springjpa.repository.GunTypeRepository;
import ro.ubb.springjpa.repository.RentalRepository;
import ro.ubb.springjpa.utils.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalServiceImpl implements RentalService{

    public static final Logger log = LoggerFactory.getLogger(Rental.class);

    @Autowired
    private RentalValidator validator;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private GunTypeRepository gunTypeRepository;

    @Autowired
    private ClientRepository clientTypeRepository;

    @Override
    @Transactional
    public void addRental(Rental rental) throws Exception {
        log.trace("addRental - method entered rental={}", rental);
        validator.validate(rental);

        if(!clientTypeRepository.existsById(rental.getClientId())){
            throw new Exception("No Client with this ID!");
        }
        if(!gunTypeRepository.existsById(rental.getGunTypeId())){
            throw new Exception("No Gun Type with this ID!");
        }
        rentalRepository.save(rental);
        log.trace("addRental - rental={} saved", rental);
    }

    @Override
    @Transactional
    public void deleteRental(Rental rental) {
        log.trace("deleteRental - method entered rental={}", rental);
        validator.validate(rental);
        rentalRepository.delete(rental);
        log.trace("deleteRental - rental={} deleted", rental);
    }

    @Override
    @Transactional
    public void updateRental(Rental rental) {
        log.trace("updateRental - method entered rental={}", rental);
        validator.validate(rental);
        rentalRepository.findById(rental.getId())
                .ifPresent(
                        rentalFound ->{
                            //rentalFound.setId(rental.getId());
                            //rentalFound.setClientId(rental.getClientId());
                            //rentalFound.setGunTypeId(rental.getGunTypeId());
                            rentalFound.setPrice(rental.getPrice());
                            log.trace("updateRental - updated rental={}", rental);
                        }
                );
    }

    @Override
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rental> getAllRentals() {
        log.trace("getAllRentals --- method entered");
        List<Rental> result = rentalRepository.findAll();
        log.trace("getAllRentals: result={}", result);
        return result;
    }

    @Override
    public GunType getMostRentedGunType() {
        log.trace("getMostRentedGunType: --- method entered");
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
            log.trace("getMostRentedGunType: result={}", result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
