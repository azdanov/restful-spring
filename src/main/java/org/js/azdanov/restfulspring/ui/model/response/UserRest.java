package org.js.azdanov.restfulspring.ui.model.response;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class UserRest {

  private UUID userId;
  private String firstName;
  private String lastName;
  private String email;
  private List<AddressRest> addresses;
}
