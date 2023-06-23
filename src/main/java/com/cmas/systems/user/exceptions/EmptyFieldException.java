package com.cmas.systems.user.exceptions;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String msg) {
        super(msg);
    }

    public EmptyFieldException() {
        super();
    }
}
