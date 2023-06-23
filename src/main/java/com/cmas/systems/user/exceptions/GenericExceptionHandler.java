package com.cmas.systems.user.exceptions;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@ControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Problem> handleEmptyFieldException(EmptyFieldException ex) {
        return ResponseEntity.of(
                Optional.of(
                        Problem.builder()
                                .withTitle("EmptyFieldException")
                                .withStatus(Status.BAD_REQUEST)
                                .withDetail(ex.getMessage()).build()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Problem> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.of(
                Optional.of(
                        Problem.builder()
                                .withTitle("UserNotFoundException")
                                .withStatus(Status.NOT_FOUND)
                                .withDetail(ex.getMessage()).build()));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Problem> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return ResponseEntity.of(
                Optional.of(
                        Problem.builder()
                                .withTitle("UnsupportedOperationException")
                                .withStatus(Status.BAD_REQUEST)
                                .withDetail(ex.getMessage()).build()));
    }
}
