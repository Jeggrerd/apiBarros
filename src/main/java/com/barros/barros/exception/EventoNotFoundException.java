package com.barros.barros.exception;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException() {
        super();
    }
    public EventoNotFoundException(String message) {
        super(message);
    }
    public EventoNotFoundException(Integer id) {
        super("Evento not found: " + id);
    }
}
