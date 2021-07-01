package gunshop.service;

import gunshop.domain.Client;
import gunshop.domain.GunType;
import gunshop.domain.Rental;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.ValidatorException;
import gunshop.repository.RepositoryInterface;
import utils.Pair;

import java.util.*;

public class RentalService
{

    private RepositoryInterface<Pair<Long,Long>, Rental> rentalRepository;
    private RepositoryInterface<Long, Client> clientRepository;
    private RepositoryInterface<Long, GunType> gunRepository;

    public RentalService(RepositoryInterface<Pair<Long, Long>, Rental> rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public RentalService(RepositoryInterface<Pair<Long, Long>, Rental> rentalRepository, RepositoryInterface<Long, Client> clientRepository, RepositoryInterface<Long, GunType> gunRepository) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.gunRepository = gunRepository;
    }

    /**
     * Sets the object's "clientRepository" parameter to the given parameter value.
     * @param clientRepository - new Repository for clients
     */
    public void setClientRepository(RepositoryInterface<Long, Client> clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Sets the object's "gunRepository" parameter to the given parameter value.
     * @param gunRepository - new Repository for guns
     */
    public void setGunRepository(RepositoryInterface<Long, GunType> gunRepository) {
        this.gunRepository = gunRepository;
    }

    /**
     * Adds the given gunType to the repository.
     * @param rental - given rental
     * @throws ValidatorException if rental is not valid
     */
    public void addRental(Rental rental) throws ValidatorException
    {
        Optional<Client> clientOptional = clientRepository.findOne(rental.getClientId());
        clientOptional.ifPresentOrElse(
                (Client c) -> {
                    Optional<GunType> gunTypeOptional = gunRepository.findOne(rental.getGunTypeId());
                    gunTypeOptional.ifPresentOrElse(
                            (GunType gt) -> {
                                rentalRepository.save(rental);
                            },
                            () -> {throw new GunShopException("Gun Type does not exist"); }
                    );
                },
                () -> {throw new GunShopException("Client does not exist"); }
        );
    }

    /**
     * Removes the given Rental from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the rental is not present in the repository
     */
    public void removeRental(Pair<Long, Long> id) throws ValidatorException
    {
        rentalRepository.delete(id)
                .orElseThrow(() -> new GunShopException("Rental does not exist"));
    }

    /**
     * Updates the given gunType in the repository.
     * @param rental - given rental
     * @throws GunShopException if the rental is not present in the repository
     */
    public void updateRental(Rental rental) throws ValidatorException
    {
        rentalRepository.update(rental)
                .orElseThrow(() -> new GunShopException("No such rental"));
    }

    /**
     * Gets all the rentals currently in the repository.
     * @return HashSet containing all rentals in the repository.
     */
    public Set<Rental> getAllRentals()
    {
        return (Set<Rental>) rentalRepository.findAll();
    }

    public Optional<GunType> getMostRentedGunType() {
        Map<Long, Integer> count = new HashMap<>();
        getAllRentals().forEach(rental -> {
            count.put(rental.getGunTypeId(), count.getOrDefault(rental.getGunTypeId(), 0) + 1);
        });
        var max = count.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
        return this.gunRepository.findOne(max);
    }

}
