package org.js.azdanov.restfulspring.exceptions;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = -2885762658603348099L;

  public ServiceException(String message) {
    super(message);
  }
}
