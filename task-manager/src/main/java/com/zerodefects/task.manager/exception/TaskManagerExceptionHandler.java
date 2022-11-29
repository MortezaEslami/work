package com.zerodefects.task.manager.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@ControllerAdvice
@RestController
public class TaskManagerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .title(messageSource.getMessage("internal.server.error", null, request.getLocale()))
                        .detail(ex.getMessage()).build();

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.NOT_FOUND.value())
                        .title(messageSource.getMessage("resource.not.found", null, request.getLocale()))
                        .detail(ex.getMessage())
                        .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }
}
