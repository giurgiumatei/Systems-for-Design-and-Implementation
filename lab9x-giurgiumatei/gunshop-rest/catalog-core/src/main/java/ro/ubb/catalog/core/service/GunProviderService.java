package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.GunProvider;

import java.util.List;

public interface GunProviderService {
    List<GunProvider> getAllGunProviders();

    GunProvider addGunProvider(GunProvider gunProvider);

    void deleteGunProvider(Long id);

    GunProvider updateGunProvider(GunProvider gunProvider);

    GunProvider getGunProviderById(Long id);

    List<GunProvider> getGunProvidersSortedByName();

    List<GunProvider> filterBySpecialityAndReputationGreater(String speciality, int reputation);
}
