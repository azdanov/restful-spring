package org.js.azdanov.restfulspring.io.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 42784019263959676L;

  @Id @GeneratedValue private long id;

  @Column(nullable = false, unique = true)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String encryptedPassword;

  private String emailVerificationToken;

  @Column(nullable = false)
  private Boolean emailVerificationStatus = false;
}
