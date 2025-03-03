package com.iss.exceptionhandlers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle all exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex);  // Log the exception stack trace
        
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();  // Generic error message
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);  // Return a 500 error
    }

    // Handle specific exceptions (you can add more handlers if needed)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.error("IllegalArgumentException: ", ex);
        
        String errorMessage = "Invalid argument: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
    // Example: Handle custom exceptions (if you have any)
    
}

