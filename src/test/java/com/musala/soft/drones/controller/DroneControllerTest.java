package com.musala.soft.drones.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.argument.RegisterDroneRequestInvalidArgumentProvider;
import com.musala.soft.drones.dto.DroneBatteryDTO;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.dto.TripDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.enums.TripStatus;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.payload.LoadMedicationsRequest;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.repository.DroneRepository;
import com.musala.soft.drones.repository.MedicationRepository;
import com.musala.soft.drones.repository.TripRepository;
import com.musala.soft.drones.service.MedicationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

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

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MedicationService medicationService;

    @BeforeEach
    void deleteAllDrone() {

        medicationRepository.deleteAll();
        tripRepository.deleteAll();
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
    void getAvailableDrones_returnDroneWithIdleAndLoadingState() {

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

        Drone d3 = new Drone();
        d3.setState(State.LOADED);
        d3.setBatteryCapacity(100D);
        d3.setSerialNumber("s3");
        d3.setModel(Model.LIGHT_WEIGHT);
        d3.setWeightLimit(500D);

        droneRepository.save(d1);
        droneRepository.save(d2);
        droneRepository.save(d3);

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

            assertEquals(2, response.getPayload().size());
            assertTrue(response.getPayload().stream().allMatch(droneDTO -> State.IDLE == droneDTO.getState()
                    || State.LOADING == droneDTO.getState()));

        });

    }


    @SneakyThrows
    @Test
    void getDroneBatteryLevel_notFoundDrone_returnFailedResponse() {
        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(BASE_URL + "/api/v1/drone/1/battery-level")
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<DroneDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.DRONE_NOT_EXIST, response.getErrors().get(0).getCode());
        });

    }


    @SneakyThrows
    @Test
    void getDroneBatteryLevel_existDrone_returnSuccessResponse() {
        // Given

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(50D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);

        final Drone saveDrone = droneRepository.save(d1);

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(BASE_URL + "/api/v1/drone/" + saveDrone.getId() + "/battery-level")
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<DroneBatteryDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
            assertTrue(response.getSuccess());
            assertNull(response.getErrors());
            assertEquals(HttpStatus.OK.value(), response.getCode());

            assertEquals(50D, response.getPayload().getBatteryCapacity());
            assertEquals("s1", response.getPayload().getSerialNumber());
        });

    }


    @SneakyThrows
    @Test
    void loadMedication_notExistDrown_returnFailedResponse() {

        LoadMedicationsRequest request = new LoadMedicationsRequest();

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/1/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.DRONE_NOT_EXIST, response.getErrors().get(0).getCode());
        });

    }


    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = { "IDLE", "LOADING" })
    @SneakyThrows
    void testWithEnumSource(State state) {

        Drone d1 = new Drone();
        d1.setState(state);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);
        final Drone save = droneRepository.save(d1);

        LoadMedicationsRequest request = new LoadMedicationsRequest();

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/"+save.getId()+"/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.DRONE_NOT_IN_VALID_STATE_TO_LOAD, response.getErrors().get(0).getCode());
        });
    }

    @SneakyThrows
    @Test
    void loadMedication_batteryLevelLow_returnFailedResponse() {

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(24D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);
        final Drone save = droneRepository.save(d1);

        LoadMedicationsRequest request = new LoadMedicationsRequest();

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/"+save.getId()+"/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.DRONE_BATTERY_LEVEL_LESS_THAN_THRESHOLD, response.getErrors().get(0).getCode());
        });
    }


    @SneakyThrows
    @Test
    void loadMedication_notExistMedication_returnFailedResponse() {

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);
        final Drone save = droneRepository.save(d1);

        LoadMedicationsRequest request = new LoadMedicationsRequest();
        request.setMedicationIds(List.of(1L));

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/"+save.getId()+"/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.MEDICATION_LOADED_BEFORE_OR_NOT_EXIST, response.getErrors().get(0).getCode());
        });
    }

    @SneakyThrows
    @Test
    void loadMedication_medicationWeightMoreThanDroneLimit_returnFailedResponse() {

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);
        final Drone save = droneRepository.save(d1);

        final AddingMedicationRequest addingMedicationRequest = InputProvider.validAddingMedicationRequest();
        addingMedicationRequest.setWeight(600D);
        final MedicationDTO medicationDTO = medicationService.addMedication(addingMedicationRequest);

        LoadMedicationsRequest request = new LoadMedicationsRequest();
        request.setMedicationIds(List.of(medicationDTO.getId()));

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/"+save.getId()+"/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<Object> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
            assertFalse(response.getSuccess());
            assertNotNull(response.getErrors());
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
            assertEquals(ErrorCode.MEDICATION_WEIGHT_MORE_THAN_DRONE_LIMIT, response.getErrors().get(0).getCode());
        });
    }


    @SneakyThrows
    @Test
    void loadMedication_validRequest_returnSuccessResponse() {

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);
        final Drone save = droneRepository.save(d1);

        final AddingMedicationRequest addingMedicationRequest = InputProvider.validAddingMedicationRequest();
        final MedicationDTO medicationDTO = medicationService.addMedication(addingMedicationRequest);

        LoadMedicationsRequest request = new LoadMedicationsRequest();
        request.setMedicationIds(List.of(medicationDTO.getId()));

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(BASE_URL + "/api/v1/drone/"+save.getId()+"/medications")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<TripDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
            assertTrue(response.getSuccess());
            assertNull(response.getErrors());
            assertEquals(HttpStatus.OK.value(), response.getCode());

            assertNotNull(response.getPayload());
            assertEquals(TripStatus.LOADING, response.getPayload().getStatus());
            assertNotNull(response.getPayload().getDrone());
            assertEquals(save.getId(),response.getPayload().getDrone().getId());
            assertNotNull(response.getPayload().getTripItems());
            assertEquals(1,response.getPayload().getTripItems().size());
            assertEquals(medicationDTO.getId(),response.getPayload().getTripItems().stream().findFirst().get().getId());
        });
    }

}