package ro.ubb.catalog.web.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class RentalDto {

//   private Long clientId;
//   private Long gunTypeId;
   private String clientName;
   private String gunTypeName;
   private int price;
}
