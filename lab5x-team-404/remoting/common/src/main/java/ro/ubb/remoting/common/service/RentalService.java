package ro.ubb.remoting.common.service;

import ro.ubb.remoting.common.domain.GunType;
import ro.ubb.remoting.common.domain.Rental;

import java.util.List;

public interface RentalService {

    void addRental(Rental rental) throws Exception;
    void deleteRental(Rental rental);
    void updateRental(Rental rental);
    Rental getRentalById(Long id);
    List<Rental> getAllRentals();
    GunType getMostRentedGunType();
}

