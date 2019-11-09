package org.js.azdanov.restfulspring.io.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity(name = "addresses")
public class AddressEntity implements Serializable {

  private static final long serialVersionUID = -8424305501881875415L;

  @Id @GeneratedValue private long id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID addressId;

  @Column(length = 15, nullable = false)
  private String city;

  @Column(length = 15, nullable = false)
  private String country;

  @Column(length = 100, nullable = false)
  private String streetName;

  @Column(length = 7, nullable = false)
  private String postalCode;

  @Column(length = 10, nullable = false)
  private String type;

  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity userDetails;
}
