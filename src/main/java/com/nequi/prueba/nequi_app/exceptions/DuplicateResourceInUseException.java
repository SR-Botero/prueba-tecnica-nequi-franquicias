package com.nequi.prueba.nequi_app.exceptions;

public class DuplicateResourceInUseException extends RuntimeException {

  public DuplicateResourceInUseException(String message) {
    super(message);
  }
}
