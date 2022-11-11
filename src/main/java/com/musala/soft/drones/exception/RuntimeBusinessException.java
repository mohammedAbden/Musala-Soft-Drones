package com.musala.soft.drones.exception;

import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.api.ErrorPayload;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class RuntimeBusinessException extends ResponseStatusException {

    /**
     *
     */
    private static final long serialVersionUID = -8526630656781428983L;


    private final String errorCode;

    public RuntimeBusinessException(String errorMessage, String errorCode, HttpStatus status) {

        super(status, errorMessage);
        this.errorCode = errorCode;
    }

    public RuntimeBusinessException(String errorMessage, String errorCode) {

        super(HttpStatus.BAD_REQUEST, errorMessage);
        this.errorCode = errorCode;
    }


    public RuntimeBusinessException(String errorMessage, String errorCode, HttpStatus status, Throwable cause) {

        super(status, errorMessage, cause);
        this.errorCode = errorCode;
    }


    public static ResponseEntity<Object> getExceptionBody(final RuntimeBusinessException exception, final String simpleName) {

        return ResponseEntity.status(exception.getStatus()).body(ApiResponse.builder()
                .success(Boolean.FALSE)
                .errors(Stream.of(ErrorPayload.builder()
                        .type(simpleName)
                        .code(exception.getErrorCode())
                        .arMessage(exception.getMessage())
                        .enMessage(exception.getMessage())
                        .build()).collect(Collectors.toList()))
                .code(exception.getStatus().value()).build());
    }


}
