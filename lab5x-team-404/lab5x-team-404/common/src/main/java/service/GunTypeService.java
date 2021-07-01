package service;

import domain.Category;
import domain.GunType;
import domain.validators.*;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.Set;

public interface GunTypeService {

    /**
     * Adds the given gunType to the repository.
     * @param gunType - given gunType
     * @throws ValidatorException if gunType is not valid
     */
    CompletableFuture<Boolean> addGunType(GunType gunType) throws ValidatorException;

    /**
     * Removes the given gunType from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the gunType is not present in the repository
     */
    CompletableFuture<Boolean> removeGunType(Long id) throws ValidatorException;

    /**
     * Updates the given gunType in the repository.
     * @param gunType - given gunType
     * @throws GunShopException if the gunType is not present in the repository
     */
    CompletableFuture<Boolean> updateGunType(GunType gunType) throws ValidatorException;
    /**
     * Gets all the gunTypes currently in the repository.
     * @return HashSet containing all gunTypes in the repository.
     */
    CompletableFuture<Set<GunType>> getAllGunTypes();

    CompletableFuture<List<GunType>> filterGunTypesByCategory(Category category);
    /**
     * Gets a gunType based on a given id.
     * @return a gunType object or null if there is no such gunType is in the repository.
     */
    CompletableFuture<GunType> getGunTypeById(Long id) ;

}
