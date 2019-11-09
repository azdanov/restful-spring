package org.js.azdanov.restfulspring.io.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 42784019263959676L;

  @Id @GeneratedValue private long id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120, unique = true)
  private String email;

  @Column(nullable = false)
  private String encryptedPassword;

  private String emailVerificationToken;

  @Column(nullable = false)
  private Boolean emailVerificationStatus = false;

  @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;
}
