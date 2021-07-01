package gunshop.service;

import gunshop.domain.Category;
import gunshop.domain.GunType;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.ValidatorException;
import gunshop.repository.RepositoryInterface;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GunTypeService
{
    private RepositoryInterface<Long, GunType> repository;

    public GunTypeService(RepositoryInterface<Long, GunType> repository)
    {
        this.repository = repository;
    }


    /**
     * Adds the given gunType to the repository.
     * @param gunType - given gunType
     * @throws ValidatorException if gunType is not valid
     */
    public void addGunType(GunType gunType) throws ValidatorException
    {
        repository.save(gunType);
    }

    /**
     * Removes the given gunType from the repository based in its id.
     * @param id - given id
     * @throws GunShopException if the gunType is not present in the repository
     */
    public void removeGunType(Long id) throws ValidatorException
    {
        repository.delete(id).
                orElseThrow(() -> new GunShopException("GunType does not exist"));
    }

    /**
     * Updates the given gunType in the repository.
     * @param gunType - given gunType
     * @throws GunShopException if the gunType is not present in the repository
     */
    public void updateGunType(GunType gunType) throws ValidatorException
    {
        repository.update(gunType)
                .orElseThrow(() -> new GunShopException("No such gunType"));
    }

    /**
     * Gets all the gunTypes currently in the repository.
     * @return HashSet containing all gunTypes in the repository.
     */
    public Set<GunType> getAllGunTypes()
    {
        return (Set<GunType>) repository.findAll();
    }

    public List<GunType> filterGunTypesByCategory(Category category)
    {
        return getAllGunTypes().stream().filter(t -> t.getCategory() == category).collect(Collectors.toList());

    }

    /**
     * Gets a gunType based on a given id.
     * @return a gunType object or null if there is no such gunType is in the repository.
     */
    public GunType getGunTypeById(Long id) {
        var optional = repository.findOne(id);
        return optional.orElse(null);
    }

    public Set<GunType> filterByCategory(String category) {
        return this.getAllGunTypes()
                .stream()
                .filter(gunType -> gunType.getCategory().equals(category))
                .collect(Collectors.toSet());
    }

}
