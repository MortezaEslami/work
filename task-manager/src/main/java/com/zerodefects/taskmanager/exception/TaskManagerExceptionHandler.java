package com.zerodefects.taskmanager.exception;

import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@ControllerAdvice
@RestController
public class TaskManagerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
        String fingerPrint = UUID.randomUUID().toString();
        LOGGER.error(fingerPrint);
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .title(messageSource.getMessage("internal.server.error", null, request.getLocale()))
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false))
                        .type(ExceptionResponseTypeValue.INTERNAL_SERVER_ERROR)
                        .fingerPrint(fingerPrint)
                        .timestamp(new Date())
                        .build();

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String fingerPrint = UUID.randomUUID().toString();
        LOGGER.info(fingerPrint);
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.NOT_FOUND.value())
                        .title(messageSource.getMessage("resource.not.found", null, request.getLocale()))
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false))
                        .type(ExceptionResponseTypeValue.NOT_FOUND)
                        .fingerPrint(fingerPrint)
                        .timestamp(new Date())
                        .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String fingerPrint = UUID.randomUUID().toString();
        List<ErrorField> errorFields = new ArrayList<>();
        LOGGER.info(fingerPrint);
        ex.getConstraintViolations().forEach(constraintViolation -> errorFields.add(
                new ErrorField(constraintViolation.getMessage(),
                        ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString())
        ));
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                        .title(messageSource.getMessage("parameters.are.not.valid", null, request.getLocale()))
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false))
                        .type(ExceptionResponseTypeValue.CONSTRAINT_VIOLATION)
                        .fingerPrint(fingerPrint)
                        .errorFields(errorFields)
                        .timestamp(new Date())
                        .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String fingerPrint = UUID.randomUUID().toString();
        LOGGER.info(fingerPrint);
        ExceptionResponse exceptionResponse =
                ExceptionResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                        .title(messageSource.getMessage("parameters.are.not.valid", null, request.getLocale()))
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false))
                        .type(ExceptionResponseTypeValue.CONSTRAINT_VIOLATION)
                        .fingerPrint(fingerPrint)
                        .timestamp(new Date())
                        .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
