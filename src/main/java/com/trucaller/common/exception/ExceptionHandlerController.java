package com.trucaller.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<String> handleBadRequest(HttpClientErrorException.BadRequest ex, HttpServletRequest request) {
        // Access the Request object and extract information from it
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        // Create a response message
        String responseMessage = "Bad Request: " + ex.getMessage() + ", Request: " + requestMethod + " " + requestURI;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

}
