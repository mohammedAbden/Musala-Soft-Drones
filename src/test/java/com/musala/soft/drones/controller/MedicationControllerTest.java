package com.musala.soft.drones.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "http://localhost:9090";

    @SneakyThrows
    @Test
    void addingMedication_validRequest_returnSuccessResponse() {

        final AddingMedicationRequest request = InputProvider.validAddingMedicationRequest();

        // Given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(BASE_URL + "/api/v1/medication")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json");

        //When
        final MvcResult result = mockMvc.perform(builder).andReturn();
        final JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(Charset.defaultCharset()));
        ApiResponse<MedicationDTO> response = objectMapper.convertValue(json, new TypeReference<>() {

        });

        assertAll(() -> {
            assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
            assertTrue(response.getSuccess());
            assertNull(response.getErrors());
            assertEquals(HttpStatus.CREATED.value(), response.getCode());

            assertEquals("ABC_123-CD", response.getPayload().getCode());
            assertEquals(20D, response.getPayload().getWeight());
            assertEquals("name1", response.getPayload().getName());
            assertEquals("image1", response.getPayload().getImage());
        });
    }


}