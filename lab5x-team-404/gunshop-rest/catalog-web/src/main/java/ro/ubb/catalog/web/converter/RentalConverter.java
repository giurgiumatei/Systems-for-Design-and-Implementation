package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.web.dto.RentalDto;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        var model = new Rental();
        model.setId(dto.getId());
        model.setPrice(dto.getPrice());
        model.setClientId(dto.getClientId());
        model.setGunTypeId(dto.getGunTypeId());
        return model;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = new RentalDto(rental.getPrice(), rental.getClientId(),rental.getGunTypeId());
        dto.setId(rental.getId());
        return dto;
    }
}
