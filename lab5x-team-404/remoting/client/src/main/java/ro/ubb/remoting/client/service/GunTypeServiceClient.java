package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.remoting.common.domain.Category;
import ro.ubb.remoting.common.domain.GunType;
import ro.ubb.remoting.common.service.GunTypeService;

import java.util.List;
import java.util.Set;

@Service
public class GunTypeServiceClient implements GunTypeService {

    @Autowired
    GunTypeService gunTypeService;

    @Override
    public void addGunType(GunType gunType) {
        gunTypeService.addGunType(gunType);
    }

    @Override
    public void deleteGunType(GunType gunType) {
        gunTypeService.deleteGunType(gunType);
    }

    @Override
    public void updateGunType(GunType gunType) {
        gunTypeService.updateGunType(gunType);
    }

    @Override
    public List<GunType> getAllGunTypes() {
        return gunTypeService.getAllGunTypes();
    }

    @Override
    public List<GunType> filterGunTypesByCategory(Category category) {
        return gunTypeService.filterGunTypesByCategory(category);
    }

    @Override
    public GunType getGunTypeById(Long id) {
        return gunTypeService.getGunTypeById(id);
    }
}
