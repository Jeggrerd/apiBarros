package com.barros.barros.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException() {
        super();
    }
    public ClienteNotFoundException(String message) {
        super(message);
    }
    public ClienteNotFoundException(Integer id) {
        super("Cliente not found: " + id);
    }
}
