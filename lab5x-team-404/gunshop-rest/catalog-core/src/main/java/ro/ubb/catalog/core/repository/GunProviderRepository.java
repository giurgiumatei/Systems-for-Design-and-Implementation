package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.GunProvider;

import java.util.List;

public interface GunProviderRepository extends Repository<GunProvider, Long> {

    List<GunProvider> findByOrderByNameAsc();

    //speciality and reputation greater than
    List<GunProvider> findBySpecialityEqualsAndReputationGreaterThan(String speciality, int reputation);
}
