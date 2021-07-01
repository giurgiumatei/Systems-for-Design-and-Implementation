package ro.ubb.catalog.web.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class RentalDto extends BaseDto{

   private int price;
   private long clientId;
   private long gunTypeId;
}
