package service;

import domain.GunProvider;
import domain.validators.*;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

public interface GunProviderService {

    /**
     * Adds the given gunType to the repository.
     * @param gunProvider - given gunProvider
     * @throws ValidatorException if gunType is not valid
     */
    CompletableFuture<Boolean> addGunProvider(GunProvider gunProvider) throws ValidatorException;

    /**
     * Removes the given gunType from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the gunType is not present in the repository
     */
    CompletableFuture<Boolean> removeGunProvider(Long id) throws ValidatorException;

    /**
     * Updates the given gunType in the repository.
     * @param gunProvider - given gunType
     * @throws GunShopException if the gunType is not present in the repository
     */
    CompletableFuture<Boolean>updateGunProvider(GunProvider gunProvider) throws ValidatorException;
    /**
     * Gets all the gunTypes currently in the repository.
     * @return HashSet containing all gunTypes in the repository.
     */
   CompletableFuture<Set<GunProvider>> getAllGunProviders();
    /**
     * Gets a gunType based on a given id.
     * @return a gunType object or null if there is no such gunType is in the repository.
     */
    CompletableFuture<GunProvider> getGunProviderById(Long id);

}
