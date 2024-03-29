package org.js.azdanov.restfulspring.ui.model.request;

import lombok.Data;

@Data
class AddressRequestModel {

  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
