package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GunProviderDto extends BaseDto{

    private String name;
    private String speciality;
    private int reputation;
}
