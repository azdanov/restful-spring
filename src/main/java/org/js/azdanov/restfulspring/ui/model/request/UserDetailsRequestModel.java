package org.js.azdanov.restfulspring.ui.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsRequestModel {

  String firstName;
  String lastName;
  String email;
  String password;
}
