package com.avicia.api.exception;

import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Resource;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private HttpHeaders headers() {
    
        HttpHeaders headers = new HttpHeaders();
    
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        return headers;
    }

    private ResponseError responseError (String message, HttpStatus statusCode) {
       
    ResponseError responseError = new ResponseError();

        responseError.setStatus("error");
        responseError.setErro(message);
        responseError.setStatusCode(statusCode.value());

        return responseError; 
    }

    @ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException (BusinessException exception, WebRequest request) {
        
        ResponseError error = responseError(exception.getMessage(), HttpStatus.CONFLICT);
    
        return handleExceptionInternal(exception, error, headers(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral (Exception e, WebRequest request) {
        
        if(e.getClass().isAssignableFrom(UndeclaredThrowableException.class)){
            
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;

            return handleBusinessException((BusinessException) exception.getUndeclaredThrowable(), request);
        } 
        else {
        
            String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
            
            ResponseError error = responseError(message,HttpStatus.INTERNAL_SERVER_ERROR);
            
            return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

}
