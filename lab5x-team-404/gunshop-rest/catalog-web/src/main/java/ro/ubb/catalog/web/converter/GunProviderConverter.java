package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.web.dto.GunProviderDto;

@Component
public class GunProviderConverter extends BaseConverter<GunProvider, GunProviderDto> {
    @Override
    public GunProvider convertDtoToModel(GunProviderDto dto) {
        var model = new GunProvider();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setReputation(dto.getReputation());
        model.setSpeciality(dto.getSpeciality());
        return model;
    }

    @Override
    public GunProviderDto convertModelToDto(GunProvider gunProvider) {
        GunProviderDto dto = new GunProviderDto(gunProvider.getName(), gunProvider.getSpeciality(), gunProvider.getReputation());
        dto.setId(gunProvider.getId());
        return dto;
    }
}
