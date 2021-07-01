package ro.ubb.springjpa.service;

import ro.ubb.springjpa.model.Category;
import ro.ubb.springjpa.model.GunType;

import java.util.List;

public interface GunTypeService {

    void addGunType(GunType gunType);
    void deleteGunType(GunType gunType);
    void updateGunType(GunType gunType);
    GunType getGunTypeById(Long id);
    List<GunType> getAllGunTypes();
    List<GunType> filterGunTypesByCategory(Category category);
}
