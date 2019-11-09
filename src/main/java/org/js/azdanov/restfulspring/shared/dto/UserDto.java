package org.js.azdanov.restfulspring.shared.dto;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto implements Serializable {

  private static final long serialVersionUID = 1985343299534060426L;
  long id;
  String userId;
  String firstName;
  String lastName;
  String email;
  String password;
  String encryptedPassword;
  String emailVerificationToken;
  boolean emailVerificationStatus = false;
}
