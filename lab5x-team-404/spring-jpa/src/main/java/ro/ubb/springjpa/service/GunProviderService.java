package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.GunProvider;

import java.util.List;

public interface GunProviderService {

    void addGunProvider(GunProvider gunProvider);
    void deleteGunProvider(GunProvider gunProvider);
    void updateGunProvider(GunProvider gunProvider);
    GunProvider getGunProviderById(Long id);
    List<GunProvider> getAllGunProviders();
}
