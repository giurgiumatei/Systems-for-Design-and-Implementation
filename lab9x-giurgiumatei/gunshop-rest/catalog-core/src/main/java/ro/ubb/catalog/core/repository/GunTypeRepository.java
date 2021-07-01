package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Category;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.core.model.GunType;

import java.util.List;

public interface GunTypeRepository extends Repository<GunType, Long> {

    List<GunType> findByCategory(Category category);
    List<GunType> findByName(String name);
}