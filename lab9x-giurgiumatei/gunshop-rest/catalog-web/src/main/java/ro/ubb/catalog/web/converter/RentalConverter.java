package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.GunProvider;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.web.dto.RentalDto;

@Component
public class RentalConverter extends AbstractConverter<Rental, RentalDto> {

    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        var model = new Rental();
        model.setClient(Client.builder().name(dto.getClientName()).build());
        model.setGunType(GunType.builder().name(dto.getGunTypeName()).build());
        model.setPrice(dto.getPrice());
        return model;

    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        return RentalDto.builder()
                .clientName(rental.getClient().getName())
                .gunTypeName(rental.getGunType().getName())
                .price(rental.getPrice())
                .build();
    }
}
//
//@Component
//public class RentalConverter extends BaseConverter<Rental, RentalDto> {
//    @Override
//    public Rental convertDtoToModel(RentalDto dto) {
//        var model = new Rental();
//        model.setId(dto.getId());
//        model.setPrice(dto.getPrice());
//        model.setClientId(dto.getClientId());
//        model.setGunTypeId(dto.getGunTypeId());
//        return model;
//    }
//
//    @Override
//    public RentalDto convertModelToDto(Rental rental) {
//        RentalDto dto = new RentalDto(rental.getPrice(), rental.getClientId(),rental.getGunTypeId());
//        dto.setId(rental.getId());
//        return dto;
//    }
//}
