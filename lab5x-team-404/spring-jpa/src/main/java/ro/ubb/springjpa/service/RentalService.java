package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.GunType;
import ro.ubb.springjpa.model.Rental;
import ro.ubb.springjpa.utils.Pair;

import java.util.List;

public interface RentalService {
    
    void addRental(Rental rental) throws Exception;
    void deleteRental(Rental rental);
    void updateRental(Rental rental);
    Rental getRentalById(Long id);
    List<Rental> getAllRentals();
    GunType getMostRentedGunType();
}
