package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> getAllRentals();

    Rental addRental(Rental rental) throws Exception;

    void deleteRental(Long id);

    Rental updateRental(Rental rental);

    Rental getRentalById(Long id);

    GunType getMostRentedGunType();
}
