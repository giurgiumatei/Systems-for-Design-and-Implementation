package ro.ubb.socket.server.service;

import domain.Category;
import domain.GunType;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;
import service.GunTypeService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GunTypeServiceImpl implements GunTypeService {

    RepositoryInterface<Long, GunType> repository;

    public GunTypeServiceImpl(RepositoryInterface<Long, GunType> repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Boolean> addGunType(GunType gunType) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.save(gunType).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> removeGunType(Long id) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(id).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updateGunType(GunType gunType) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.update(gunType).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Set<GunType>> getAllGunTypes() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (Set<GunType>) repository.findAll();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<GunType> getGunTypeById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.findOne(id).orElse(null);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<List<GunType>> filterGunTypesByCategory(Category category) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ((Set<GunType>) repository.findAll()).stream()
                        .filter(gunType -> gunType.getCategory() == category)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

}
