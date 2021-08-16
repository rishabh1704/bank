package com.finance.bank.errors;

import com.finance.bank.exceptions.InvalidCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
//        final String bodyOfResponse = "The Credentials stored not found";
        final String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
