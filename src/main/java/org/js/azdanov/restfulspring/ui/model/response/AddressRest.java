package org.js.azdanov.restfulspring.ui.model.response;

import java.util.UUID;
import lombok.Data;

@Data
public class AddressRest {

  private UUID addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
