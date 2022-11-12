package com.musala.soft.drones.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.argument.RegisterDroneRequestInvalidArgumentProvider;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "http://localhost:9090";

    @SneakyThrows
    @Test
    void registerDrone_validRequest_returnSuccessResponse() {

        final RegisterDroneRequest request = InputProvider.validRegisterDroneRequest();

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(BASE_URL + "/api/v1/drone")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        //When
        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<DroneDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
            assertTrue(response.getSuccess());
            assertNull(response.getErrors());
            assertEquals(HttpStatus.CREATED.value(), response.getCode());

            assertEquals(500D, response.getPayload().getWeightLimit());
            assertEquals(100D, response.getPayload().getBatteryCapacity());
            assertEquals(Model.LIGHT_WEIGHT, response.getPayload().getModel());
            assertEquals(request.getSerialNumber(), response.getPayload().getSerialNumber());
            assertEquals(State.IDLE, response.getPayload().getState());
        });
    }

    @SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(RegisterDroneRequestInvalidArgumentProvider.class)
    void registerDrone_invalidRequest_returnFailedResponse(RegisterDroneRequest request, String errorCode){

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(BASE_URL + "/api/v1/drone")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        //When
        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
           assertEquals(errorCode,response.getErrors().get(0).getCode());
        });
    }

}