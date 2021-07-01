package ro.ubb.remoting.common.service;

import ro.ubb.remoting.common.domain.GunProvider;

import java.util.List;

public interface GunProviderService {

    void addGunProvider(GunProvider gunProvider);
    void deleteGunProvider(GunProvider gunProvider);
    void updateGunProvider(GunProvider gunProvider);
    GunProvider getGunProviderById(Long id);
    List<GunProvider> getAllGunProviders();
}
