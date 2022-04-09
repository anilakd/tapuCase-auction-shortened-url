package com.anilakdemir.auctionshortenedurl.exception;

import com.anilakdemir.auctionshortenedurl.dto.RestResponse;
import com.anilakdemir.auctionshortenedurl.exceptions.DuplicateException;
import com.anilakdemir.auctionshortenedurl.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author anilakdemir
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        // TODO: 08.04.2022 bütün alanları döndürme işlemine bak
        Date errorDate = new Date();
        String message = ex.getFieldError().getDefaultMessage();
        String description = request.getDescription(false);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorDate, description);

        RestResponse<ExceptionResponse> restResponse = RestResponse.error(exceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicateException(DuplicateException exception, WebRequest webRequest){

        Date errorDate = new Date();
        String message = exception.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorDate, description);

        RestResponse<ExceptionResponse> restResponse = RestResponse.error(exceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restResponse);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest webRequest){

        Date errorDate = new Date();
        String message = exception.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorDate, description);

        RestResponse<ExceptionResponse> restResponse = RestResponse.error(exceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);
    }
}
