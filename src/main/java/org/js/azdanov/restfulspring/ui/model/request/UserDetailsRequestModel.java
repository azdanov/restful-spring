package org.js.azdanov.restfulspring.ui.model.request;

import java.util.List;
import lombok.Data;

@Data
public class UserDetailsRequestModel {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<AddressRequestModel> addresses;
}
