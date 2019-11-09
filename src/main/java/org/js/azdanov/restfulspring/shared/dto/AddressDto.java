package org.js.azdanov.restfulspring.shared.dto;

import lombok.Data;

@Data
class AddressDto {

  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
