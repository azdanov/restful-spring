package org.js.azdanov.restfulspring.ui.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequestModel {

  String email;
  String password;
}
