package com.teset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleUnknownException(Exception e){
        ApiError apiError = ApiError.builder()
                .error("internal_error")
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
       return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(RegisterException.class)
    protected ResponseEntity<ApiError> handleUnknownException(RegisterException e){
        ApiError apiError = ApiError.builder()
                .error("bad_request")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiError> handleNotFoundException(NotFoundException e){
        ApiError apiError = ApiError.builder()
                .error("bad_request")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException e){
        ApiError apiError = ApiError.builder()
                .error("bad_request")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}
