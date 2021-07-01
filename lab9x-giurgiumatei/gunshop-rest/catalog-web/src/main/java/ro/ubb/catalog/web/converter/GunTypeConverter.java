package ro.ubb.catalog.web.converter;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.web.dto.GunTypeDto;

@Component
public class GunTypeConverter extends BaseConverter<GunType, GunTypeDto> {
    @Override
    public GunType convertDtoToModel(GunTypeDto dto) {
        var model = new GunType();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setCategory(dto.getCategory());
        model.setGunProvider(GunProvider.builder().name(dto.getGunProviderName()).build());
        return model;
    }

    @Override
    public GunTypeDto convertModelToDto(GunType gunType) {
        GunTypeDto dto = new GunTypeDto(gunType.getName(), gunType.getCategory(),gunType.getGunProvider().getName());
        dto.setId(gunType.getId());
        return dto;
    }
}
