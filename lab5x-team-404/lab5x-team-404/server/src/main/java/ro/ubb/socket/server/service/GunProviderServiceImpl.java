package ro.ubb.socket.server.service;

import domain.GunProvider;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;
import service.GunProviderService;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GunProviderServiceImpl implements GunProviderService {

    private final RepositoryInterface<Long, GunProvider> repository;

    public GunProviderServiceImpl(RepositoryInterface<Long, GunProvider> repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Boolean> addGunProvider(GunProvider gunProvider) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.save(gunProvider).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> removeGunProvider(Long id) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(id).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updateGunProvider(GunProvider gunProvider) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.update(gunProvider).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Set<GunProvider>> getAllGunProviders() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (Set<GunProvider>) repository.findAll();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<GunProvider> getGunProviderById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.findOne(id).orElse(null);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
