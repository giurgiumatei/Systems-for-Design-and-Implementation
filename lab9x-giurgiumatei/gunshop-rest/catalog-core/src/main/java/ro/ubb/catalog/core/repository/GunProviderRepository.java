package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Category;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.core.model.GunType;

import java.util.List;

public interface GunProviderRepository extends Repository<GunProvider, Long> {

    List<GunProvider> findByOrderByNameAsc();

    //speciality and reputation greater than
    List<GunProvider> findBySpecialityEqualsAndReputationGreaterThan(String speciality, int reputation);

    boolean existsGunProviderByName(String name);

    //GunProvider findByName(String name);

    List<GunProvider> findByName(String name);


}
