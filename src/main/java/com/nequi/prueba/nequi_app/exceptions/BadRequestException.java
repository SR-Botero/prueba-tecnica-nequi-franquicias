package com.nequi.prueba.nequi_app.exceptions;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}
