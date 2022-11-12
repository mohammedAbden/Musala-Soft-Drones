package com.musala.soft.drones.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.argument.RegisterDroneRequestInvalidArgumentProvider;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.repository.DroneRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Collection;
import java.util.List;

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

    @Autowired
    private DroneRepository droneRepository;

    @BeforeEach
    void deleteAllDrone() {

        droneRepository.deleteAll();
    }


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
    void registerDrone_invalidRequest_returnFailedResponse(RegisterDroneRequest request, String errorCode) {

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
            assertEquals(errorCode, response.getErrors().get(0).getCode());
        });
    }

    @SneakyThrows
    @Test
    void registerDrone_duplicationSerialNumber_returnFailedResponse() {

        final RegisterDroneRequest request = InputProvider.validRegisterDroneRequest();
        request.setSerialNumber("exist_serial");

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(BASE_URL + "/api/v1/drone")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<DroneDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        //When
        final MvcResult result2 = mockMvc.perform(builder).andReturn();
        final JsonNode json2 = objectMapper.readTree(result2.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response2 = objectMapper.convertValue(json2, new TypeReference<>() {

        });

        //then
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

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result2.getResponse().getStatus());
            assertFalse(response2.getSuccess());
            assertNotNull(response2.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response2.getCode());
            assertEquals(ErrorCode.DRONE_SERIAL_NUMBER_EXIST, response2.getErrors().get(0).getCode());
        });
    }

    @SneakyThrows
    @Test
    void getAvailableDrones_returnFailedResponse() {

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);

        Drone d2 = new Drone();
        d2.setState(State.LOADING);
        d2.setBatteryCapacity(100D);
        d2.setSerialNumber("s2");
        d2.setModel(Model.LIGHT_WEIGHT);
        d2.setWeightLimit(500D);

        droneRepository.save(d1);
        droneRepository.save(d2);

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(BASE_URL + "/api/v1/drone/available")
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<List<DroneDTO>> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        //then
        assertAll(() -> {
            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
            assertTrue(response.getSuccess());
            assertNull(response.getErrors());
            assertEquals(HttpStatus.OK.value(), response.getCode());

            assertEquals(1,response.getPayload().size());
            assertEquals("s1", response.getPayload().get(0).getSerialNumber());
        });

    }


}