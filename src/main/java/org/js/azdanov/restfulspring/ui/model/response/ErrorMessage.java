package org.js.azdanov.restfulspring.ui.model.response;

import java.util.Date;
import lombok.Data;

@Data
public class ErrorMessage {

  private static final long serialVersionUID = -8727704235960510598L;
  private final Date timestamp;
  private final String message;
}
