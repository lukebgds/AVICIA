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

import com.avicia.api.service.SystemLogService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private final SystemLogService systemLogService;

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

    /**
     * Trata exceções de regra de negócio (BusinessException)
     * e registra o erro no SystemLogService se houver contexto de usuário.
     */
    @ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException (BusinessException exception, WebRequest request) {
    
        // Registra erro automaticamente, se houver dados de contexto
        if (exception.getIdUsuario() != null || exception.getContexto() != null) {
            systemLogService.registrarErro(
                exception.getIdUsuario() != null ? exception.getIdUsuario() : 100,
                exception.getContexto() != null ? exception.getContexto() : "Sistema",
                exception.getMessage()
            );
        }

        ResponseError error = responseError(exception.getMessage(), HttpStatus.CONFLICT);
        return handleExceptionInternal(exception, error, headers(), HttpStatus.CONFLICT, request);
    }

    /**
     * Trata exceções genéricas e falhas não previstas.
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral (Exception e, WebRequest request) {
        
        if (e instanceof UndeclaredThrowableException undeclared
                && undeclared.getUndeclaredThrowable() instanceof BusinessException businessEx) {
            return handleBusinessException(businessEx, request);
        }

        String message = messageSource.getMessage(
            "error.server",
            new Object[] { e.getMessage() },
            "Erro interno no servidor", null
        );

        ResponseError error = responseError(message, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
