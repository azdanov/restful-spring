package org.js.azdanov.restfulspring.ui.model.response;

import lombok.Data;

@Data
public class UserRest {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;
}
