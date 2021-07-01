package ro.ubb.catalog.web.dto;

import lombok.*;
import ro.ubb.catalog.core.model.Category;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class GunTypeDto extends BaseDto{

    private String name;
    private Category category;
    private long gunProviderId;
}
