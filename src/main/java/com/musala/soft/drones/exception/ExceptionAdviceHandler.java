package com.musala.soft.drones.exception;

import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.api.ErrorPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdviceHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {

        final String simpleName = HttpClientErrorException.class.getSimpleName();
        List<ErrorPayload> errorPayloads = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorPayloads.add(ErrorPayload.builder()
                    .enMessage(getMessage(error.getDefaultMessage(), "en"))
                    .arMessage(getMessage(error.getDefaultMessage(), "ar"))
                    .code(error.getDefaultMessage())
                    .type(simpleName).build());

        }
        return handleErrorResponse(errorPayloads, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeBusinessException.class)
    private ResponseEntity<Object> handleException(RuntimeBusinessException exception) {

        final String simpleName = RuntimeBusinessException.class.getSimpleName();
        List<ErrorPayload> errorPayloads = new ArrayList<>();

        errorPayloads.add(ErrorPayload.builder()
                .enMessage(getMessage(exception.getErrorCode(), "en"))
                .arMessage(getMessage(exception.getErrorCode(), "ar"))
                .code(exception.getErrorCode())
                .type(simpleName).build());

        return handleErrorResponse(errorPayloads, exception.getStatus());
    }

    private String getMessage(String error, String language) {

        try {
            if ("en".equalsIgnoreCase(language))
                return messageSource.getMessage(error, null, new Locale("en"));
            return messageSource.getMessage(error, null, new Locale("ar"));
        } catch (NoSuchMessageException messageEx) {
            if ("en".equalsIgnoreCase(language))
                return messageSource.getMessage("UNEXPECTED_ERROR", null, new Locale("en"));
            return messageSource.getMessage("UNEXPECTED_ERROR", null, new Locale("ar"));
        }
    }

    private ResponseEntity<Object> handleErrorResponse(List<ErrorPayload> errors, HttpStatus status) {

        return new ResponseEntity<>(ApiResponse.builder()
                .success(Boolean.FALSE)
                .errors(errors)
                .code(status.value())
                .build(), status);
    }

}
