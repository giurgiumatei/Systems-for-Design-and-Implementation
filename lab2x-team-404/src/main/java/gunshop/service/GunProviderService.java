package gunshop.service;

import gunshop.domain.GunProvider;
import gunshop.domain.GunType;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.ValidatorException;
import gunshop.repository.RepositoryInterface;

import java.util.Set;

public class GunProviderService {

    private RepositoryInterface<Long, GunProvider> repository;

    public GunProviderService(RepositoryInterface<Long, GunProvider> repository)
    {
        this.repository = repository;
    }

    /**
     * Adds the given gunType to the repository.
     * @param gunProvider - given gunProvider
     * @throws ValidatorException if gunType is not valid
     */
    public void addGunProvider(GunProvider gunProvider) throws ValidatorException
    {
        repository.save(gunProvider);
    }

    /**
     * Removes the given gunType from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the gunType is not present in the repository
     */
    public void removeGunProvider(Long id) throws ValidatorException
    {
        repository.delete(id).
                orElseThrow(() -> new GunShopException("GunProvider does not exist"));
    }

    /**
     * Updates the given gunType in the repository.
     * @param gunProvider - given gunType
     * @throws GunShopException if the gunType is not present in the repository
     */
    public void updateGunProvider(GunProvider gunProvider) throws ValidatorException
    {
        repository.update(gunProvider)
                .orElseThrow(() -> new GunShopException("No such gun provider"));
    }

    /**
     * Gets all the gunTypes currently in the repository.
     * @return HashSet containing all gunTypes in the repository.
     */
    public Set<GunProvider> getAllGunProviders()
    {
        return (Set<GunProvider>) repository.findAll();
    }

    /**
     * Gets a gunType based on a given id.
     * @return a gunType object or null if there is no such gunType is in the repository.
     */
    public GunProvider getGunProviderById(Long id) {
        var optional = repository.findOne(id);
        return optional.orElse(null);
    }
}
