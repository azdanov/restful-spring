package org.js.azdanov.restfulspring.exceptions;

import java.util.Date;
import org.js.azdanov.restfulspring.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionsHandler {

  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<Object> handleUserServiceException(
      ServiceException exception, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception exception, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
