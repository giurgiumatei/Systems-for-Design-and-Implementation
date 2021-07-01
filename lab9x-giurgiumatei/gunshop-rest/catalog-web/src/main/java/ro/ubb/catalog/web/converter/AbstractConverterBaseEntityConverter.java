package ro.ubb.catalog.web.converter;

import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.web.dto.BaseDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConverterBaseEntityConverter<Model extends BaseEntity<Long>, Dto extends BaseDto>
        extends AbstractConverter<Model, Dto> implements ConverterBaseEntity<Model, Dto> {

    public List<Long> convertModelsToIDs(List<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public List<Long> convertDTOsToIDs(List<Dto> dtos) {
        return dtos.stream()
                .map(BaseDto::getId)
                .collect(Collectors.toList());
    }

}
