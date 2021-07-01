package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.remoting.common.domain.GunProvider;
import ro.ubb.remoting.common.service.ClientService;
import ro.ubb.remoting.common.service.GunProviderService;

import java.util.List;
import java.util.Set;

@Service
public class GunProviderServiceClient implements GunProviderService {

    @Autowired
    GunProviderService gunProviderService;

    @Override
    public void addGunProvider(GunProvider gunProvider) {
        gunProviderService.addGunProvider(gunProvider);

    }

    @Override
    public void deleteGunProvider(GunProvider gp) {
        gunProviderService.deleteGunProvider(gp);

    }

    @Override
    public void updateGunProvider(GunProvider gunProvider) {
        gunProviderService.updateGunProvider(gunProvider);

    }

    @Override
    public List<GunProvider> getAllGunProviders() {
        return gunProviderService.getAllGunProviders();
    }

    @Override
    public GunProvider getGunProviderById(Long id) {
        return null;
    }
}
