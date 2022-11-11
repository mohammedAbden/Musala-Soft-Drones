package com.musala.soft.drones.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @Builder.Default
    private Boolean success = true;
    private List<ErrorPayload> errors;
    @Builder.Default
    private Integer code = HttpStatus.OK.value();
    private T payload;
    private String serviceTime;

    public static <T> ApiResponse<T> ok(T payload) {
        return status(HttpStatus.OK, payload, null);
    }

    public static <T> ApiResponse<T> created(T payload) {
        return status(HttpStatus.CREATED, payload, null);
    }

    public static <T> ApiResponse<T> accepted(T payload) {
        return status(HttpStatus.ACCEPTED, payload, null);
    }

    public static <T> ApiResponse<T> ok(T payload, String serviceTime) {
        return status(HttpStatus.OK, payload, serviceTime);
    }

    public static <T> ApiResponse<T> created(T payload, String serviceTime) {
        return status(HttpStatus.CREATED, payload, serviceTime);
    }

    public static <T> ApiResponse<T> accepted(T payload, String serviceTime) {
        return status(HttpStatus.ACCEPTED, payload, serviceTime);
    }

    public static <T> ApiResponse<T> noContent(String serviceTime) {
        return status(HttpStatus.NO_CONTENT, null, serviceTime);
    }

    public static <T> ApiResponse<T> badRequest(T payload, String serviceTime,List<ErrorPayload> errors) {
        return status(HttpStatus.BAD_REQUEST, payload, serviceTime,false,errors);
    }

    private static <T> ApiResponse<T> status(HttpStatus status, T payload, String serviceTime) {
        return new ApiResponse<>(true, null, status.value(), payload, serviceTime);
    }

    private static <T> ApiResponse<T> status(HttpStatus status, T payload, String serviceTime,boolean success,List<ErrorPayload> errors) {
        return new ApiResponse<>(success, errors, status.value(), payload, serviceTime);
    }
}
