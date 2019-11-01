package org.js.azdanov.restfulspring.exceptions;

public class UserServiceException extends RuntimeException {

  private static final long serialVersionUID = -2885762658603348099L;

  public UserServiceException(String message) {
    super(message);
  }
}
