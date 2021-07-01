package ro.ubb.catalog.web.dto;

import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GunProvidersDto {

    List<GunProviderDto> gunProviders;
}
