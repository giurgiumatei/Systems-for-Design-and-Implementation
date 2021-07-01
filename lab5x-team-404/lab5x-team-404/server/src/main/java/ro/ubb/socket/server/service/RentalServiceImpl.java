package ro.ubb.socket.server.service;

import domain.Client;
import domain.GunType;
import domain.Rental;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;
import service.RentalService;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RentalServiceImpl implements RentalService {

    private RepositoryInterface<Pair<Long, Long>, Rental> rentalRepository;
    private RepositoryInterface<Long, Client> clientRepository;
    private RepositoryInterface<Long, GunType> gunRepositoryl;

    public RentalServiceImpl(RepositoryInterface<Pair<Long, Long>, Rental> rentalRepository, RepositoryInterface<Long, Client> clientRepository, RepositoryInterface<Long, GunType> gunRepositoryl) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.gunRepositoryl = gunRepositoryl;
    }

    @Override
    public CompletableFuture<Boolean> addRental(Rental rental) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return rentalRepository.save(rental).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> removeRental(Pair<Long, Long> id) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return rentalRepository.delete(id).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updateRental(Rental rental) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return rentalRepository.update(rental).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Rental>> getAllRentals() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (Set<Rental>) rentalRepository.findAll();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<GunType> getMostRentedGunType() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Map<Long, Integer> count = new HashMap<>();
                this.getAllRentals().get().forEach(rental -> {
                    count.put(rental.getGunTypeId(), count.getOrDefault(rental.getGunTypeId(), 0) + 1);
                });

                var max = count.entrySet().stream()
                        .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                        .get()
                        .getKey();

                return this.gunRepositoryl.findOne(max).orElse(null);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
