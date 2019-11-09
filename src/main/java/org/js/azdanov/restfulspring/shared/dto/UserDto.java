package org.js.azdanov.restfulspring.shared.dto;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {

  private long id;
  private UUID userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private String emailVerificationToken;
  private boolean emailVerificationStatus = false;
  private List<AddressDto> addresses;
}
