package service;

import domain.Client;
import domain.GunType;
import domain.Rental;
import domain.validators.*;
import repository.RepositoryInterface;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.Set;

public interface RentalService {

    /**
     * Adds the given gunType to the repository.
     * @param rental - given rental
     * @throws ValidatorException if rental is not valid
     */
    CompletableFuture<Boolean> addRental(Rental rental) throws ValidatorException;
    /**
     * Removes the given Rental from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the rental is not present in the repository
     */
    CompletableFuture<Boolean> removeRental(Pair<Long, Long> id) throws ValidatorException;
    /**
     * Updates the given gunType in the repository.
     * @param rental - given rental
     * @throws GunShopException if the rental is not present in the repository
     */
    CompletableFuture<Boolean> updateRental(Rental rental) throws ValidatorException;

    /**
     * Gets all the rentals currently in the repository.
     * @return HashSet containing all rentals in the repository.
     */
    CompletableFuture<Set<Rental>> getAllRentals();

    CompletableFuture<GunType> getMostRentedGunType();
}
