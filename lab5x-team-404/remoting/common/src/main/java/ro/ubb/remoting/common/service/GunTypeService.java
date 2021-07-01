package ro.ubb.remoting.common.service;

import ro.ubb.remoting.common.domain.Category;
import ro.ubb.remoting.common.domain.GunType;

import java.util.List;

public interface GunTypeService {

    void addGunType(GunType gunType);
    void deleteGunType(GunType gunType);
    void updateGunType(GunType gunType);
    GunType getGunTypeById(Long id);
    List<GunType> getAllGunTypes();
    List<GunType> filterGunTypesByCategory(Category category);
}