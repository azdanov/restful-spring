package org.js.azdanov.restfulspring.ui.model.response;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorMessage extends RuntimeException {

  private static final long serialVersionUID = -8727704235960510598L;
  private final Date timestamp;
  private final String message;
}
