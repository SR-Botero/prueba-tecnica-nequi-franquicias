package com.nequi.prueba.nequi_app.exceptions;

public class ResourceInUseException extends RuntimeException {

    public ResourceInUseException(String message) {
        super(message);
    }
}
