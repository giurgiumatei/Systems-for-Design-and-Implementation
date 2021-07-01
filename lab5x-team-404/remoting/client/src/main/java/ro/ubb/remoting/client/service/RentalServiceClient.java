package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.remoting.common.domain.GunType;
import ro.ubb.remoting.common.domain.Rental;
import ro.ubb.remoting.common.service.RentalService;

import java.util.List;

@Service
public class RentalServiceClient implements RentalService {

    @Autowired
    RentalService rentalService;

    @Override
    public void addRental(Rental rental) {
        try {
            rentalService.addRental(rental);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRental(Rental r) {
        rentalService.deleteRental(r);
    }

    @Override
    public void updateRental(Rental rental) {
        rentalService.updateRental(rental);
    }

    @Override
    public Rental getRentalById(Long id) {
        return rentalService.getRentalById(id);
    }

    @Override
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Override
    public GunType getMostRentedGunType() {
        return rentalService.getMostRentedGunType();
    }
}
