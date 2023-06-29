package com.cmas.systems.user.exceptions;

/** One or more Fields empty or null */
public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String msg) {
        super(msg);
    }

    public EmptyFieldException() {
        super();
    }
}
