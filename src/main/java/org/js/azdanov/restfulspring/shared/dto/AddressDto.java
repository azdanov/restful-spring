package org.js.azdanov.restfulspring.shared.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class AddressDto {

  private long id;
  private UUID addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
  private UserDto userDetails;
}
